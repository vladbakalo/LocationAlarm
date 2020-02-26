package com.vladbakalo.location_alarm.manager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.data.models.Alarm
import com.vladbakalo.location_alarm.data.models.LocationAlarm


class AppNotificationManager(val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    init {
        Logger.dt(TAG, "Init")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(createLocationUpdatesServiceChannel())
            notificationManager.createNotificationChannel(createAlarmNotifyChannel())
        }
    }

    fun sendAlarmNotification(locationAlarm: LocationAlarm, alarm: Alarm){
        val builder = NotificationCompat.Builder(context, ENotificationChannel.ALARM_NOTIFY.channelId)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(String.format(context.getString(R.string.alarm_notification_tittle_distance),
                locationAlarm.name, alarm.notifyDistanceMeters))
            .setContentText(locationAlarm.note)
            .setShowWhen(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(defaultSoundUri)
            .setCategory(Notification.CATEGORY_ALARM)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            builder.priority = Notification.PRIORITY_HIGH
        }

        notificationManager.notify(getLocationAlarmNotifyId(locationAlarm), builder.build())
    }

    fun removeAlarmNotification(locationAlarm: LocationAlarm){
        notificationManager.cancel(getLocationAlarmNotifyId(locationAlarm))
    }

    fun getLocationUpdatesServiceNotification(tittle: String,
                                              action: NotificationCompat.Action): Notification {
        val builder =
            NotificationCompat.Builder(context, ENotificationChannel.LOCATION_UPDATES.channelId)
                .addAction(action)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(tittle)
                .setNotificationSilent()
                .setShowWhen(false)
                .setCategory(Notification.CATEGORY_SERVICE)

        return builder.build()
    }

    fun updateLocationUpdatesServiceNotification(notificationId: Int,
                                                 tittle: String,
                                                 action: NotificationCompat.Action){
        notificationManager.notify(notificationId, getLocationUpdatesServiceNotification(tittle, action))
    }

    private fun getLocationAlarmNotifyId(locationAlarm: LocationAlarm): Int{
        return (locationAlarm.id * 100).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createLocationUpdatesServiceChannel(): NotificationChannel {
        return createNotificationChannel(
            ENotificationChannel.LOCATION_UPDATES.channelId,
            ENotificationChannel.LOCATION_UPDATES.channelName, NotificationManager.IMPORTANCE_LOW)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createAlarmNotifyChannel(): NotificationChannel {
        return createNotificationChannel(ENotificationChannel.ALARM_NOTIFY.channelId,
            ENotificationChannel.ALARM_NOTIFY.channelName, NotificationManager.IMPORTANCE_HIGH)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String,
                                          channelName: String,
                                          importance: Int): NotificationChannel {
        return NotificationChannel(channelId, channelName, importance).apply {
            enableLights(true)
            enableVibration(true)
            lightColor = Color.YELLOW
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
    }

    enum class ENotificationChannel(val channelId: String, val channelName: String) {
        LOCATION_UPDATES("APP_CHANNEL_1", "Location-Alarm service"),
        ALARM_NOTIFY("APP_CHANNEL_2", "Alarm Notifications");
    }

    companion object{
        const val TAG = "AppNotificationManager"
    }
}