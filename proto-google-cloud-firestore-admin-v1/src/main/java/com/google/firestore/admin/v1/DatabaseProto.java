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
// source: google/firestore/admin/v1/database.proto

// Protobuf Java Version: 3.25.3
package com.google.firestore.admin.v1;

public final class DatabaseProto {
  private DatabaseProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_firestore_admin_v1_Database_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_firestore_admin_v1_Database_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n(google/firestore/admin/v1/database.pro"
          + "to\022\031google.firestore.admin.v1\032\037google/ap"
          + "i/field_behavior.proto\032\031google/api/resou"
          + "rce.proto\032\036google/protobuf/duration.prot"
          + "o\032\037google/protobuf/timestamp.proto\"\271\013\n\010D"
          + "atabase\022\014\n\004name\030\001 \001(\t\022\020\n\003uid\030\003 \001(\tB\003\340A\003\022"
          + "4\n\013create_time\030\005 \001(\0132\032.google.protobuf.T"
          + "imestampB\003\340A\003\0224\n\013update_time\030\006 \001(\0132\032.goo"
          + "gle.protobuf.TimestampB\003\340A\003\022\023\n\013location_"
          + "id\030\t \001(\t\022>\n\004type\030\n \001(\01620.google.firestor"
          + "e.admin.v1.Database.DatabaseType\022M\n\020conc"
          + "urrency_mode\030\017 \001(\01623.google.firestore.ad"
          + "min.v1.Database.ConcurrencyMode\022@\n\030versi"
          + "on_retention_period\030\021 \001(\0132\031.google.proto"
          + "buf.DurationB\003\340A\003\022>\n\025earliest_version_ti"
          + "me\030\022 \001(\0132\032.google.protobuf.TimestampB\003\340A"
          + "\003\022l\n!point_in_time_recovery_enablement\030\025"
          + " \001(\0162A.google.firestore.admin.v1.Databas"
          + "e.PointInTimeRecoveryEnablement\022a\n\033app_e"
          + "ngine_integration_mode\030\023 \001(\0162<.google.fi"
          + "restore.admin.v1.Database.AppEngineInteg"
          + "rationMode\022\027\n\nkey_prefix\030\024 \001(\tB\003\340A\003\022Z\n\027d"
          + "elete_protection_state\030\026 \001(\01629.google.fi"
          + "restore.admin.v1.Database.DeleteProtecti"
          + "onState\022\014\n\004etag\030c \001(\t\"W\n\014DatabaseType\022\035\n"
          + "\031DATABASE_TYPE_UNSPECIFIED\020\000\022\024\n\020FIRESTOR"
          + "E_NATIVE\020\001\022\022\n\016DATASTORE_MODE\020\002\"w\n\017Concur"
          + "rencyMode\022 \n\034CONCURRENCY_MODE_UNSPECIFIE"
          + "D\020\000\022\016\n\nOPTIMISTIC\020\001\022\017\n\013PESSIMISTIC\020\002\022!\n\035"
          + "OPTIMISTIC_WITH_ENTITY_GROUPS\020\003\"\233\001\n\035Poin"
          + "tInTimeRecoveryEnablement\0221\n-POINT_IN_TI"
          + "ME_RECOVERY_ENABLEMENT_UNSPECIFIED\020\000\022\"\n\036"
          + "POINT_IN_TIME_RECOVERY_ENABLED\020\001\022#\n\037POIN"
          + "T_IN_TIME_RECOVERY_DISABLED\020\002\"b\n\030AppEngi"
          + "neIntegrationMode\022+\n\'APP_ENGINE_INTEGRAT"
          + "ION_MODE_UNSPECIFIED\020\000\022\013\n\007ENABLED\020\001\022\014\n\010D"
          + "ISABLED\020\002\"\177\n\025DeleteProtectionState\022\'\n#DE"
          + "LETE_PROTECTION_STATE_UNSPECIFIED\020\000\022\036\n\032D"
          + "ELETE_PROTECTION_DISABLED\020\001\022\035\n\031DELETE_PR"
          + "OTECTION_ENABLED\020\002:R\352AO\n!firestore.googl"
          + "eapis.com/Database\022\'projects/{project}/d"
          + "atabases/{database}R\001\001B\334\001\n\035com.google.fi"
          + "restore.admin.v1B\rDatabaseProtoP\001Z9cloud"
          + ".google.com/go/firestore/apiv1/admin/adm"
          + "inpb;adminpb\242\002\004GCFS\252\002\037Google.Cloud.Fires"
          + "tore.Admin.V1\312\002\037Google\\Cloud\\Firestore\\A"
          + "dmin\\V1\352\002#Google::Cloud::Firestore::Admi"
          + "n::V1b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.api.FieldBehaviorProto.getDescriptor(),
              com.google.api.ResourceProto.getDescriptor(),
              com.google.protobuf.DurationProto.getDescriptor(),
              com.google.protobuf.TimestampProto.getDescriptor(),
            });
    internal_static_google_firestore_admin_v1_Database_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_google_firestore_admin_v1_Database_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_firestore_admin_v1_Database_descriptor,
            new java.lang.String[] {
              "Name",
              "Uid",
              "CreateTime",
              "UpdateTime",
              "LocationId",
              "Type",
              "ConcurrencyMode",
              "VersionRetentionPeriod",
              "EarliestVersionTime",
              "PointInTimeRecoveryEnablement",
              "AppEngineIntegrationMode",
              "KeyPrefix",
              "DeleteProtectionState",
              "Etag",
            });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.google.api.FieldBehaviorProto.fieldBehavior);
    registry.add(com.google.api.ResourceProto.resource);
    com.google.protobuf.Descriptors.FileDescriptor.internalUpdateFileDescriptor(
        descriptor, registry);
    com.google.api.FieldBehaviorProto.getDescriptor();
    com.google.api.ResourceProto.getDescriptor();
    com.google.protobuf.DurationProto.getDescriptor();
    com.google.protobuf.TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
