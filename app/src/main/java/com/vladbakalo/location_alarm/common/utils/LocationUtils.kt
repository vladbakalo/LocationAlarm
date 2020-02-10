package com.vladbakalo.location_alarm.common.utils

import android.location.Location

object LocationUtils {

    fun latLngToLocation(latitude: Double, longitude: Double): Location {
        val location = Location("LocationUtils")
        location.latitude = latitude
        location.longitude = longitude

        return location
    }
}