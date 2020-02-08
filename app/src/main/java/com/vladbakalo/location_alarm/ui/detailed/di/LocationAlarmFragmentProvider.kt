package com.vladbakalo.location_alarm.ui.detailed.di

import com.vladbakalo.location_alarm.ui.detailed.LocationAlarmFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationAlarmFragmentProvider {

    @ContributesAndroidInjector(modules = [])
    abstract fun provideLocationAlarmFragment(): LocationAlarmFragment
}