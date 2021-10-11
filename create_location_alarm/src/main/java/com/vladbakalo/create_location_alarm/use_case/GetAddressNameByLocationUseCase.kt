package com.vladbakalo.create_location_alarm.use_case

import android.content.Context
import android.location.Geocoder
import com.vladbakalo.core.R
import com.vladbakalo.core.base.SuspendUseCase
import com.vladbakalo.core.common.MyLogger
import com.vladbakalo.core.data.common.LatLng
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*

class GetAddressNameByLocationUseCase(private val context: Context,
                                      coroutineDispatcher: CoroutineDispatcher) :
    SuspendUseCase<LatLng, String>(coroutineDispatcher) {

    override suspend fun execute(parameters: LatLng): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val builder = StringBuilder()
        try {
            val addressResult = geocoder.getFromLocation(parameters.latitude, parameters.longitude, 1)
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
            MyLogger.dt(GetAddressNameByLocationUseCase::class.java.simpleName, "locationName : ${address.getAddressLine(0)}")

            return builder.toString()
        } catch (e: Throwable) {
            throw Exception(context.getString(R.string.failed_get_address))
        }
    }
}