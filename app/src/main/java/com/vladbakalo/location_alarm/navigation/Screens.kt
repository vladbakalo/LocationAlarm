package com.vladbakalo.location_alarm.navigation

import androidx.fragment.app.Fragment
import com.vladbakalo.location_alarm.navigation.common.RootSupportAppScreen
import com.vladbakalo.location_alarm.ui.alarm_create.LocationAlarmCreateFragment
import com.vladbakalo.location_alarm.ui.alarm_preview.LocationAlarmFragment
import com.vladbakalo.location_alarm.ui.auth.login.LoginFragment
import com.vladbakalo.location_alarm.ui.auth.registration.RegistrationFragment
import com.vladbakalo.location_alarm.ui.list.AlarmListFragment
import com.vladbakalo.location_alarm.ui.map.AlarmMapFragment
import com.vladbakalo.location_alarm.ui.settings.SettingsFragment
import com.vladbakalo.location_alarm.ui.tab.TabContainerFragment
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