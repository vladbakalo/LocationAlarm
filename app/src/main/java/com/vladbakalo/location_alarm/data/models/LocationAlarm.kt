package com.vladbakalo.location_alarm.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_alarm")
data class LocationAlarm(
    @PrimaryKey var id: Long,
    var name: String,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var note: String,
    var enabled: Boolean = true)