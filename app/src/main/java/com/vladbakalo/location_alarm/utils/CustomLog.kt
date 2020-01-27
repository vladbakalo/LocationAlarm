package com.vladbakalo.location_alarm.utils

import android.util.Log
import com.crashlytics.android.Crashlytics
import java.lang.Exception

object CustomLog {

    fun logException(tag: String, e: Exception){
        Log.w(tag, e)
        Crashlytics.logException(e)
    }
}