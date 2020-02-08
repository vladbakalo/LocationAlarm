package com.vladbakalo.location_alarm.ui.settings.di

import com.vladbakalo.location_alarm.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingsFragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideSettingsFragmentFactory(): SettingsFragment
}