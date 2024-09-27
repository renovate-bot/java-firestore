/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/firestore/admin/v1/operation.proto

// Protobuf Java Version: 3.25.5
package com.google.firestore.admin.v1;

public interface IndexOperationMetadataOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.firestore.admin.v1.IndexOperationMetadata)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The time this operation started.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   *
   * @return Whether the startTime field is set.
   */
  boolean hasStartTime();
  /**
   *
   *
   * <pre>
   * The time this operation started.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   *
   * @return The startTime.
   */
  com.google.protobuf.Timestamp getStartTime();
  /**
   *
   *
   * <pre>
   * The time this operation started.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   */
  com.google.protobuf.TimestampOrBuilder getStartTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * The time this operation completed. Will be unset if operation still in
   * progress.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   *
   * @return Whether the endTime field is set.
   */
  boolean hasEndTime();
  /**
   *
   *
   * <pre>
   * The time this operation completed. Will be unset if operation still in
   * progress.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   *
   * @return The endTime.
   */
  com.google.protobuf.Timestamp getEndTime();
  /**
   *
   *
   * <pre>
   * The time this operation completed. Will be unset if operation still in
   * progress.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   */
  com.google.protobuf.TimestampOrBuilder getEndTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * The index resource that this operation is acting on. For example:
   * `projects/{project_id}/databases/{database_id}/collectionGroups/{collection_id}/indexes/{index_id}`
   * </pre>
   *
   * <code>string index = 3;</code>
   *
   * @return The index.
   */
  java.lang.String getIndex();
  /**
   *
   *
   * <pre>
   * The index resource that this operation is acting on. For example:
   * `projects/{project_id}/databases/{database_id}/collectionGroups/{collection_id}/indexes/{index_id}`
   * </pre>
   *
   * <code>string index = 3;</code>
   *
   * @return The bytes for index.
   */
  com.google.protobuf.ByteString getIndexBytes();

  /**
   *
   *
   * <pre>
   * The state of the operation.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.OperationState state = 4;</code>
   *
   * @return The enum numeric value on the wire for state.
   */
  int getStateValue();
  /**
   *
   *
   * <pre>
   * The state of the operation.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.OperationState state = 4;</code>
   *
   * @return The state.
   */
  com.google.firestore.admin.v1.OperationState getState();

  /**
   *
   *
   * <pre>
   * The progress, in documents, of this operation.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.Progress progress_documents = 5;</code>
   *
   * @return Whether the progressDocuments field is set.
   */
  boolean hasProgressDocuments();
  /**
   *
   *
   * <pre>
   * The progress, in documents, of this operation.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.Progress progress_documents = 5;</code>
   *
   * @return The progressDocuments.
   */
  com.google.firestore.admin.v1.Progress getProgressDocuments();
  /**
   *
   *
   * <pre>
   * The progress, in documents, of this operation.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.Progress progress_documents = 5;</code>
   */
  com.google.firestore.admin.v1.ProgressOrBuilder getProgressDocumentsOrBuilder();

  /**
   *
   *
   * <pre>
   * The progress, in bytes, of this operation.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.Progress progress_bytes = 6;</code>
   *
   * @return Whether the progressBytes field is set.
   */
  boolean hasProgressBytes();
  /**
   *
   *
   * <pre>
   * The progress, in bytes, of this operation.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.Progress progress_bytes = 6;</code>
   *
   * @return The progressBytes.
   */
  com.google.firestore.admin.v1.Progress getProgressBytes();
  /**
   *
   *
   * <pre>
   * The progress, in bytes, of this operation.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.Progress progress_bytes = 6;</code>
   */
  com.google.firestore.admin.v1.ProgressOrBuilder getProgressBytesOrBuilder();
}
