package com.vladbakalo.location_alarm.di.module

import android.content.Context
import com.vladbakalo.location_alarm.common.manager.AppNotificationManager
import com.vladbakalo.location_alarm.common.manager.LocationAlarmManager
import com.vladbakalo.location_alarm.common.manager.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagerModule {

    @Singleton
    @Provides
    fun provideNotificationManager(context: Context): AppNotificationManager
            = AppNotificationManager(context)

    @Singleton
    @Provides
    fun provideLocationManager(notificationManager: AppNotificationManager): LocationAlarmManager
            = LocationAlarmManager(notificationManager)

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context): PreferenceManager
            = PreferenceManager(context)
}