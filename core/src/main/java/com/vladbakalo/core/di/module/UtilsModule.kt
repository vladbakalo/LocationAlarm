package com.vladbakalo.core.di.module

import com.vladbakalo.core.common.live_data.LastLocationLiveData
import com.vladbakalo.core.di.CoreScope
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    @CoreScope
    @Provides
    fun provideLastLocationLiveData() = LastLocationLiveData()
}