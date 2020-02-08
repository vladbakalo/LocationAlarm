package com.vladbakalo.location_alarm.navigation

import com.vladbakalo.location_alarm.ui.auth.login.LoginFragment
import com.vladbakalo.location_alarm.ui.auth.registration.RegistrationFragment
import com.vladbakalo.location_alarm.ui.detailed.LocationAlarmFragment
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

    object AlarmMapScreen :SupportAppScreen() {
        override fun getFragment() = AlarmMapFragment.create()
    }

    object AlarmListScreen :SupportAppScreen() {
        override fun getFragment() = AlarmListFragment.create()
    }

    object SettingsScreen :SupportAppScreen() {
        override fun getFragment() = SettingsFragment.create()
    }

    data class LocationAlarmScreen(val alarmId: Long) :SupportAppScreen() {
        override fun getFragment() = LocationAlarmFragment.create(alarmId)
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