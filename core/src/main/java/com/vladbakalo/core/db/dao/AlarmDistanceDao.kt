package com.vladbakalo.core.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.vladbakalo.core.db.base.BaseDao
import com.vladbakalo.core.db.models.AlarmDistanceDb

@Dao
interface AlarmDistanceDao :BaseDao<AlarmDistanceDb> {

    @Query("SELECT * FROM alarm_distance")
    suspend fun finAll(): List<AlarmDistanceDb>

    @Query("SELECT * FROM alarm_distance WHERE location_alarm_id is :locationAlarmId")
    suspend fun finAllByLocationAlarmId(locationAlarmId: Long): List<AlarmDistanceDb>

    @Query("DELETE FROM alarm_distance WHERE location_alarm_id is :alarmId")
    suspend fun deleteAlarmDistancesByLocationAlarmId(alarmId: Long)

    @Query("DELETE FROM alarm_distance WHERE id is :id")
    suspend fun delete(id: Long)
}