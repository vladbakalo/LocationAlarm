package com.vladbakalo.location_alarm.navigation

import com.google.android.gms.maps.model.LatLng
import com.vladbakalo.location_alarm.navigation.common.RootSupportAppScreen
import com.vladbakalo.location_alarm.ui.fragment.alarm_create.LocationAlarmCreateFragment
import com.vladbakalo.location_alarm.ui.fragment.alarm_preview.LocationAlarmFragment
import com.vladbakalo.location_alarm.ui.fragment.auth.login.LoginFragment
import com.vladbakalo.location_alarm.ui.fragment.auth.registration.RegistrationFragment
import com.vladbakalo.location_alarm.ui.fragment.list.AlarmListFragment
import com.vladbakalo.location_alarm.ui.fragment.map.AlarmMapFragment
import com.vladbakalo.location_alarm.ui.fragment.settings.SettingsFragment
import com.vladbakalo.location_alarm.ui.fragment.tab.TabContainerFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object AuthLoginScreen :SupportAppScreen() {
        override fun getFragment() = LoginFragment.create()
    }

    object AuthRegisterScreen :SupportAppScreen() {
        override fun getFragment() = RegistrationFragment.create()
    }

    object AlarmMapScreen :RootSupportAppScreen() {
        override fun getScreenFragment() = AlarmMapFragment.create()
    }

    object AlarmListScreen :RootSupportAppScreen() {
        override fun getScreenFragment() = AlarmListFragment.create()
    }

    object SettingsScreen :RootSupportAppScreen() {
        override fun getScreenFragment() = SettingsFragment.create()
    }

    data class LocationAlarmScreen(val alarmId: Long) :SupportAppScreen() {
        override fun getFragment() = LocationAlarmFragment.create(alarmId)
    }

    object LocationAlarmCreateScreen :SupportAppScreen() {
        override fun getFragment() = LocationAlarmCreateFragment.create()
    }

    data class LocationAlarmMapCreateScreen(val latLng: LatLng) :SupportAppScreen() {
        override fun getFragment() = LocationAlarmCreateFragment.create(latLng)
    }

    data class LocationAlarmEditScreen(val locationAlarmId: Long) :SupportAppScreen() {
        override fun getFragment() = LocationAlarmCreateFragment.create(locationAlarmId)
    }

    data class TabScreen(val tabTag: String) :SupportAppScreen() {
        override fun getFragment() = TabContainerFragment.create(tabTag)

        companion object {
            fun getTabScreenByTag(tag: String): SupportAppScreen {
                return when (tag) {
                    AlarmMapFragment.TAG -> AlarmMapScreen
                    AlarmListFragment.TAG -> AlarmListScreen
                    SettingsFragment.TAG -> SettingsScreen
                    else -> throw IllegalArgumentException("Wrong tag of Tab Screen")
                }
            }
        }
    }
}