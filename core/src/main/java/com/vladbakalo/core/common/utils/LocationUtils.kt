package com.vladbakalo.core.common.utils

import android.location.Location

object LocationUtils {
    private const val TAG = "LocationUtils"

    fun latLngToLocation(latitude: Double, longitude: Double): Location {
        val location = Location("LocationUtils")
        location.latitude = latitude
        location.longitude = longitude

        return location
    }
}