package com.vladbakalo.location_alarm.application

import android.content.Context
import android.content.Context.MODE_PRIVATE

private const val NAME = "mainSharedPreferences"

class SharedPreference constructor(var context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        NAME, MODE_PRIVATE)


    companion object {
        private const val USER_FIRST_NAME_KEY = "USER_FIRST_NAME_KEY"
        private const val USER_EMAIL_KEY = "USER_EMAIL_KEY"
    }
}