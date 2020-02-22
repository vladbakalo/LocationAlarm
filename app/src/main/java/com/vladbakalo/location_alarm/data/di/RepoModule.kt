package com.vladbakalo.location_alarm.data.di

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
    fun provideLocationAlarmRepo(database: AppDatabase): LocationAlarmRepository
            = LocationAlarmRepository(database)

    @Singleton
    @Provides
    fun provideAlarmRepo(database: AppDatabase): AlarmRepository
            = AlarmRepository(database)
}