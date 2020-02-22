package com.vladbakalo.location_alarm.ui.list.di

import com.vladbakalo.location_alarm.ui.list.AlarmListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AlarmListFragmentProvider {

    @ContributesAndroidInjector(modules = [AlarmListFragmentModule::class])
    abstract fun contributeAlarmListFragment(): AlarmListFragment
}