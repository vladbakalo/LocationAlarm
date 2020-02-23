package com.vladbakalo.location_alarm.di.module

import com.vladbakalo.location_alarm.common.live_data.LastLocationLiveData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun provideLastLocationLiveData() = LastLocationLiveData()
}