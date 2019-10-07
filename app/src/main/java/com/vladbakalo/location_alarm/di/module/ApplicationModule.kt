package com.vladbakalo.location_alarm.di.module

import com.vladbakalo.location_alarm.application.App
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val app: App) {

    @Provides
    fun provideApplication() : App{
        return app
    }
}