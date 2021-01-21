package com.vladbakalo.location_alarm.manager

import android.location.Location
import androidx.lifecycle.Observer
import com.vladbakalo.location_alarm.common.MyLogger
import com.vladbakalo.location_alarm.data.models.LocationAlarmWithAlarmDistances
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class LocationAlarmManager(private val notificationManager: AppNotificationManager,
                           private val locationAlarmRepo: LocationAlarmRepository) {

    private val activeLocationAlarmLive = locationAlarmRepo.getAllEnabledLocationAlarmsWithAlarms()
    private val activeLocationAlarmObserver = Observer<List<LocationAlarmWithAlarmDistances>> {
        onLocationAlarmUpdate(it)
    }
    private var activeLocationAlarmList: List<LocationAlarmWithAlarmDistances> = ArrayList()

    private val locationAlarmIdsFocus = HashSet<Long>()
    private val notifiedAlarmIds = HashSet<Long>()

    private val newLocationSubject = PublishSubject.create<Location>()
    private var newLocationDisposable: Disposable? = null

    init {
        MyLogger.dt(TAG, "LocationAlarmManager")
    }

    fun start(){
        activeLocationAlarmLive.observeForever(activeLocationAlarmObserver)
        newLocationDisposable = newLocationSubject.subscribeOn(Schedulers.io())
            .throttleFirst(5, TimeUnit.SECONDS)
            .subscribe {
                checkLocationAlarmDistance(it)
            }
    }

    fun stop(){
        activeLocationAlarmLive.removeObserver(activeLocationAlarmObserver)
        newLocationDisposable?.dispose()
    }

    fun onNewLocation(location: Location){
        MyLogger.dt(TAG, "onNewLocation")
        newLocationSubject.onNext(location)
    }

    private fun checkLocationAlarmDistance(location: Location){
        var distanceToAlarm: Float
        var isAlarmAlreadyNotified: Boolean
        for (item in activeLocationAlarmList){
            MyLogger.dt(TAG, "checkLocationAlarmDistance : ${item.locationAlarm.id}")
            distanceToAlarm = item.locationAlarm.getLocation().distanceTo(location)

            MyLogger.dt(TAG, "checkLocationAlarmDistance : distance : $distanceToAlarm")
            for (alarm in item.alarmDistances) {
                isAlarmAlreadyNotified = notifiedAlarmIds.contains(alarm.id)
                MyLogger.dt(TAG, "checkLocationAlarmDistance : alarm : ${alarm.id} id")
                MyLogger.dt(TAG,
                    "checkLocationAlarmDistance : alarm : ${alarm.notifyDistanceMeters} meters")

                if (alarm.notifyDistanceMeters >= distanceToAlarm && alarm.enabled) {
                    MyLogger.dt(TAG, "checkLocationAlarmDistance : alarm in the distance!")
                    if (!isAlarmAlreadyNotified) {
                        MyLogger.dt(TAG, "checkLocationAlarmDistance : alarm was not notified")
                        notificationManager.sendAlarmNotification(item.locationAlarm, alarm)
                        notifiedAlarmIds.add(alarm.id)
                    }
                    MyLogger.dt(TAG, "checkLocationAlarmDistance : alarm was notified")
                } else {
                    if (isAlarmAlreadyNotified) {
                        MyLogger.dt(TAG, "checkLocationAlarmDistance : remove notification")
                        notificationManager.removeAlarmNotification(item.locationAlarm)
                        notifiedAlarmIds.remove(alarm.id)
                    }
                }
            }
        }
    }

    private fun onLocationAlarmUpdate(data: List<LocationAlarmWithAlarmDistances>) {
        activeLocationAlarmList = data
    }

    companion object{
        private const val TAG = "LocationAlarmManager"
    }
}