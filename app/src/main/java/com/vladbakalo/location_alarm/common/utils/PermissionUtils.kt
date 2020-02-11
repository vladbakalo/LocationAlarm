package com.vladbakalo.location_alarm.common.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionUtils {
    public const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1001


    fun requestLocationPermission(activity: Activity): Boolean{
        val permissionAccessFineLocationApproved = ActivityCompat.checkSelfPermission(activity,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (permissionAccessFineLocationApproved){
            val permissionAccessBackgroundLocationApproved = ActivityCompat.checkSelfPermission(activity,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED

            if (permissionAccessBackgroundLocationApproved){
                return true
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(
                    android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            }
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
        return false
    }

    fun checkLocationPermission(context: Context): Boolean{
        return ActivityCompat.checkSelfPermission(context,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun shouldShowRequestLocationPermissionRationale(activity: Activity): Boolean{
        return ActivityCompat.shouldShowRequestPermissionRationale(activity,
            Manifest.permission.ACCESS_FINE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(activity,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }
}