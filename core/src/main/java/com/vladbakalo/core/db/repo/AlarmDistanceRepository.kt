package com.vladbakalo.core.db.repo

import com.vladbakalo.core.db.base.BaseRepository
import com.vladbakalo.core.db.dao.AlarmDistanceDao
import com.vladbakalo.core.db.models.AlarmDistanceDb

open class AlarmDistanceRepository(private val alarmDistanceDao: AlarmDistanceDao) : BaseRepository() {

    suspend fun deleteAlarmDistancesByLocationAlarmId(id: Long){
        alarmDistanceDao.deleteAlarmDistancesByLocationAlarmId(id)
    }

    suspend fun createOrUpdateAlarmsById(alarmDistanceDbList: List<AlarmDistanceDb>, locationAlarmId: Long) {
        alarmDistanceDao.deleteAlarmDistancesByLocationAlarmId(locationAlarmId)
        alarmDistanceDbList.forEach {
            it.locationAlarmId = locationAlarmId
        }
        alarmDistanceDao.insertAll(alarmDistanceDbList)
    }
}