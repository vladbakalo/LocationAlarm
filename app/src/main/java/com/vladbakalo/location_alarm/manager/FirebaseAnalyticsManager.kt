package com.vladbakalo.location_alarm.manager

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.vladbakalo.location_alarm.data.models.LocationAlarm

class FirebaseAnalyticsManager(context: Context) {
    private val analytics = FirebaseAnalytics.getInstance(context)

    fun logEditMap(alarm: LocationAlarm) {
        val bundle = createBaseAlarmBundle(alarm)
        analytics.logEvent(EVENT_EDIT_ALARM_MAP, bundle)
    }

    fun logEditList(alarm: LocationAlarm) {
        val bundle = createBaseAlarmBundle(alarm)
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

    private fun createBaseAlarmBundle(alarm: LocationAlarm): Bundle {
        return Bundle().apply {
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, CONTENT_TYPE_ALARM)
            putString(FirebaseAnalytics.Param.ITEM_ID, alarm.id.toString())
            putString(FirebaseAnalytics.Param.ITEM_NAME, alarm.name)
        }
    }

    companion object {
        private const val CONTENT_TYPE_ALARM = "alarm"

        private const val EVENT_CREATE_ALARM_MAP = "create_alarm_map"
        private const val EVENT_CREATE_ALARM_LIST = "create_alarm_list"
        private const val EVENT_EDIT_ALARM_MAP = "edit_alarm_map"
        private const val EVENT_EDIT_ALARM_LIST = "edit_alarm_list"

    }
}