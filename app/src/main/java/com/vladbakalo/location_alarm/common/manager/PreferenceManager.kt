package com.vladbakalo.location_alarm.common.manager

import android.content.Context
import com.vladbakalo.location_alarm.common.Logger


class PreferenceManager constructor(var context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        NAME, Context.MODE_PRIVATE)


    companion object {
        private const val NAME = "shared_preference_main"

        private const val USER_FIRST_NAME_KEY = "USER_FIRST_NAME_KEY"
        private const val USER_EMAIL_KEY = "USER_EMAIL_KEY"
    }
}