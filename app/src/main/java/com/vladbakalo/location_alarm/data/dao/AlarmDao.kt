package com.vladbakalo.location_alarm.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.vladbakalo.location_alarm.application.base.BaseDao
import com.vladbakalo.location_alarm.data.models.AlarmDistance

@Dao
abstract class AlarmDao :BaseDao<AlarmDistance> {

    @Query("SELECT * FROM alarm_distance")
    abstract fun finAll(): List<AlarmDistance>

    @Query("SELECT * FROM alarm_distance WHERE location_alarm_id is :locationAlarmId")
    abstract fun finAllByLocationAlarmId(locationAlarmId: Long): List<AlarmDistance>

    @Query("DELETE FROM alarm_distance WHERE location_alarm_id is :alarmId")
    abstract fun deleteByLocationAlarmId(alarmId: Long)

    @Query("DELETE FROM alarm_distance WHERE id is :id")
    abstract fun delete(id: Long)
}