package com.vladbakalo.location_alarm.data.repo

import androidx.lifecycle.LiveData
import com.vladbakalo.location_alarm.data.AppDatabase
import com.vladbakalo.location_alarm.data.models.LocationAlarm


class LocationAlarmRepository(var database: AppDatabase) {

    fun getAllLocationAlarm(): LiveData<List<LocationAlarm>>{
        return database.locationAlarmDao().getAllSorted()
    }
}