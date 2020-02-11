package com.vladbakalo.location_alarm.ui.alarm_create.di

import com.vladbakalo.location_alarm.ui.alarm_create.LocationAlarmCreateFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationAlarmCreateProvider {

    @ContributesAndroidInjector(modules = [LocationAlarmCreateModule::class])
    abstract fun provideLocationAlarmCreateFactory(): LocationAlarmCreateFragment
}