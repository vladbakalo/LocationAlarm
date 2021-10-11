package com.vladbakalo.location_service.manager

import android.content.Context


class PreferenceManager constructor(var context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        NAME, Context.MODE_PRIVATE)


    companion object {
        private const val NAME = "shared_preference_main"

        private const val USER_FIRST_NAME_KEY = "USER_FIRST_NAME_KEY"
        private const val USER_EMAIL_KEY = "USER_EMAIL_KEY"
    }
}