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
// source: google/firestore/admin/v1/schedule.proto

// Protobuf Java Version: 3.25.5
package com.google.firestore.admin.v1;

public interface BackupScheduleOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.firestore.admin.v1.BackupSchedule)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Output only. The unique backup schedule identifier across all locations and
   * databases for the given project.
   *
   * This will be auto-assigned.
   *
   * Format is
   * `projects/{project}/databases/{database}/backupSchedules/{backup_schedule}`
   * </pre>
   *
   * <code>string name = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The name.
   */
  java.lang.String getName();
  /**
   *
   *
   * <pre>
   * Output only. The unique backup schedule identifier across all locations and
   * databases for the given project.
   *
   * This will be auto-assigned.
   *
   * Format is
   * `projects/{project}/databases/{database}/backupSchedules/{backup_schedule}`
   * </pre>
   *
   * <code>string name = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * Output only. The timestamp at which this backup schedule was created and
   * effective since.
   *
   * No backups will be created for this schedule before this time.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 3 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return Whether the createTime field is set.
   */
  boolean hasCreateTime();
  /**
   *
   *
   * <pre>
   * Output only. The timestamp at which this backup schedule was created and
   * effective since.
   *
   * No backups will be created for this schedule before this time.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 3 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return The createTime.
   */
  com.google.protobuf.Timestamp getCreateTime();
  /**
   *
   *
   * <pre>
   * Output only. The timestamp at which this backup schedule was created and
   * effective since.
   *
   * No backups will be created for this schedule before this time.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 3 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   */
  com.google.protobuf.TimestampOrBuilder getCreateTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * Output only. The timestamp at which this backup schedule was most recently
   * updated. When a backup schedule is first created, this is the same as
   * create_time.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 10 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return Whether the updateTime field is set.
   */
  boolean hasUpdateTime();
  /**
   *
   *
   * <pre>
   * Output only. The timestamp at which this backup schedule was most recently
   * updated. When a backup schedule is first created, this is the same as
   * create_time.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 10 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return The updateTime.
   */
  com.google.protobuf.Timestamp getUpdateTime();
  /**
   *
   *
   * <pre>
   * Output only. The timestamp at which this backup schedule was most recently
   * updated. When a backup schedule is first created, this is the same as
   * create_time.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 10 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   */
  com.google.protobuf.TimestampOrBuilder getUpdateTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * At what relative time in the future, compared to its creation time,
   * the backup should be deleted, e.g. keep backups for 7 days.
   *
   * The maximum supported retention period is 14 weeks.
   * </pre>
   *
   * <code>.google.protobuf.Duration retention = 6;</code>
   *
   * @return Whether the retention field is set.
   */
  boolean hasRetention();
  /**
   *
   *
   * <pre>
   * At what relative time in the future, compared to its creation time,
   * the backup should be deleted, e.g. keep backups for 7 days.
   *
   * The maximum supported retention period is 14 weeks.
   * </pre>
   *
   * <code>.google.protobuf.Duration retention = 6;</code>
   *
   * @return The retention.
   */
  com.google.protobuf.Duration getRetention();
  /**
   *
   *
   * <pre>
   * At what relative time in the future, compared to its creation time,
   * the backup should be deleted, e.g. keep backups for 7 days.
   *
   * The maximum supported retention period is 14 weeks.
   * </pre>
   *
   * <code>.google.protobuf.Duration retention = 6;</code>
   */
  com.google.protobuf.DurationOrBuilder getRetentionOrBuilder();

  /**
   *
   *
   * <pre>
   * For a schedule that runs daily.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.DailyRecurrence daily_recurrence = 7;</code>
   *
   * @return Whether the dailyRecurrence field is set.
   */
  boolean hasDailyRecurrence();
  /**
   *
   *
   * <pre>
   * For a schedule that runs daily.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.DailyRecurrence daily_recurrence = 7;</code>
   *
   * @return The dailyRecurrence.
   */
  com.google.firestore.admin.v1.DailyRecurrence getDailyRecurrence();
  /**
   *
   *
   * <pre>
   * For a schedule that runs daily.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.DailyRecurrence daily_recurrence = 7;</code>
   */
  com.google.firestore.admin.v1.DailyRecurrenceOrBuilder getDailyRecurrenceOrBuilder();

  /**
   *
   *
   * <pre>
   * For a schedule that runs weekly on a specific day.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.WeeklyRecurrence weekly_recurrence = 8;</code>
   *
   * @return Whether the weeklyRecurrence field is set.
   */
  boolean hasWeeklyRecurrence();
  /**
   *
   *
   * <pre>
   * For a schedule that runs weekly on a specific day.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.WeeklyRecurrence weekly_recurrence = 8;</code>
   *
   * @return The weeklyRecurrence.
   */
  com.google.firestore.admin.v1.WeeklyRecurrence getWeeklyRecurrence();
  /**
   *
   *
   * <pre>
   * For a schedule that runs weekly on a specific day.
   * </pre>
   *
   * <code>.google.firestore.admin.v1.WeeklyRecurrence weekly_recurrence = 8;</code>
   */
  com.google.firestore.admin.v1.WeeklyRecurrenceOrBuilder getWeeklyRecurrenceOrBuilder();

  com.google.firestore.admin.v1.BackupSchedule.RecurrenceCase getRecurrenceCase();
}
