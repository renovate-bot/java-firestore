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
// source: google/firestore/v1/firestore.proto

// Protobuf Java Version: 3.25.5
package com.google.firestore.v1;

public interface PartitionQueryRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.firestore.v1.PartitionQueryRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Required. The parent resource name. In the format:
   * `projects/{project_id}/databases/{database_id}/documents`.
   * Document resource names are not supported; only database resource names
   * can be specified.
   * </pre>
   *
   * <code>string parent = 1 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The parent.
   */
  java.lang.String getParent();
  /**
   *
   *
   * <pre>
   * Required. The parent resource name. In the format:
   * `projects/{project_id}/databases/{database_id}/documents`.
   * Document resource names are not supported; only database resource names
   * can be specified.
   * </pre>
   *
   * <code>string parent = 1 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The bytes for parent.
   */
  com.google.protobuf.ByteString getParentBytes();

  /**
   *
   *
   * <pre>
   * A structured query.
   * Query must specify collection with all descendants and be ordered by name
   * ascending. Other filters, order bys, limits, offsets, and start/end
   * cursors are not supported.
   * </pre>
   *
   * <code>.google.firestore.v1.StructuredQuery structured_query = 2;</code>
   *
   * @return Whether the structuredQuery field is set.
   */
  boolean hasStructuredQuery();
  /**
   *
   *
   * <pre>
   * A structured query.
   * Query must specify collection with all descendants and be ordered by name
   * ascending. Other filters, order bys, limits, offsets, and start/end
   * cursors are not supported.
   * </pre>
   *
   * <code>.google.firestore.v1.StructuredQuery structured_query = 2;</code>
   *
   * @return The structuredQuery.
   */
  com.google.firestore.v1.StructuredQuery getStructuredQuery();
  /**
   *
   *
   * <pre>
   * A structured query.
   * Query must specify collection with all descendants and be ordered by name
   * ascending. Other filters, order bys, limits, offsets, and start/end
   * cursors are not supported.
   * </pre>
   *
   * <code>.google.firestore.v1.StructuredQuery structured_query = 2;</code>
   */
  com.google.firestore.v1.StructuredQueryOrBuilder getStructuredQueryOrBuilder();

  /**
   *
   *
   * <pre>
   * The desired maximum number of partition points.
   * The partitions may be returned across multiple pages of results.
   * The number must be positive. The actual number of partitions
   * returned may be fewer.
   *
   * For example, this may be set to one fewer than the number of parallel
   * queries to be run, or in running a data pipeline job, one fewer than the
   * number of workers or compute instances available.
   * </pre>
   *
   * <code>int64 partition_count = 3;</code>
   *
   * @return The partitionCount.
   */
  long getPartitionCount();

  /**
   *
   *
   * <pre>
   * The `next_page_token` value returned from a previous call to
   * PartitionQuery that may be used to get an additional set of results.
   * There are no ordering guarantees between sets of results. Thus, using
   * multiple sets of results will require merging the different result sets.
   *
   * For example, two subsequent calls using a page_token may return:
   *
   *  * cursor B, cursor M, cursor Q
   *  * cursor A, cursor U, cursor W
   *
   * To obtain a complete result set ordered with respect to the results of the
   * query supplied to PartitionQuery, the results sets should be merged:
   * cursor A, cursor B, cursor M, cursor Q, cursor U, cursor W
   * </pre>
   *
   * <code>string page_token = 4;</code>
   *
   * @return The pageToken.
   */
  java.lang.String getPageToken();
  /**
   *
   *
   * <pre>
   * The `next_page_token` value returned from a previous call to
   * PartitionQuery that may be used to get an additional set of results.
   * There are no ordering guarantees between sets of results. Thus, using
   * multiple sets of results will require merging the different result sets.
   *
   * For example, two subsequent calls using a page_token may return:
   *
   *  * cursor B, cursor M, cursor Q
   *  * cursor A, cursor U, cursor W
   *
   * To obtain a complete result set ordered with respect to the results of the
   * query supplied to PartitionQuery, the results sets should be merged:
   * cursor A, cursor B, cursor M, cursor Q, cursor U, cursor W
   * </pre>
   *
   * <code>string page_token = 4;</code>
   *
   * @return The bytes for pageToken.
   */
  com.google.protobuf.ByteString getPageTokenBytes();

  /**
   *
   *
   * <pre>
   * The maximum number of partitions to return in this call, subject to
   * `partition_count`.
   *
   * For example, if `partition_count` = 10 and `page_size` = 8, the first call
   * to PartitionQuery will return up to 8 partitions and a `next_page_token`
   * if more results exist. A second call to PartitionQuery will return up to
   * 2 partitions, to complete the total of 10 specified in `partition_count`.
   * </pre>
   *
   * <code>int32 page_size = 5;</code>
   *
   * @return The pageSize.
   */
  int getPageSize();

  /**
   *
   *
   * <pre>
   * Reads documents as they were at the given time.
   *
   * This must be a microsecond precision timestamp within the past one hour,
   * or if Point-in-Time Recovery is enabled, can additionally be a whole
   * minute timestamp within the past 7 days.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp read_time = 6;</code>
   *
   * @return Whether the readTime field is set.
   */
  boolean hasReadTime();
  /**
   *
   *
   * <pre>
   * Reads documents as they were at the given time.
   *
   * This must be a microsecond precision timestamp within the past one hour,
   * or if Point-in-Time Recovery is enabled, can additionally be a whole
   * minute timestamp within the past 7 days.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp read_time = 6;</code>
   *
   * @return The readTime.
   */
  com.google.protobuf.Timestamp getReadTime();
  /**
   *
   *
   * <pre>
   * Reads documents as they were at the given time.
   *
   * This must be a microsecond precision timestamp within the past one hour,
   * or if Point-in-Time Recovery is enabled, can additionally be a whole
   * minute timestamp within the past 7 days.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp read_time = 6;</code>
   */
  com.google.protobuf.TimestampOrBuilder getReadTimeOrBuilder();

  com.google.firestore.v1.PartitionQueryRequest.QueryTypeCase getQueryTypeCase();

  com.google.firestore.v1.PartitionQueryRequest.ConsistencySelectorCase
      getConsistencySelectorCase();
}
