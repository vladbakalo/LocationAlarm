package com.vladbakalo.location_alarm.di.module

import com.vladbakalo.location_alarm.application.BaseApplication
import com.vladbakalo.location_alarm.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val baseApplication: BaseApplication) {

    @Provides
    @ApplicationScope
    fun provideApplication() : BaseApplication{
        return baseApplication
    }
}