package com.vladbakalo.location_alarm.common.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.vladbakalo.location_alarm.R

object NotificationUtils {

    fun getLocationUpdatesNotification(context: Context,
                                       tittle: String,
                                       action: NotificationCompat.Action): Notification {
        val builder =
            NotificationCompat.Builder(context, ENotificationChannel.LOCATION_UPDATES.channelId)
                .addAction(action)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_main_pin)
                .setContentTitle(tittle)
                .setNotificationSilent()
                .setShowWhen(false)
                .setCategory(Notification.CATEGORY_SERVICE)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            builder.priority = Notification.PRIORITY_HIGH
        }

        return builder.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createServiceChannel(channelId: String,
                                     channelName: String,
                                     isImportant: Boolean): NotificationChannel {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val lockScreenVisibility =
            if (isImportant) Notification.VISIBILITY_PUBLIC else Notification.VISIBILITY_PRIVATE

        return NotificationChannel(channelId, channelName, importance).apply {
            enableLights(true)
            enableVibration(true)
            lightColor = Color.YELLOW
            lockscreenVisibility = lockScreenVisibility
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createLocationUpdatesServiceChannel(): NotificationChannel {
        return createServiceChannel(ENotificationChannel.LOCATION_UPDATES.channelId,
            ENotificationChannel.LOCATION_UPDATES.channelName, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createAlarmNotifyChannel(): NotificationChannel {
        return createServiceChannel(ENotificationChannel.ALARM_NOTIFY.channelId,
            ENotificationChannel.ALARM_NOTIFY.channelName, true)
    }

    enum class ENotificationChannel(val channelId: String, val channelName: String) {
        LOCATION_UPDATES("APP_CHANNEL_1", "Location-Alarm service"),
        ALARM_NOTIFY("APP_CHANNEL_2", "Alarm Notifications");
    }
}