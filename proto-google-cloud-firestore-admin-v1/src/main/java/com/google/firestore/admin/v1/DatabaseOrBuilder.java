/*
 * Copyright 2020 Google LLC
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
// source: google/firestore/admin/v1/database.proto

package com.google.firestore.admin.v1;

public interface DatabaseOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.firestore.admin.v1.Database)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The resource name of the Database.
   * Format: `projects/{project}/databases/{database}`
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The name.
   */
  java.lang.String getName();
  /**
   *
   *
   * <pre>
   * The resource name of the Database.
   * Format: `projects/{project}/databases/{database}`
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * The location of the database. Available databases are listed at
   * https://cloud.google.com/firestore/docs/locations.
   * </pre>
   *
   * <code>string location_id = 9;</code>
   *
   * @return The locationId.
   */
  java.lang.String getLocationId();
  /**
   *
   *
   * <pre>
   * The location of the database. Available databases are listed at
   * https://cloud.google.com/firestore/docs/locations.
   * </pre>
   *
   * <code>string location_id = 9;</code>
   *
   * @return The bytes for locationId.
   */
  com.google.protobuf.ByteString getLocationIdBytes();

  /**
   *
   *
   * <pre>
   * The type of the database.
   * See https://cloud.google.com/datastore/docs/firestore-or-datastore for
   * information about how to choose.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.Database.DatabaseType type = 10;</code>
   *
   * @return The enum numeric value on the wire for type.
   */
  int getTypeValue();
  /**
   *
   *
   * <pre>
   * The type of the database.
   * See https://cloud.google.com/datastore/docs/firestore-or-datastore for
   * information about how to choose.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.Database.DatabaseType type = 10;</code>
   *
   * @return The type.
   */
  com.google.firestore.admin.v1.Database.DatabaseType getType();

  /**
   *
   *
   * <pre>
   * The concurrency control mode to use for this database.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.Database.ConcurrencyMode concurrency_mode = 15;</code>
   *
   * @return The enum numeric value on the wire for concurrencyMode.
   */
  int getConcurrencyModeValue();
  /**
   *
   *
   * <pre>
   * The concurrency control mode to use for this database.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.Database.ConcurrencyMode concurrency_mode = 15;</code>
   *
   * @return The concurrencyMode.
   */
  com.google.firestore.admin.v1.Database.ConcurrencyMode getConcurrencyMode();

  /**
   *
   *
   * <pre>
   * The App Engine integration mode to use for this database.
   * </pre>
   *
   * <code>
   * .google.firestore.admin.v1.Database.AppEngineIntegrationMode app_engine_integration_mode = 19;
   * </code>
   *
   * @return The enum numeric value on the wire for appEngineIntegrationMode.
   */
  int getAppEngineIntegrationModeValue();
  /**
   *
   *
   * <pre>
   * The App Engine integration mode to use for this database.
   * </pre>
   *
   * <code>
   * .google.firestore.admin.v1.Database.AppEngineIntegrationMode app_engine_integration_mode = 19;
   * </code>
   *
   * @return The appEngineIntegrationMode.
   */
  com.google.firestore.admin.v1.Database.AppEngineIntegrationMode getAppEngineIntegrationMode();

  /**
   *
   *
   * <pre>
   * Output only. The key_prefix for this database. This key_prefix is used, in combination
   * with the project id ("&lt;key prefix&gt;~&lt;project id&gt;") to construct the
   * application id that is returned from the Cloud Datastore APIs in Google App
   * Engine first generation runtimes.
   * This value may be empty in which case the appid to use for URL-encoded keys
   * is the project_id (eg: foo instead of v~foo).
   * </pre>
   *
   * <code>string key_prefix = 20 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The keyPrefix.
   */
  java.lang.String getKeyPrefix();
  /**
   *
   *
   * <pre>
   * Output only. The key_prefix for this database. This key_prefix is used, in combination
   * with the project id ("&lt;key prefix&gt;~&lt;project id&gt;") to construct the
   * application id that is returned from the Cloud Datastore APIs in Google App
   * Engine first generation runtimes.
   * This value may be empty in which case the appid to use for URL-encoded keys
   * is the project_id (eg: foo instead of v~foo).
   * </pre>
   *
   * <code>string key_prefix = 20 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The bytes for keyPrefix.
   */
  com.google.protobuf.ByteString getKeyPrefixBytes();

  /**
   *
   *
   * <pre>
   * This checksum is computed by the server based on the value of other
   * fields, and may be sent on update and delete requests to ensure the
   * client has an up-to-date value before proceeding.
   * </pre>
   *
   * <code>string etag = 99;</code>
   *
   * @return The etag.
   */
  java.lang.String getEtag();
  /**
   *
   *
   * <pre>
   * This checksum is computed by the server based on the value of other
   * fields, and may be sent on update and delete requests to ensure the
   * client has an up-to-date value before proceeding.
   * </pre>
   *
   * <code>string etag = 99;</code>
   *
   * @return The bytes for etag.
   */
  com.google.protobuf.ByteString getEtagBytes();
}