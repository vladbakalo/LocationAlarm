package com.vladbakalo.location_alarm.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.vladbakalo.location_alarm.application.base.BaseDao
import com.vladbakalo.location_alarm.data.models.Alarm

@Dao
abstract class AlarmDao :BaseDao<Alarm> {

    @Query("DELETE FROM alarm WHERE location_alarm_id is :alarmId")
    abstract fun deleteByLocationAlarmId(alarmId: Long)

    @Query("DELETE FROM alarm WHERE id is :id")
    abstract fun delete(id: Long)
}