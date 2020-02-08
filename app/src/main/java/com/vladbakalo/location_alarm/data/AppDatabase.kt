package com.vladbakalo.location_alarm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vladbakalo.location_alarm.data.dao.AlarmDao
import com.vladbakalo.location_alarm.data.dao.LocationAlarmDao
import com.vladbakalo.location_alarm.data.models.Alarm
import com.vladbakalo.location_alarm.data.models.LocationAlarm

@Database(entities = [LocationAlarm::class, Alarm::class],
    version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun locationAlarmDao(): LocationAlarmDao
    abstract fun alarmDao(): AlarmDao
}