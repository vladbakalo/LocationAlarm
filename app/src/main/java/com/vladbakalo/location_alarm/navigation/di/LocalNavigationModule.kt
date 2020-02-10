package com.vladbakalo.location_alarm.navigation.di

import com.vladbakalo.location_alarm.navigation.LocalCiceroneHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalNavigationModule {

    @Singleton
    @Provides
    fun provideLocalCiceroneHolder(): LocalCiceroneHolder {
        return LocalCiceroneHolder()
    }
}