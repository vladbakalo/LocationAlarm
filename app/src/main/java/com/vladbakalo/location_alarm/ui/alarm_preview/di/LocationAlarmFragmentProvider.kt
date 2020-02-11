package com.vladbakalo.location_alarm.ui.alarm_preview.di

import com.vladbakalo.location_alarm.ui.alarm_preview.LocationAlarmFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationAlarmFragmentProvider {

    @ContributesAndroidInjector(modules = [])
    abstract fun provideLocationAlarmFragment(): LocationAlarmFragment
}