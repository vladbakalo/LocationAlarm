package com.vladbakalo.location_alarm.common.manager

import android.location.Location


class LocationAlarmManager(val notificationManager: AppNotificationManager) {

    fun onNewLocation(location: Location){
        //Do work
    }
}