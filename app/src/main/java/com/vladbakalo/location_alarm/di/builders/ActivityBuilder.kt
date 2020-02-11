package com.vladbakalo.location_alarm.di.builders

import com.vladbakalo.location_alarm.ui.alarm_create.di.LocationAlarmCreateProvider
import com.vladbakalo.location_alarm.ui.list.di.AlarmListFragmentProvider
import com.vladbakalo.location_alarm.ui.main.MainNavigationActivity
import com.vladbakalo.location_alarm.ui.map.di.AlarmMapFragmentProvider
import com.vladbakalo.location_alarm.ui.settings.di.SettingsFragmentProvider
import com.vladbakalo.location_alarm.ui.tab.di.TabContainerFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
        modules = [
            AlarmMapFragmentProvider::class,
            AlarmListFragmentProvider::class,
            SettingsFragmentProvider::class,
            LocationAlarmCreateProvider::class,
            TabContainerFragmentProvider::class])
    abstract fun contributeMainActivity(): MainNavigationActivity
}