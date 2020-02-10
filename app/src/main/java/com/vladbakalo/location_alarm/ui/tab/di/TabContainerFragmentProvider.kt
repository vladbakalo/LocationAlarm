package com.vladbakalo.location_alarm.ui.tab.di

import com.vladbakalo.location_alarm.ui.tab.TabContainerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TabContainerFragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideTabContainerFragmentFactory(): TabContainerFragment
}