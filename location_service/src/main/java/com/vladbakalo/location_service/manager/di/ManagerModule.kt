package com.vladbakalo.location_service.manager.di

import android.content.Context
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import com.vladbakalo.location_service.di.LocationServiceScope
import com.vladbakalo.location_service.manager.AppNotificationManager
import com.vladbakalo.location_service.manager.LocationAlarmManager
import com.vladbakalo.location_service.manager.PreferenceManager
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class ManagerModule {

    @LocationServiceScope
    @Provides
    fun provideNotificationManager(context: Context): AppNotificationManager {
        return AppNotificationManager(context)
    }

    @LocationServiceScope
    @Provides
    fun provideLocationManager(notificationManager: AppNotificationManager,
                               locationAlarmRepo: LocationAlarmRepository): LocationAlarmManager {
        return LocationAlarmManager(notificationManager, locationAlarmRepo, Dispatchers.IO)
    }

    @Provides
    fun provideSharedPreference(context: Context): PreferenceManager
            = PreferenceManager(context)
}