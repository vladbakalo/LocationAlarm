package com.vladbakalo.location_alarm.common.manager

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.vladbakalo.location_alarm.common.utils.NotificationUtils

class AppNotificationManager(val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationUtils.createLocationUpdatesServiceChannel())
            notificationManager.createNotificationChannel(NotificationUtils.createAlarmNotifyChannel())
        }
    }

}