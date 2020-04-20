package com.vladbakalo.location_alarm.common.utils

import android.location.Geocoder
import android.location.Location
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.App
import com.vladbakalo.location_alarm.common.MyLogger
import io.reactivex.Single
import java.util.*


object LocationUtils {
    private const val TAG = "LocationUtils"

    fun latLngToLocation(latitude: Double, longitude: Double): Location {
        val location = Location("LocationUtils")
        location.latitude = latitude
        location.longitude = longitude

        return location
    }

    fun getLocationNameRx(latitude: Double, longitude: Double): Single<String>{
        return Single.create {
            val geocoder = Geocoder(App.context, Locale.getDefault())
            val builder = StringBuilder()
            try {
                val addressResult = geocoder.getFromLocation(latitude, longitude, 1)
                val address = addressResult[0]

                address.thoroughfare?.let { it ->
                    builder.append(it).append(", ")
                }
                address.featureName?.let { it ->
                    builder.append(it).append(", ")
                }
                address.locality?.let { it ->
                    builder.append(it).append(", ")
                }
                builder.setLength(builder.length - 2) //Remove coma in end
                MyLogger.dt(TAG, "getLocationNameRx : ${address.getAddressLine(0)}")

                it.onSuccess(builder.toString())
            } catch (e: Throwable) {
                it.onError(Exception(StringUtils.getString(R.string.failed_get_address)))
            }
        }
    }
}