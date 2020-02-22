package com.vladbakalo.location_alarm.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vladbakalo.location_alarm.application.base.BaseDataModel

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var enabled: Boolean,
    @ColumnInfo(name = "location_alarm_id") var locationAlarmId: Long,
    @ColumnInfo(name = "notify_distance") var notifyDistanceMeters: Double) : BaseDataModel {
    override fun getPrimaryId(): Long {
        return id
    }
}