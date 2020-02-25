package com.vladbakalo.location_alarm.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.vladbakalo.location_alarm.application.base.BaseDao
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.models.LocationAlarmWithAlarms
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class LocationAlarmDao: BaseDao<LocationAlarm> {

    @Query("SELECT * FROM location_alarm WHERE id is :alarmId")
    abstract fun getEntityById(alarmId: Long): LocationAlarm

    @Query("SELECT * FROM location_alarm WHERE id is :alarmId")
    abstract fun getEntityByIdRx(alarmId: Long): Single<LocationAlarm>

    @Query("SELECT * FROM location_alarm WHERE name like :name")
    abstract fun getEntityByName(name: String): LocationAlarm?

    @Query("SELECT * FROM location_alarm ORDER BY id DESC")
    abstract fun getAllSorted(): LiveData<List<LocationAlarm>>

    @Transaction
    @Query("SELECT * FROM location_alarm")
    abstract fun getAllLocationAlarmWithAlarms(): LiveData<List<LocationAlarmWithAlarms>>

    @Transaction
    @Query("SELECT * FROM location_alarm WHERE enabled is 1")
    abstract fun getAllEnabledLocationAlarmWithAlarms(): LiveData<List<LocationAlarmWithAlarms>>

    @Transaction
    @Query("SELECT * FROM location_alarm WHERE id is :alarmId")
    abstract fun getLocationAlarmsWithAlarmsById(alarmId: Long): Single<LocationAlarmWithAlarms>

    @Query("DELETE FROM location_alarm WHERE id is :alarmId")
    abstract fun delete(alarmId: Long)
}