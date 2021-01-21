package com.vladbakalo.location_alarm.data.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseRepository
import com.vladbakalo.location_alarm.data.AppDatabase
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.models.LocationAlarmWithAlarmDistances
import io.reactivex.Completable
import io.reactivex.Single


open class LocationAlarmRepository(database: AppDatabase, val context: Context) :BaseRepository(database) {
    private val locationAlarmDao = database.locationAlarmDao()

    fun getAllLocationAlarm(): LiveData<List<LocationAlarm>>{
        return locationAlarmDao.getAllSorted()
    }

    fun getAllLocationAlarmsWithAlarms(): LiveData<List<LocationAlarmWithAlarmDistances>> {
        return locationAlarmDao.getAllLocationAlarmWithAlarms()
    }

    fun getAllEnabledLocationAlarmsWithAlarms(): LiveData<List<LocationAlarmWithAlarmDistances>> {
        return locationAlarmDao.getAllEnabledLocationAlarmWithAlarms()
    }

    open fun deleteLocationAlarm(id: Long): Completable{
        return Completable.fromAction {
            locationAlarmDao.delete(id)
        }
    }

    fun getLocationAlarm(id: Long): Single<LocationAlarmWithAlarmDistances> {
        return locationAlarmDao.getLocationAlarmsWithAlarmsById(id)
    }

    fun createOrUpdate(model: LocationAlarm): Single<LocationAlarm> {
        return if (model.id == 0L){
            isExists(model.name).flatMap {
                if (it) throw IllegalArgumentException(context.getString(R.string.location_alarm_name_already_exists))

                create(model)
            }
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
            locationAlarmDao.update(model)
            return@fromCallable model
        }
    }
}