package com.vladbakalo.core.db.models

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vladbakalo.core.db.base.BaseDataModel

@Entity(tableName = "location_alarm")
data class LocationAlarmDb(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var name: String,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var note: String,
    var enabled: Boolean = true):BaseDataModel {

    override fun getPrimaryId(): Long {
        return id
    }

    fun getLocation(): Location {
        return Location("").apply {
            this.latitude = this@LocationAlarmDb.latitude
            this.longitude = this@LocationAlarmDb.longitude
        }
    }

    fun getLatitudeStr(): String{
        return String.format("%.4f", latitude)
    }

    fun getLongitudeStr(): String{
        return String.format("%.4f", longitude)
    }
}