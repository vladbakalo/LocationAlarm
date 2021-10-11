package com.vladbakalo.core.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.vladbakalo.core.db.base.BaseDao
import com.vladbakalo.core.db.models.LocationAlarmDb
import com.vladbakalo.core.db.models.LocationAlarmWithAlarmDistancesDb
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationAlarmDao: BaseDao<LocationAlarmDb> {

    @Query("SELECT * FROM location_alarm WHERE id is :alarmId")
    suspend fun getEntityById(alarmId: Long): LocationAlarmDb

    @Query("SELECT * FROM location_alarm WHERE name like :name")
    suspend fun getEntityByName(name: String): LocationAlarmDb?

    @Query("SELECT * FROM location_alarm ORDER BY id DESC")
    fun getAllSorted(): Flow<List<LocationAlarmDb>>

    @Transaction
    @Query("SELECT * FROM location_alarm")
    suspend fun getAllLocationAlarmWithAlarms(): List<LocationAlarmWithAlarmDistancesDb>

    @Transaction
    @Query("SELECT * FROM location_alarm WHERE enabled is 1")
    fun getAllEnabledLocationAlarmWithAlarms(): Flow<List<LocationAlarmWithAlarmDistancesDb>>

    @Transaction
    @Query("SELECT * FROM location_alarm WHERE id is :alarmId")
    suspend fun getLocationAlarmsWithAlarmsById(alarmId: Long): LocationAlarmWithAlarmDistancesDb

    @Query("DELETE FROM location_alarm WHERE id is :alarmId")
    suspend fun delete(alarmId: Long)
}