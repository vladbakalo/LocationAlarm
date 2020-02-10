package com.vladbakalo.location_alarm.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.models.LocationAlarmWithAlarms

@Dao
interface LocationAlarmDao {

    @Query("SELECT * FROM location_alarm ORDER BY id DESC")
    fun getAllSorted(): LiveData<LocationAlarm>

    @Transaction
    @Query("SELECT * FROM location_alarm")
    fun getLocationAlarmsWithAlarms(): LiveData<LocationAlarmWithAlarms>
}