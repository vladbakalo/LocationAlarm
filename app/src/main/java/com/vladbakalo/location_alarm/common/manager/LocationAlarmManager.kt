package com.vladbakalo.location_alarm.common.manager

import android.location.Location
import com.vladbakalo.location_alarm.common.Logger


class LocationAlarmManager(val notificationManager: AppNotificationManager) {

    init {
        Logger.dt("Agaga", "LocationAlarmManager")
    }

    fun onNewLocation(location: Location){
        //Do work
    }
}