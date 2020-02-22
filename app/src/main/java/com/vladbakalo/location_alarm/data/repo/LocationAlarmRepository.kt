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

    fun getLocationAlarm(id: Long): Flowable<LocationAlarmWithAlarms>{
        return locationAlarmDao.getLocationAlarmsWithAlarmsById(id)
    }

    fun createOrUpdate(model: LocationAlarm): Flowable<LocationAlarm> {
        return Single.fromCallable {
            val isExists = locationAlarmDao.getEntityByName(model.name) != null
            if (isExists){
                throw IllegalArgumentException(StringUtils.getString(R.string.location_alarm_name_already_exists))
            }
            return@fromCallable model
        }.flatMapPublisher  {
            return@flatMapPublisher if (it.id == 0L){
                create(it)
            } else {
                update(it)
            }
        }

    }

    private fun create(model: LocationAlarm): Flowable<LocationAlarm>{
        return Single.fromCallable{
            return@fromCallable locationAlarmDao.insertRx(model)
        }
            .toFlowable()
            .flatMap { locationAlarmDao.getEntityByIdRx(it) }
    }

    fun update(model: LocationAlarm): Flowable<LocationAlarm>{
        return Flowable.fromCallable{
            locationAlarmDao.updateRx(model)
            return@fromCallable model
        }
    }
}