package com.vladbakalo.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vladbakalo.core.db.dao.AlarmDistanceDao
import com.vladbakalo.core.db.dao.LocationAlarmDao
import com.vladbakalo.core.db.models.AlarmDistanceDb
import com.vladbakalo.core.db.models.LocationAlarmDb

@Database(entities = [LocationAlarmDb::class, AlarmDistanceDb::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getLocationAlarmDao(): LocationAlarmDao

    abstract fun getAlarmDistanceDao(): AlarmDistanceDao
}