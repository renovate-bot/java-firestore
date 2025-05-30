/*
 * Copyright 2017 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.firestore;

import com.google.api.core.CurrentMillisClock;
import com.google.api.gax.grpc.GrpcStatusCode;
import com.google.api.gax.retrying.ExponentialRetryAlgorithm;
import com.google.api.gax.retrying.TimedAttemptSettings;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.BidiStreamObserver;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentChange.Type;
import com.google.common.base.Preconditions;
import com.google.firestore.v1.Document;
import com.google.firestore.v1.ListenRequest;
import com.google.firestore.v1.ListenResponse;
import com.google.firestore.v1.Target;
import com.google.firestore.v1.Target.QueryTarget;
import com.google.firestore.v1.TargetChange;
import com.google.protobuf.ByteString;
import io.grpc.Status;
import io.grpc.Status.Code;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/**
 * Watch provides listen functionality and exposes snapshot listeners. It can be used with any valid
 * Firestore Listen target.
 *
 * <p>This class is thread-compatible when called through the methods defined in ApiStreamObserver.
 * It synchronizes on its own instance so it is advisable not to use this class for external
 * synchronization.
 */
final class Watch implements BidiStreamObserver<ListenRequest, ListenResponse> {
  private static final Logger LOGGER = Logger.getLogger(Watch.class.getName());

  /**
   * Target ID used by watch. Watch uses a fixed target id since we only support one target per
   * stream. The actual target ID we use is arbitrary.
   */
  private static final int WATCH_TARGET_ID = 0x1;

  private final FirestoreImpl firestore;
  private final ScheduledExecutorService firestoreExecutor;
  private final Query query;
  private final Comparator<QueryDocumentSnapshot> comparator;
  private final ExponentialRetryAlgorithm backoff;
  private final Target target;
  private TimedAttemptSettings nextAttempt;
  private SilenceableBidiStream<ListenRequest, ListenResponse> stream;

  /** The sorted tree of DocumentSnapshots as sent in the last snapshot. */
  private DocumentSet documentSet;

  /** The accumulates map of document changes (keyed by document name) for the current snapshot. */
  private Map<ResourcePath, Document> changeMap;

  /** The server-assigned resume token. */
  private ByteString resumeToken;

  /** The user-provided listener. */
  private EventListener<QuerySnapshot> listener;

  /** The user-provided callback executor. */
  private Executor userCallbackExecutor;

  /**
   * Whether the retrieved result set has been marked 'CURRENT' (has caught up with the backend
   * state at target initialization).
   */
  private boolean current;

  /**
   * Tracks whether we've pushed an initial set of changes. This is needed since we should push
   * those even when there are none.
   */
  private boolean hasPushed;

  /**
   * Indicates whether we are interested in data from the stream. Set to false in the
   * 'unsubscribe()' callback.
   */
  private final AtomicBoolean isActive;

  /** The list of document changes in a snapshot separated by change type. */
  static class ChangeSet {
    List<QueryDocumentSnapshot> deletes = new ArrayList<>();
    List<QueryDocumentSnapshot> adds = new ArrayList<>();
    List<QueryDocumentSnapshot> updates = new ArrayList<>();
  }

  /**
   * @param firestore The Firestore Database client.
   * @param query The query that is used to order the document snapshots returned by this watch.
   * @param target A Firestore 'Target' proto denoting the target to listen on.
   */
  private Watch(FirestoreImpl firestore, Query query, Target target) {
    this.firestore = firestore;
    this.target = target;
    this.query = query;
    this.comparator = query.comparator();
    this.backoff =
        new ExponentialRetryAlgorithm(
            firestore.getOptions().getRetrySettings(), CurrentMillisClock.getDefaultClock());
    this.firestoreExecutor = firestore.getClient().getExecutor();
    this.isActive = new AtomicBoolean();
    this.nextAttempt = backoff.createFirstAttempt();
  }

  /**
   * Creates a new Watch instance that listens on DocumentReferences.
   *
   * @param documentReference The document reference for this watch.
   * @return A newly created Watch instance.
   */
  static Watch forDocument(DocumentReference documentReference) {
    Target.Builder target = Target.newBuilder();
    target.getDocumentsBuilder().addDocuments(documentReference.getName());
    target.setTargetId(WATCH_TARGET_ID);

    return new Watch(
        (FirestoreImpl) documentReference.getFirestore(),
        documentReference.getParent(),
        target.build());
  }

  /**
   * Creates a new Watch instance that listens on Queries.
   *
   * @param query The query used for this watch.
   * @return A newly created Watch instance.
   */
  static Watch forQuery(Query query) {
    Target.Builder target = Target.newBuilder();
    target.setQuery(
        QueryTarget.newBuilder()
            .setStructuredQuery(query.buildQuery())
            .setParent(query.options.getParentPath().getName())
            .build());
    target.setTargetId(WATCH_TARGET_ID);

    return new Watch((FirestoreImpl) query.getFirestore(), query, target.build());
  }

  @Override
  public void onStart(StreamController streamController) {}

  @Override
  public void onReady(ClientStream<ListenRequest> clientStream) {}

  @Override
  public synchronized void onResponse(ListenResponse listenResponse) {
    switch (listenResponse.getResponseTypeCase()) {
      case TARGET_CHANGE:
        TargetChange change = listenResponse.getTargetChange();
        boolean noTargetIds = change.getTargetIdsCount() == 0;

        switch (change.getTargetChangeType()) {
          case NO_CHANGE:
            if (noTargetIds && change.hasReadTime() && current) {
              // This means everything is up-to-date, so emit the current set of docs as a snapshot,
              // if there were changes.
              pushSnapshot(Timestamp.fromProto(change.getReadTime()), change.getResumeToken());
            }
            break;
          case ADD:
            if (WATCH_TARGET_ID != change.getTargetIds(0)) {
              closeStream(FirestoreException.forInvalidArgument("Target ID must be 0x01"));
            }
            break;
          case REMOVE:
            Status status =
                change.hasCause()
                    ? Status.fromCodeValue(change.getCause().getCode())
                    : Status.CANCELLED;
            closeStream(
                FirestoreException.forServerRejection(
                    status, "Backend ended Listen stream: " + change.getCause().getMessage()));
            break;
          case CURRENT:
            current = true;
            break;
          case RESET:
            resetDocs();
            break;
          default:
            closeStream(
                FirestoreException.forInvalidArgument(
                    "Encountered invalid target change type: " + change.getTargetChangeType()));
        }

        if (change.getResumeToken() != null
            && affectsTarget(change.getTargetIdsList(), WATCH_TARGET_ID)) {
          nextAttempt = backoff.createFirstAttempt();
        }

        break;
      case DOCUMENT_CHANGE:
        // No other targetIds can show up here, but we still need to see if the targetId was in the
        // added list or removed list.
        List<Integer> targetIds = listenResponse.getDocumentChange().getTargetIdsList();
        List<Integer> removedTargetIds =
            listenResponse.getDocumentChange().getRemovedTargetIdsList();
        boolean changed = targetIds.contains(WATCH_TARGET_ID);
        boolean removed = removedTargetIds.contains(WATCH_TARGET_ID);

        Document document = listenResponse.getDocumentChange().getDocument();
        ResourcePath name = ResourcePath.create(document.getName());

        if (changed) {
          changeMap.put(name, document);
        } else if (removed) {
          changeMap.put(name, null);
        }
        break;
      case DOCUMENT_DELETE:
        changeMap.put(ResourcePath.create(listenResponse.getDocumentDelete().getDocument()), null);
        break;
      case DOCUMENT_REMOVE:
        changeMap.put(ResourcePath.create(listenResponse.getDocumentRemove().getDocument()), null);
        break;
      case FILTER:
        // Keep copy of counts for producing log message.
        // The method currentSize() is computationally expensive, and should only be run once.
        int filterCount = listenResponse.getFilter().getCount();
        int currentSize = currentSize();
        if (filterCount != currentSize) {
          LOGGER.info(
              () ->
                  String.format(
                      "filter: count mismatch filter count %d != current size %d",
                      filterCount, currentSize));
          // We need to remove all the current results.
          resetDocs();
          // The filter didn't match, so re-issue the query.
          resetStream();
        }
        break;
      default:
        closeStream(
            FirestoreException.forInvalidArgument("Encountered invalid listen response type"));
        break;
    }
  }

  @Override
  public synchronized void onError(Throwable throwable) {
    maybeReopenStream(throwable);
  }

  @Override
  public synchronized void onComplete() {
    maybeReopenStream(new StatusException(Status.fromCode(Code.UNKNOWN)));
  }

  /** API entry point that starts the Watch stream. */
  ListenerRegistration runWatch(
      final Executor userCallbackExecutor, EventListener<QuerySnapshot> listener) {
    boolean watchStarted = isActive.compareAndSet(false, true);

    Preconditions.checkState(watchStarted, "Can't restart an already active watch");

    this.userCallbackExecutor = userCallbackExecutor;
    this.listener = listener;

    this.stream = null;
    this.documentSet = DocumentSet.emptySet(comparator);
    this.changeMap = new HashMap<>();
    this.resumeToken = null;
    this.current = false;

    initStream();

    return () -> {
      isActive.set(false);

      firestore
          .getClient()
          .getExecutor()
          .execute(
              () -> {
                synchronized (Watch.this) {
                  stream.closeSend();
                  stream = null;
                }
              });
    };
  }

  /**
   * Returns the current count of all documents, including the changes from the current changeMap.
   */
  private int currentSize() {
    ChangeSet changeSet = extractChanges(Timestamp.now());
    return documentSet.size() + changeSet.adds.size() - changeSet.deletes.size();
  }

  /** Helper to clear the docs on RESET or filter mismatch. */
  private void resetDocs() {
    changeMap.clear();
    resumeToken = null;

    for (DocumentSnapshot snapshot : documentSet) {
      // Mark each document as deleted. If documents are not deleted, they  will be sent again by
      // the server.
      changeMap.put(snapshot.getReference().getResourcePath(), null);
    }

    current = false;
  }

  /** Closes the stream and calls onError() if the stream is still active. */
  private void closeStream(final Throwable throwable) {
    if (stream != null) {
      stream.closeSendAndSilence();
      stream = null;
    }

    if (isActive.getAndSet(false)) {
      userCallbackExecutor.execute(
          () -> {
            if (throwable instanceof FirestoreException) {
              listener.onEvent(null, (FirestoreException) throwable);
            } else {
              Status status = getStatus(throwable);
              FirestoreException firestoreException =
                  FirestoreException.forApiException(
                      new ApiException(
                          throwable,
                          GrpcStatusCode.of(status != null ? status.getCode() : Code.UNKNOWN),
                          false));
              listener.onEvent(null, firestoreException);
            }
          });
    }
  }

  /**
   * Re-opens the stream unless the specified error is considered permanent. Clears the change map.
   */
  private void maybeReopenStream(Throwable throwable) {
    if (isActive.get() && !isPermanentError(throwable)) {
      if (isResourceExhaustedError(throwable)) {
        nextAttempt = backoff.createNextAttempt(nextAttempt);
      }

      changeMap.clear();
      resetStream();
    } else {
      closeStream(throwable);
    }
  }

  /** Helper to restart the outgoing stream to the backend. */
  private void resetStream() {
    if (stream != null) {
      stream.closeSendAndSilence();
      stream = null;
    }

    initStream();
  }

  /** Initializes a new stream to the backend with backoff. */
  private void initStream() {
    firestoreExecutor.schedule(
        () -> {
          try {
            if (!isActive.get()) {
              return;
            }

            synchronized (Watch.this) {
              if (!isActive.get()) {
                return;
              }

              Preconditions.checkState(stream == null);

              current = false;
              nextAttempt = backoff.createNextAttempt(nextAttempt);

              stream =
                  new SilenceableBidiStream<>(
                      Watch.this,
                      observer ->
                          firestore.streamRequest(
                              observer, firestore.getClient().listenCallable()));

              ListenRequest.Builder request = ListenRequest.newBuilder();
              request.setDatabase(firestore.getDatabaseName());
              request.setAddTarget(target);
              if (resumeToken != null) {
                request.getAddTargetBuilder().setResumeToken(resumeToken);
              }

              stream.send(request.build());
            }
          } catch (Throwable throwable) {
            onError(throwable);
          }
        },
        nextAttempt.getRandomizedRetryDelay().toMillis(),
        TimeUnit.MILLISECONDS);
  }

  /**
   * Checks if the current target id is included in the list of target ids. Returns true if no
   * targetIds are provided.
   */
  private boolean affectsTarget(List<Integer> targetIds, int currentId) {
    return targetIds == null || targetIds.isEmpty() || targetIds.contains(currentId);
  }

  /** Splits up document changes into removals, additions, and updates. */
  private ChangeSet extractChanges(Timestamp readTime) {
    ChangeSet changeSet = new ChangeSet();

    for (Entry<ResourcePath, Document> change : changeMap.entrySet()) {
      if (change.getValue() == null) {
        if (documentSet.contains(change.getKey())) {
          changeSet.deletes.add(documentSet.getDocument(change.getKey()));
        }
        continue;
      }

      QueryDocumentSnapshot snapshot =
          QueryDocumentSnapshot.fromDocument(firestore, readTime, change.getValue());

      if (documentSet.contains(change.getKey())) {
        changeSet.updates.add(snapshot);
      } else {
        changeSet.adds.add(snapshot);
      }
    }

    return changeSet;
  }

  /**
   * Assembles a new snapshot from the current set of changes and invokes the user's callback.
   * Clears the current changes on completion.
   */
  private void pushSnapshot(final Timestamp readTime, ByteString nextResumeToken) {
    final List<DocumentChange> changes = computeSnapshot(readTime);
    if (!hasPushed || !changes.isEmpty()) {
      final QuerySnapshot querySnapshot =
          QuerySnapshot.withChanges(query, readTime, documentSet, changes);
      LOGGER.fine(querySnapshot::toString);
      userCallbackExecutor.execute(() -> listener.onEvent(querySnapshot, null));
      hasPushed = true;
    }

    changeMap.clear();
    resumeToken = nextResumeToken;
  }

  /**
   * Applies a document delete to the document tree. Returns the corresponding DocumentChange event.
   */
  private DocumentChange deleteDoc(QueryDocumentSnapshot oldDocument) {
    ResourcePath resourcePath = oldDocument.getReference().getResourcePath();
    int oldIndex = documentSet.indexOf(resourcePath);
    documentSet = documentSet.remove(resourcePath);
    return new DocumentChange(oldDocument, Type.REMOVED, oldIndex, -1);
  }

  /**
   * Applies a document add to the document tree. Returns the corresponding DocumentChange event.
   */
  private DocumentChange addDoc(QueryDocumentSnapshot newDocument) {
    ResourcePath resourcePath = newDocument.getReference().getResourcePath();
    documentSet = documentSet.add(newDocument);
    int newIndex = documentSet.indexOf(resourcePath);
    return new DocumentChange(newDocument, Type.ADDED, -1, newIndex);
  }

  /**
   * Applies a document modification to the document tree. Returns the DocumentChange event for
   * successful modifications.
   */
  @Nullable
  private DocumentChange modifyDoc(QueryDocumentSnapshot newDocument) {
    ResourcePath resourcePath = newDocument.getReference().getResourcePath();
    DocumentSnapshot oldDocument = documentSet.getDocument(resourcePath);

    if (!oldDocument.getUpdateTime().equals(newDocument.getUpdateTime())) {
      int oldIndex = documentSet.indexOf(resourcePath);
      documentSet = documentSet.remove(resourcePath);
      documentSet = documentSet.add(newDocument);
      int newIndex = documentSet.indexOf(resourcePath);
      return new DocumentChange(newDocument, Type.MODIFIED, oldIndex, newIndex);
    }
    return null;
  }

  /**
   * Applies the mutations in changeMap to the document tree. Modified 'documentSet' in-place and
   * returns the changed documents.
   *
   * @param readTime The time at which this snapshot was obtained.
   */
  private List<DocumentChange> computeSnapshot(Timestamp readTime) {
    List<DocumentChange> appliedChanges = new ArrayList<>();

    ChangeSet changeSet = extractChanges(readTime);

    // Process the sorted changes in the order that is expected by our clients (removals, additions,
    // and then modifications). We also need to sort the individual changes to assure that
    // oldIndex/newIndex keep incrementing.
    changeSet.deletes.sort(comparator);
    for (QueryDocumentSnapshot delete : changeSet.deletes) {
      appliedChanges.add(deleteDoc(delete));
    }

    changeSet.adds.sort(comparator);
    for (QueryDocumentSnapshot add : changeSet.adds) {
      appliedChanges.add(addDoc(add));
    }

    changeSet.updates.sort(comparator);
    for (QueryDocumentSnapshot update : changeSet.updates) {
      DocumentChange change = modifyDoc(update);
      if (change != null) {
        appliedChanges.add(change);
      }
    }

    return appliedChanges;
  }

  /** Determines whether a GRPC Error is considered permanent and should not be retried. */
  private static boolean isPermanentError(Throwable throwable) {
    Status status = getStatus(throwable);

    if (status == null) {
      return true;
    }

    switch (status.getCode()) {
      case CANCELLED:
      case UNKNOWN:
      case DEADLINE_EXCEEDED:
      case RESOURCE_EXHAUSTED:
      case INTERNAL:
      case UNAVAILABLE:
      case UNAUTHENTICATED:
        return false;
      default:
        return true;
    }
  }

  /** Extracts the GRPC status code if available. Returns `null` for non-GRPC exceptions. */
  @Nullable
  private static Status getStatus(Throwable throwable) {
    if (throwable instanceof StatusRuntimeException) {
      return ((StatusRuntimeException) throwable).getStatus();
    } else if (throwable instanceof StatusException) {
      return ((StatusException) throwable).getStatus();
    } else if (throwable instanceof ApiException
        && ((ApiException) throwable).getStatusCode().getTransportCode() instanceof Code) {
      return ((Code) ((ApiException) throwable).getStatusCode().getTransportCode()).toStatus();
    }
    return null;
  }

  /** Determines whether we need to initiate a longer backoff due to system overload. */
  private static boolean isResourceExhaustedError(Throwable throwable) {
    Status status = getStatus(throwable);
    return status != null && status.getCode().equals(Code.RESOURCE_EXHAUSTED);
  }
}
