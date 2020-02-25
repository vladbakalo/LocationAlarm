package com.vladbakalo.location_alarm.data.di

import android.content.Context
import com.vladbakalo.location_alarm.data.AppDatabase
import com.vladbakalo.location_alarm.data.repo.AlarmRepository
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideLocationAlarmRepo(database: AppDatabase, context: Context): LocationAlarmRepository
            = LocationAlarmRepository(database, context)

    @Singleton
    @Provides
    fun provideAlarmRepo(database: AppDatabase): AlarmRepository
            = AlarmRepository(database)
}