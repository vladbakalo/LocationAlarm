package com.vladbakalo.core.common.helper

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.vladbakalo.core.data.models.LocationAlarm

object AppFirebaseAnalytics {
    private val analytics = Firebase.analytics

    fun logEditMap(alarmDb: LocationAlarm) {
        val bundle = createBaseAlarmBundle(alarmDb)
        analytics.logEvent(EVENT_EDIT_ALARM_MAP, bundle)
    }

    fun logEditList(alarmDb: LocationAlarm) {
        val bundle = createBaseAlarmBundle(alarmDb)
        analytics.logEvent(EVENT_EDIT_ALARM_LIST, bundle)
    }

    fun logCreateMap() {
        val bundle = Bundle()
        analytics.logEvent(EVENT_CREATE_ALARM_MAP, bundle)
    }

    fun logCreateList() {
        val bundle = Bundle()
        analytics.logEvent(EVENT_CREATE_ALARM_LIST, bundle)
    }

    private fun createBaseAlarmBundle(alarmDb: LocationAlarm): Bundle {
        return Bundle().apply {
            putLong(LOCATION_ALARM_ID, alarmDb.id)
            putString(LOCATION_ALARM_NAME, alarmDb.name)
        }
    }

    interface IFirebaseAnalytic{
        fun logEvent(eventName: String, eventData: Bundle)
    }

    private const val CONTENT_TYPE_ALARM = "alarm"

    private const val EVENT_CREATE_ALARM_MAP = "create_alarm_map"
    private const val EVENT_CREATE_ALARM_LIST = "create_alarm_list"
    private const val EVENT_EDIT_ALARM_MAP = "edit_alarm_map"
    private const val EVENT_EDIT_ALARM_LIST = "edit_alarm_list"

    private const val LOCATION_ALARM_ID = "location_alarm_id"
    private const val LOCATION_ALARM_NAME = "location_alarm_name"
}