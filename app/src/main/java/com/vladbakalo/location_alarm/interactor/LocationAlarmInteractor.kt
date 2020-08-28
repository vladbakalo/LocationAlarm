package com.vladbakalo.location_alarm.interactor

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.vladbakalo.location_alarm.application.base.BaseInteractor
import com.vladbakalo.location_alarm.data.models.Alarm
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.models.LocationAlarmWithAlarms
import com.vladbakalo.location_alarm.data.repo.AlarmRepository
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import io.reactivex.Completable
import io.reactivex.Single

class LocationAlarmInteractor(private val locationAlarmRepo: LocationAlarmRepository,
                              private val alarmRepo: AlarmRepository): BaseInteractor() {

    fun deleteLocationAlarm(locationAlarmId: Long): Completable{
        return locationAlarmRepo.deleteLocationAlarm(locationAlarmId)
            .andThen { alarmRepo.deleteAlarmByLocationAlarmId(locationAlarmId) }
    }

    fun getLocationAlarmWithAlarms(locationAlarmId: Long): Single<LocationAlarmWithAlarms> {
        return locationAlarmRepo.getLocationAlarm(locationAlarmId)
    }

    fun getAllLocationAlarms(): LiveData<List<LocationAlarm>>{
        return locationAlarmRepo.getAllLocationAlarm()
    }

    fun getAllLocationAlarmWithAlarms(): LiveData<List<LocationAlarmWithAlarms>>{
        return locationAlarmRepo.getAllLocationAlarmsWithAlarms()
    }

    fun changeLocationAlarmPosition(locationAlarm: LocationAlarm, position: LatLng): Completable{
        with(position){
            locationAlarm.latitude = latitude
            locationAlarm.longitude = longitude
        }
        return updateLocationAlarm(locationAlarm)
    }

    fun changeLocationAlarmEnabledState(locationAlarm: LocationAlarm, enabled: Boolean): Completable{
        locationAlarm.enabled = enabled
        return updateLocationAlarm(locationAlarm)
    }

    private fun updateLocationAlarm(locationAlarm: LocationAlarm): Completable{
        return locationAlarmRepo.update(locationAlarm).ignoreElement()
    }

    fun createOrUpdateLocationAlarm(locationAlarm: LocationAlarm, alarmList: List<Alarm>): Completable{
        return locationAlarmRepo.createOrUpdate(locationAlarm)
            .flatMapCompletable {
                alarmRepo.createOrUpdateAlarmsById(alarmList, it.id)
            }
    }
}