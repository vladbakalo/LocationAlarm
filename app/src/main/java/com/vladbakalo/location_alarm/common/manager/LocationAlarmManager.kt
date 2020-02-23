package com.vladbakalo.location_alarm.common.manager

import android.location.Location
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.common.live_data.LastLocationLiveData


class LocationAlarmManager(private val notificationManager: AppNotificationManager,
                           private val lastLocationLiveData: LastLocationLiveData) {

    init {
        Logger.dt("Agaga", "LocationAlarmManager")
    }

    fun onNewLocation(location: Location){
        lastLocationLiveData.postValue(location)
        //Do work
    }
}