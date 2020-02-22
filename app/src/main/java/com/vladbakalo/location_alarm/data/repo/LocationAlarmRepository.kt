package com.vladbakalo.location_alarm.data.repo

import androidx.lifecycle.LiveData
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseRepository
import com.vladbakalo.location_alarm.common.utils.StringUtils
import com.vladbakalo.location_alarm.data.AppDatabase
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.models.LocationAlarmWithAlarms
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.lang.IllegalArgumentException


class LocationAlarmRepository(database: AppDatabase) :BaseRepository(database) {
    private val locationAlarmDao = database.locationAlarmDao()

    fun getAllLocationAlarm(): LiveData<List<LocationAlarm>>{
        return locationAlarmDao.getAllSorted()
    }

    fun deleteLocationAlarm(id: Long): Completable{
        return Completable.fromAction {
            locationAlarmDao.delete(id)
        }
    }

    fun getLocationAlarm(id: Long): Single<LocationAlarmWithAlarms>{
        return locationAlarmDao.getLocationAlarmsWithAlarmsById(id)
    }

    fun createOrUpdate(model: LocationAlarm): Single<LocationAlarm> {
        return if (model.id == 0L){
            create(model)
        } else {
            update(model)
        }
    }

    fun isExists(name: String): Single<Boolean>{
        return Single.fromCallable {
            locationAlarmDao.getEntityByName(name) != null
        }
    }

    private fun create(model: LocationAlarm): Single<LocationAlarm>{
        return Single.fromCallable{
            return@fromCallable locationAlarmDao.insertRx(model)
        }.flatMap { locationAlarmDao.getEntityByIdRx(it) }
    }

    fun update(model: LocationAlarm): Single<LocationAlarm>{
        return Single.fromCallable{
            locationAlarmDao.updateRx(model)
            return@fromCallable model
        }
    }
}