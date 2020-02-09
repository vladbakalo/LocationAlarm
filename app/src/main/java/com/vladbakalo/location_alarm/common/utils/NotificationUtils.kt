package com.vladbakalo.location_alarm.common.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.service.LocationUpdatesService

object NotificationUtils {

    fun getLocationUpdatesNotification(context: Context, action: NotificationCompat.Action): Notification{
        val builder = NotificationCompat.Builder(context, ENotificationChannel.LOCATION_UPDATES.channelId)
            .addAction(action)
            .setOngoing(true)
            .setContentTitle(context.getString(R.string.location_alarm_running))

        return builder.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createServiceChannel(channelId: String,
                                            channelName: String,
                                            isImportant: Boolean): NotificationChannel{
        val importance = if (isImportant)
            NotificationManager.IMPORTANCE_HIGH else NotificationManager.IMPORTANCE_DEFAULT
        val lockScreenVisibility = if (isImportant)
            Notification.VISIBILITY_PRIVATE else Notification.VISIBILITY_PUBLIC

        return NotificationChannel(channelId, channelName, importance).apply {
            enableLights(true)
            enableVibration(true)
            lightColor = Color.YELLOW
            lockscreenVisibility = lockScreenVisibility
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createLocationUpdatesServiceChannel(): NotificationChannel{
        return createServiceChannel(ENotificationChannel.LOCATION_UPDATES.channelId,
            ENotificationChannel.LOCATION_UPDATES.channelName, true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createAlarmNotifyChannel(): NotificationChannel{
        return createServiceChannel(ENotificationChannel.ALARM_NOTIFY.channelId,
            ENotificationChannel.ALARM_NOTIFY.channelName, false)
    }

    enum class ENotificationChannel(val channelId: String,
                                    val channelName: String) {
        LOCATION_UPDATES("APP_CHANNEL_1", "LOCATION UPDATES CHANNEL"),
        ALARM_NOTIFY("APP_CHANNEL_2", "ALARM NOTIFY CHANNEL");

    }
}