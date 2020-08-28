package com.vladbakalo.location_alarm.common.extentions

import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.gms.maps.model.LatLng

fun Context.getLayoutInflater(): LayoutInflater {
    return this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}

fun ViewGroup.getLayoutInflater(): LayoutInflater {
    return this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}

fun Location.isValid(): Boolean{
    if(this.latitude < -90 || this.latitude > 90) {
        return false
    } else if(this.longitude < -180 || this.longitude > 180) {
        return false
    }
    return true
}

fun Location.toLatLng(): LatLng{
    return LatLng(this.latitude, this.longitude)
}
