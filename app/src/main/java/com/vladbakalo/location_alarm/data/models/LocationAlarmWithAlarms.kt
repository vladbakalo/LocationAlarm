package com.vladbakalo.location_alarm.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class LocationAlarmWithAlarms(
    @Embedded val locationAlarm: LocationAlarm,
    @Relation(
        parentColumn = "id",
        entityColumn = "location_alarm_id"
    )
    val alarms: List<Alarm>
)