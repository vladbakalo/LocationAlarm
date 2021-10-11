package com.vladbakalo.core.db.di

import android.content.Context
import com.vladbakalo.core.db.dao.AlarmDistanceDao
import com.vladbakalo.core.db.dao.LocationAlarmDao
import com.vladbakalo.core.db.repo.AlarmDistanceRepository
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import dagger.Module
import dagger.Provides

@Module
class RepoModule {

    @Provides
    fun provideLocationAlarmRepo(locationAlarmDao: LocationAlarmDao, context: Context): LocationAlarmRepository
            = LocationAlarmRepository(locationAlarmDao, context)

    @Provides
    fun provideAlarmRepo(alarmDistanceDao: AlarmDistanceDao): AlarmDistanceRepository
            = AlarmDistanceRepository(alarmDistanceDao)
}