package com.vladbakalo.location_alarm.interactor

import com.vladbakalo.location_alarm.application.base.BaseInteractor
import com.vladbakalo.location_alarm.data.models.Alarm
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.models.LocationAlarmWithAlarms
import com.vladbakalo.location_alarm.data.repo.AlarmRepository
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class LocationAlarmCreateEditInteractor(private val locationAlarmRepo: LocationAlarmRepository,
                                        private val alarmRepo: AlarmRepository): BaseInteractor() {

    fun deleteLocationAlarm(locationAlarmId: Long){
        locationAlarmRepo.deleteLocationAlarm(locationAlarmId)
        alarmRepo.deleteAlarmByLocationAlarmId(locationAlarmId)
    }

    fun getLocationAlarm(locationAlarmId: Long): Flowable<LocationAlarmWithAlarms>{
        return locationAlarmRepo.getLocationAlarm(locationAlarmId)
    }

    fun createOrUpdateLocationAlarm(locationAlarm: LocationAlarm, alarmList: List<Alarm>): Completable{
        return locationAlarmRepo.createOrUpdate(locationAlarm)
            .firstOrError()
            .flatMapCompletable {
                alarmRepo.createOrUpdateAlarmsById(alarmList, it.id)
            }
    }
}