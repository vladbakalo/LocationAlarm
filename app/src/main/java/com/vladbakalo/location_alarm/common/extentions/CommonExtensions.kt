package com.vladbakalo.location_alarm.common.extentions

import android.content.Context
import android.view.LayoutInflater

fun Context.getLayoutInflater(): LayoutInflater {
    return this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}