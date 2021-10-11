package com.vladbakalo.core.data.models

import android.location.Location
import com.vladbakalo.core.db.models.LocationAlarmDb

class LocationAlarm(
    var id: Long,
    var name: String,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var note: String,
    var enabled: Boolean
) {

    constructor(model: LocationAlarmDb) : this(
        model.id,
        model.name,
        model.address,
        model.latitude,
        model.longitude,
        model.note,
        model.enabled
    )

    fun toDbInstance(): LocationAlarmDb {
        return LocationAlarmDb(id, name, address, latitude, longitude, note, enabled)
    }

    fun getLocation(): Location {
        return Location("").apply {
            this.latitude = this@LocationAlarm.latitude
            this.longitude = this@LocationAlarm.longitude
        }
    }

    fun getLatitudeStr(): String {
        return String.format("%.4f", latitude)
    }

    fun getLongitudeStr(): String {
        return String.format("%.4f", longitude)
    }
}