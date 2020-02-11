package com.vladbakalo.location_alarm.common.manager

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.common.utils.NotificationUtils

class AppNotificationManager(val context: Context) {

    val systemNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        Logger.dt(TAG, "Init")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            systemNotificationManager.createNotificationChannel(NotificationUtils.createLocationUpdatesServiceChannel())
            systemNotificationManager.createNotificationChannel(NotificationUtils.createAlarmNotifyChannel())
        }
    }

    companion object{
        const val TAG = "AppNotificationManager"
    }
}