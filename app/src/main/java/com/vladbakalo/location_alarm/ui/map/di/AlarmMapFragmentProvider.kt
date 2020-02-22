package com.vladbakalo.location_alarm.ui.map.di

import com.vladbakalo.location_alarm.ui.map.AlarmMapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AlarmMapFragmentProvider {

    @ContributesAndroidInjector(modules = [])
    abstract fun contributeAlarmMapFragment(): AlarmMapFragment
}