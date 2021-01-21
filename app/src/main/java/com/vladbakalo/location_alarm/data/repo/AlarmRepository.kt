package com.vladbakalo.location_alarm.data.repo

import com.vladbakalo.location_alarm.application.base.BaseRepository
import com.vladbakalo.location_alarm.data.AppDatabase
import com.vladbakalo.location_alarm.data.models.AlarmDistance
import io.reactivex.Completable

open class AlarmRepository(database: AppDatabase) : BaseRepository(database) {
    private val alarmDao = database.alarmDao()

    open fun deleteAlarmByLocationAlarmId(id: Long): Completable{
        return Completable.fromAction{
            alarmDao.deleteByLocationAlarmId(id)
        }
    }

    fun findAll(): List<AlarmDistance> {
        return alarmDao.finAll()
    }

    fun createOrUpdateAlarmsById(alarmDistanceList: List<AlarmDistance>, locationAlarmId: Long): Completable {
        return Completable.fromAction {
            alarmDao.deleteByLocationAlarmId(locationAlarmId)
            alarmDistanceList.forEach {
                it.locationAlarmId = locationAlarmId
            }
            alarmDao.insertAll(alarmDistanceList)
        }
    }
}