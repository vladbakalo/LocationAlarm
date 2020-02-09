package com.vladbakalo.location_alarm.common

import android.util.Log
import com.crashlytics.android.Crashlytics

object Logger {

    fun dt(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun logException(tag: String, e: Exception) {
        Log.w(tag, e)
        Crashlytics.logException(e)
    }
}