package com.vladbakalo.location_alarm.application.service

import android.app.PendingIntent
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.common.manager.LocationAlarmManager
import com.vladbakalo.location_alarm.common.utils.NotificationUtils
import com.vladbakalo.location_alarm.common.utils.PermissionUtils
import dagger.android.DaggerService
import javax.inject.Inject

class LocationUpdatesService: DaggerService() {

    @Inject
    lateinit var locationAlarmManager: LocationAlarmManager

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private var isConfigurationChanging: Boolean = false
    private var isLocationUpdatesEnabled: Boolean = false

    override fun onCreate() {
        super.onCreate()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object :LocationCallback(){
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

    override fun onBind(intent: Intent): IBinder? {
        Logger.dt(TAG, "onBind")
        stopForeground(true)
        isConfigurationChanging = false
        return null
    }

    override fun onRebind(intent: Intent?) {
        Logger.dt(TAG, "onRebind")
        stopForeground(true)
        isConfigurationChanging = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Logger.dt(TAG, "onUnbind")
        if (isConfigurationChanging.not() && PermissionUtils.checkLocationPermission(this)){
            Logger.dt(TAG, "onUnbind : start foreground")

            startForeground(NOTIFICATION_ID,
                NotificationUtils.getLocationUpdatesNotification(this, getPendingAction()))
        }
        return true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.dt(TAG, "onStartCommand")

        val isToggleServiceState = intent?.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false) ?: false

        if (isToggleServiceState){
            isLocationUpdatesEnabled = isLocationUpdatesEnabled.not()

            if (isLocationUpdatesEnabled){
                requestLocationUpdatesWithCheck()
            } else {
                removeLocationUpdates()
            }
        }
        return START_NOT_STICKY
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        isConfigurationChanging = true
    }

    private fun onNewLocation(location: Location?){
        if (location == null){
            Logger.dt(TAG, "onNewLocation : new location is null")
            return
        }

        //Process new location
        locationAlarmManager.onNewLocation(location)
    }

    private fun requestLocationUpdatesWithCheck(){
        if (PermissionUtils.checkLocationPermission(this)){
            requestLocationUpdates()
        }
    }

    private fun requestLocationUpdates(){
        Logger.dt(TAG, "requestLocationUpdates")
        isLocationUpdatesEnabled = true
        try {
            fusedLocationClient.requestLocationUpdates(createLocationRequest(), locationCallback, Looper.myLooper())
        } catch (e: SecurityException){
            Logger.dt(TAG, "requestLocationUpdates error : ${e.message}")
        }
    }

    private fun removeLocationUpdates(){
        isLocationUpdatesEnabled = false
        try {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        } catch (e: SecurityException){
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

    private fun getPendingAction(): NotificationCompat.Action{
        val actionText = this.getString(if (isLocationUpdatesEnabled) R.string.disable else R.string.enable)
        val intent = Intent(this, LocationUpdatesService::class.java)
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true)

        val servicePendingIntent = PendingIntent.getService(this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Action(R.drawable.ic_alarm, actionText, servicePendingIntent)
    }

    companion object {
        const val TAG = "LocationUpdatesService"
        const val EXTRA_STARTED_FROM_NOTIFICATION = "$TAG.EXTRA_STARTED_FROM_NOTIFICATION"

        const val NOTIFICATION_ID = 1001

        const val INTERVAL = 10000L
        const val FASTEST_INTERVAL = 5000L
    }
}