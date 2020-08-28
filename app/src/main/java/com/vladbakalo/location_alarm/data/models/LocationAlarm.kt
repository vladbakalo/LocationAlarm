package com.vladbakalo.location_alarm.data.models

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.vladbakalo.location_alarm.application.base.BaseDataModel

@Entity(tableName = "location_alarm")
data class LocationAlarm(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var name: String,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var note: String,
    var enabled: Boolean = true): BaseDataModel {

    override fun getPrimaryId(): Long {
        return id
    }

    fun getLatLng(): LatLng{
        return LatLng(latitude, longitude)
    }

    fun getLocation(): Location {
        return Location("").apply {
            this.latitude = this@LocationAlarm.latitude
            this.longitude = this@LocationAlarm.longitude
        }
    }

    fun getLatitudeStr(): String{
        return String.format("%.4f", latitude)
    }

    fun getLongitudeStr(): String{
        return String.format("%.4f", longitude)
    }
}