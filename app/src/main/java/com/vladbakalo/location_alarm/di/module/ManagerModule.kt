package com.vladbakalo.location_alarm.di.module

import android.content.Context
import com.vladbakalo.location_alarm.common.manager.AppNotificationManager
import com.vladbakalo.location_alarm.common.manager.LocationAlarmManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagerModule {

    @Singleton
    @Provides
    fun provideNotificationManager(context: Context): AppNotificationManager{
        return AppNotificationManager(context)
    }

    @Singleton
    @Provides
    fun provideLocationManager(notificationManager: AppNotificationManager): LocationAlarmManager{
        return LocationAlarmManager(notificationManager)
    }
}