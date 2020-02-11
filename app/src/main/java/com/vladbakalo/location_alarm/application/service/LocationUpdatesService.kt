package com.vladbakalo.location_alarm.application.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.common.manager.AppNotificationManager
import com.vladbakalo.location_alarm.common.manager.LocationAlarmManager
import com.vladbakalo.location_alarm.common.utils.NotificationUtils
import com.vladbakalo.location_alarm.common.utils.PermissionUtils
import dagger.android.DaggerService
import javax.inject.Inject

class LocationUpdatesService :DaggerService() {

    @Inject
    lateinit var locationAlarmManager: LocationAlarmManager

    @Inject
    lateinit var notificationManager: AppNotificationManager

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val binder = LocalBinder()

    private var isConfigurationChanging: Boolean = false
    private var isLocationUpdatesEnabled: Boolean = false

    override fun onCreate() {
        super.onCreate()
        Logger.dt(TAG, "onCreate")
        isRunning = true

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object :LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                super.onLocationResult(result)

                onNewLocation(result?.lastLocation)
            }

            override fun onLocationAvailability(result: LocationAvailability?) {
                super.onLocationAvailability(result)
                Logger.dt(TAG, "onLocationAvailability : ${result?.isLocationAvailable}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.dt(TAG, "onDestroy")
        isRunning = false
    }

    override fun onBind(intent: Intent): IBinder? {
        Logger.dt(TAG, "onBind")
//        stopForeground(true)

        isConfigurationChanging = false
        return binder
    }

    override fun onRebind(intent: Intent?) {
        Logger.dt(TAG, "onRebind")
//        stopForeground(true)
        isConfigurationChanging = false

        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Logger.dt(TAG, "onUnbind")
        return true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.dt(TAG, "onStartCommand")

        val isToggleServiceState =
            intent?.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false) ?: false

        if (isToggleServiceState) {
            isLocationUpdatesEnabled = isLocationUpdatesEnabled.not()

            if (isLocationUpdatesEnabled) {
                requestLocationUpdatesWithCheck()
            } else {
                removeLocationUpdates()
            }
            updateNotification()
        }

        return START_NOT_STICKY
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Logger.dt(TAG, "onConfigurationChanged")
        isConfigurationChanging = true
    }

    private fun onNewLocation(location: Location?) {
        if (location == null) {
            Logger.dt(TAG, "onNewLocation : new location is null")
            return
        }
        Logger.dt(TAG, "onNewLocation : $location")

        //Process new location
        locationAlarmManager.onNewLocation(location)
    }

    private fun requestLocationUpdatesWithCheck() {
        if (PermissionUtils.checkLocationPermission(this)) {
            requestLocationUpdates()
        } else {
            stopForeground(true)
        }
    }

    public fun requestLocationUpdates() {
        if (isLocationUpdatesEnabled){
            Logger.dt(TAG, "requestLocationUpdates : already requested")
            return
        }
        Logger.dt(TAG, "requestLocationUpdates")

        isLocationUpdatesEnabled = true
        try {
            fusedLocationClient.requestLocationUpdates(createLocationRequest(), locationCallback,
                Looper.myLooper())
        } catch (e: SecurityException) {
            Logger.dt(TAG, "requestLocationUpdates error : ${e.message}")
        }
        beginForegroundMode()
    }

    private fun removeLocationUpdates() {
        Logger.dt(TAG, "requestLocationUpdates")
        isLocationUpdatesEnabled = false
        try {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        } catch (e: SecurityException) {
            Logger.dt(TAG, "requestLocationUpdates error : ${e.message}")
        }
    }

    private fun createLocationRequest(): LocationRequest? {
        return LocationRequest.create()?.apply {
                interval = INTERVAL
                fastestInterval = FASTEST_INTERVAL
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
    }

    private fun getPendingAction(): NotificationCompat.Action {
        val actionText =
            this.getString(if (isLocationUpdatesEnabled) R.string.pause else R.string.resume).toUpperCase()
        val intent = Intent(this, LocationUpdatesService::class.java)
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true)

        val servicePendingIntent =
            PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Action(R.drawable.ic_alarm, actionText, servicePendingIntent)
    }

    private fun beginForegroundMode() {
        Logger.dt(TAG, "requestLocationUpdates")
        startForeground(NOTIFICATION_ID,
            NotificationUtils.getLocationUpdatesNotification(this, getNotificationTittle(), getPendingAction()))
    }

    private fun updateNotification() {
        notificationManager.systemNotificationManager.notify(NOTIFICATION_ID,
            NotificationUtils.getLocationUpdatesNotification(this, getNotificationTittle(), getPendingAction()))
    }

    private fun getNotificationTittle(): String {
        return if (isLocationUpdatesEnabled) getString(
            R.string.location_alarm_running) else getString(R.string.location_alarm_stopped)
    }

    inner class LocalBinder :Binder() {
        fun getService(): LocationUpdatesService {
            return this@LocationUpdatesService
        }
    }

    companion object {
        const val TAG = "LocationUpdatesService"
        const val EXTRA_STARTED_FROM_NOTIFICATION = "$TAG.EXTRA_STARTED_FROM_NOTIFICATION"

        const val NOTIFICATION_ID = 1001

        const val INTERVAL = 10000L
        const val FASTEST_INTERVAL = 5000L

        private var isRunning = false

        fun startService(context: Context){
            if (isRunning) return

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService( Intent(context, LocationUpdatesService::class.java))
            } else {
                context.startService( Intent(context, LocationUpdatesService::class.java))
            }
        }
    }
}