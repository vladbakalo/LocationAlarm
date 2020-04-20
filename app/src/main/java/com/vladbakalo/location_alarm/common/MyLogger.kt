package com.vladbakalo.location_alarm.common

import android.util.Log
import com.crashlytics.android.Crashlytics

object MyLogger {

    fun dt(tag: String, msg: String) {
        Log.d("VVV-$tag", msg)
    }

    fun logException(tag: String, e: Throwable) {
        Log.w(tag, e)
        Crashlytics.logException(e)
    }
}