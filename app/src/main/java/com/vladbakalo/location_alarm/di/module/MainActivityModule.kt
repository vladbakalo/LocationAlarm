package com.vladbakalo.location_alarm.di.module

import com.vladbakalo.location_alarm.ui.main.MainActivityViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal abstract class MainActivityModule {

    @Provides
    internal fun providesMainViewModelFactory(): MainActivityViewModelFactory
            = MainActivityViewModelFactory()
}