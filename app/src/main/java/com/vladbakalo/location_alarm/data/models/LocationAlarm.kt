package com.vladbakalo.location_alarm.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vladbakalo.location_alarm.application.base.BaseDataModel

@Entity(tableName = "location_alarm")
data class LocationAlarm(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var name: String,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var note: String,
    var enabled: Boolean = true): BaseDataModel {

    override fun getPrimaryId(): Long {
        return id
    }
}