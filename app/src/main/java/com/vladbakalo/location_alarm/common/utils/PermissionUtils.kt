package com.vladbakalo.location_alarm.common.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

object PermissionUtils {
    public const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1001


    fun requestLocationPermission(activity: Activity): Boolean{
        val permissionAccessFineLocationApproved = ActivityCompat.checkSelfPermission(activity,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (permissionAccessFineLocationApproved){
            val permissionAccessBackgroundLocationApproved = isBackgroundLocationGranted(activity)

            if (permissionAccessBackgroundLocationApproved){
                return true
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            }
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
        return false
    }

    fun checkLocationPermission(context: Context): Boolean{
        return ActivityCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && isBackgroundLocationGranted(context)
    }

    fun shouldShowRequestLocationPermissionRationale(activity: Activity): Boolean{
        return ActivityCompat.shouldShowRequestPermissionRationale(activity,
            Manifest.permission.ACCESS_FINE_LOCATION)
                && isBackgroundLocationGranted(activity)
    }

    private fun isBackgroundLocationGranted(context: Context): Boolean{
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return true

        return ActivityCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}