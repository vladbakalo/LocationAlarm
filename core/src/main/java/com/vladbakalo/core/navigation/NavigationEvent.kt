package com.vladbakalo.core.navigation

import android.content.Context
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import com.vladbakalo.core.R

data class NavigationEvent(val navDeepLinkRequest: NavDeepLinkRequest) {

    companion object{

        fun getCreateLocationAlarmNavigationEvent(context: Context): NavigationEvent{
            val uri = context.getString(R.string.deeplink_create_location_alarm_screen).toUri()
            val request = NavDeepLinkRequest.Builder.fromUri(uri).build()
            return NavigationEvent(request)
        }

        fun getAlarmDistanceListNavigationEvent(context: Context): NavigationEvent{
            val uri = context.getString(R.string.deeplink_alarm_distance_list_screen).toUri()
            val request = NavDeepLinkRequest.Builder.fromUri(uri).build()
            return NavigationEvent(request)
        }
    }
}