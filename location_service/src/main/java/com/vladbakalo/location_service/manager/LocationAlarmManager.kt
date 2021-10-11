package com.vladbakalo.location_service.manager

import android.location.Location
import com.vladbakalo.core.common.MyLogger
import com.vladbakalo.core.db.models.LocationAlarmWithAlarmDistancesDb
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import java.util.concurrent.TimeUnit


class LocationAlarmManager constructor(private val notificationManager: AppNotificationManager,
                                               locationAlarmRepo: LocationAlarmRepository,
                                               coroutineDispatcher: CoroutineDispatcher) {
    private val activeLocationAlarmFlow = locationAlarmRepo.getAllEnabledLocationAlarmsWithAlarms()
    private val newLocationMutableStateFlow = MutableStateFlow<Location?>(null)
    private var activeLocationAlarmListDb: List<LocationAlarmWithAlarmDistancesDb> = ArrayList()

    private val notifiedAlarmIds = HashSet<Long>()

    private val coroutineScope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private var activeLocationJob: Job? = null
    private var newLocationJob: Job? = null

    init {
        MyLogger.dt(TAG, "LocationAlarmManager")
    }

    fun start() {
        if (newLocationJob != null) newLocationJob?.cancel()
        if (activeLocationJob != null) activeLocationJob?.cancel()

        newLocationJob = coroutineScope.launch {
            activeLocationAlarmFlow.collect {
                onLocationAlarmUpdate(it)
            }
        }

        activeLocationJob = coroutineScope.launch {
            newLocationMutableStateFlow.debounce(TimeUnit.SECONDS.toMillis(5))
                .collect {
                    checkLocationAlarmDistance(it ?: return@collect)
                }
        }
    }

    fun stop() {
        newLocationJob?.cancel()
        newLocationJob = null

        activeLocationJob?.cancel()
        activeLocationJob = null
    }

    fun onNewLocation(location: Location) {
        MyLogger.dt(TAG, "onNewLocation")
        coroutineScope.launch {
            newLocationMutableStateFlow.emit(location)
        }
    }

    private fun checkLocationAlarmDistance(location: Location) {
        var distanceToAlarm: Float
        var isAlarmAlreadyNotified: Boolean
        for (item in activeLocationAlarmListDb) {
            MyLogger.dt(TAG, "checkLocationAlarmDistance : ${item.locationAlarmDb.id}")
            distanceToAlarm = item.locationAlarmDb.getLocation()
                .distanceTo(location)

            MyLogger.dt(TAG, "checkLocationAlarmDistance : distance : $distanceToAlarm")
            for (alarm in item.alarmDistanceList) {
                isAlarmAlreadyNotified = notifiedAlarmIds.contains(alarm.id)
                MyLogger.dt(TAG, "checkLocationAlarmDistance : alarm : ${alarm.id} id")
                MyLogger.dt(TAG,
                    "checkLocationAlarmDistance : alarm : ${alarm.notifyDistanceMeters} meters")

                if (alarm.notifyDistanceMeters >= distanceToAlarm && alarm.enabled) {
                    MyLogger.dt(TAG, "checkLocationAlarmDistance : alarm in the distance!")
                    if (!isAlarmAlreadyNotified) {
                        MyLogger.dt(TAG, "checkLocationAlarmDistance : alarm was not notified")
                        notificationManager.sendAlarmNotification(item.locationAlarmDb, alarm)
                        notifiedAlarmIds.add(alarm.id)
                    }
                    MyLogger.dt(TAG, "checkLocationAlarmDistance : alarm was notified")
                } else {
                    if (isAlarmAlreadyNotified) {
                        MyLogger.dt(TAG, "checkLocationAlarmDistance : remove notification")
                        notificationManager.removeAlarmNotification(item.locationAlarmDb)
                        notifiedAlarmIds.remove(alarm.id)
                    }
                }
            }
        }
    }

    private fun onLocationAlarmUpdate(data: List<LocationAlarmWithAlarmDistancesDb>) {
        activeLocationAlarmListDb = data
    }

    companion object {
        private const val TAG = "LocationAlarmManager"
    }
}