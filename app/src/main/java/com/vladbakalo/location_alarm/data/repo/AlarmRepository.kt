package com.vladbakalo.location_alarm.data.repo

import com.vladbakalo.location_alarm.application.base.BaseRepository
import com.vladbakalo.location_alarm.data.AppDatabase
import com.vladbakalo.location_alarm.data.models.Alarm
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.concurrent.Callable

class AlarmRepository(database: AppDatabase) : BaseRepository(database) {
    private val alarmDao = database.alarmDao()

    fun deleteAlarmByLocationAlarmId(id: Long){
        alarmDao.deleteByLocationAlarmId(id)
    }

    fun findAll(): List<Alarm>{
        return alarmDao.finAll()
    }

    fun createOrUpdateAlarmsById(alarmList: List<Alarm>, locationAlarmId: Long): Completable{
        return Completable.fromAction {
            alarmDao.deleteByLocationAlarmId(locationAlarmId)
            alarmList.forEach {
                it.locationAlarmId = locationAlarmId
            }
            alarmDao.insertAll(alarmList)
        }
    }
}