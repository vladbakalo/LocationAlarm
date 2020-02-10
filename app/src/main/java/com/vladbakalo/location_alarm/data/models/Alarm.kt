package com.vladbakalo.location_alarm.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey val id: Long,
    var enabled: Boolean,
    @ColumnInfo(name = "location_alarm_id") var locationAlarmId: Long,
    @ColumnInfo(name = "notify_distance") var notifyDistance: Double)