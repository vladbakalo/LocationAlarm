package com.vladbakalo.location_alarm.di.module

import android.content.Context
import com.vladbakalo.location_alarm.application.App
import com.vladbakalo.location_alarm.common.SharedPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: App): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context): SharedPreference {
        return SharedPreference(context)
    }
}