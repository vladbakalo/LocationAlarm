package com.vladbakalo.location_alarm.common.utils

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.vladbakalo.location_alarm.BuildConfig

object IntentUtils {

    fun createAppSettingsIntent(): Intent{
        return Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}