package com.vladbakalo.core.db.models

import androidx.room.Embedded
import androidx.room.Relation

data class LocationAlarmWithAlarmDistancesDb(@Embedded val locationAlarmDb: LocationAlarmDb,
                                             @Relation(parentColumn = "id",
                                               entityColumn = "location_alarm_id") val alarmDistanceList: List<AlarmDistanceDb>)