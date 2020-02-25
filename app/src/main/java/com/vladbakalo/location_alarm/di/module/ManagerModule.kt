package com.vladbakalo.location_alarm.di.module

import android.content.Context
import com.vladbakalo.location_alarm.common.live_data.LastLocationLiveData
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import com.vladbakalo.location_alarm.manager.AppNotificationManager
import com.vladbakalo.location_alarm.manager.LocationAlarmManager
import com.vladbakalo.location_alarm.manager.PreferenceManager
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
    fun provideLocationManager(notificationManager: AppNotificationManager,
                               locationAlarmRepo: LocationAlarmRepository): LocationAlarmManager
            = LocationAlarmManager(notificationManager, locationAlarmRepo)

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context): PreferenceManager
            = PreferenceManager(context)
}