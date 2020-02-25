package com.vladbakalo.location_alarm.manager

import android.location.Location
import androidx.lifecycle.Observer
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.data.models.LocationAlarmWithAlarms
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class LocationAlarmManager(private val notificationManager: AppNotificationManager,
                           private val locationAlarmRepo: LocationAlarmRepository) {

    private val activeLocationAlarmLive = locationAlarmRepo.getAllEnabledLocationAlarmsWithAlarms()
    private val activeLocationAlarmObserver = Observer<List<LocationAlarmWithAlarms>> {
        onLocationAlarmUpdate(it)
    }
    private var activeLocationAlarmList: List<LocationAlarmWithAlarms> = ArrayList()

    private val locationAlarmIdsFocus = HashSet<Long>()
    private val notifiedAlarmIds = HashSet<Long>()

    private val newLocationSubject = PublishSubject.create<Location>()
    private var newLocationDisposable: Disposable? = null

    init {
        Logger.dt(TAG, "LocationAlarmManager")
    }

    fun start(){
        activeLocationAlarmLive.observeForever(activeLocationAlarmObserver)
        newLocationDisposable = newLocationSubject.subscribeOn(Schedulers.io())
            .throttleFirst(10, TimeUnit.SECONDS)
            .subscribe {
                checkLocationAlarmDistance(it)
            }
    }

    fun stop(){
        activeLocationAlarmLive.removeObserver(activeLocationAlarmObserver)
        newLocationDisposable?.dispose()
    }

    fun onNewLocation(location: Location){
        Logger.dt(TAG, "onNewLocation")
        newLocationSubject.onNext(location)
    }

    private fun checkLocationAlarmDistance(location: Location){
        var distanceToAlarm: Float
        var isAlarmAlreadyNotified: Boolean
        for (item in activeLocationAlarmList){
            Logger.dt(TAG, "checkLocationAlarmDistance : ${item.locationAlarm.id}")
            distanceToAlarm = item.locationAlarm.getLocation().distanceTo(location)

            Logger.dt(TAG, "checkLocationAlarmDistance : distance : $distanceToAlarm")
            for (alarm in item.alarms){
                isAlarmAlreadyNotified = notifiedAlarmIds.contains(alarm.id)
                Logger.dt(TAG, "checkLocationAlarmDistance : alarm : ${alarm.id} id")
                Logger.dt(TAG, "checkLocationAlarmDistance : alarm : ${alarm.notifyDistanceMeters} meters")

                if (alarm.notifyDistanceMeters >= distanceToAlarm) {
                    Logger.dt(TAG, "checkLocationAlarmDistance : alarm in the distance!")
                    if (!isAlarmAlreadyNotified) {
                        Logger.dt(TAG, "checkLocationAlarmDistance : alarm was not notified")
                        notificationManager.sendAlarmNotification(item.locationAlarm, alarm)
                        notifiedAlarmIds.add(alarm.id)
                    }
                    Logger.dt(TAG, "checkLocationAlarmDistance : alarm was notified")
                } else {
                    if (isAlarmAlreadyNotified) {
                        Logger.dt(TAG, "checkLocationAlarmDistance : remove notification")
                        notificationManager.removeAlarmNotification(item.locationAlarm)
                        notifiedAlarmIds.remove(alarm.id)
                    }
                }
            }
        }
    }

    private fun onLocationAlarmUpdate(data: List<LocationAlarmWithAlarms>){
        activeLocationAlarmList = data
    }

    companion object{
        private const val TAG = "LocationAlarmManager"
    }
}