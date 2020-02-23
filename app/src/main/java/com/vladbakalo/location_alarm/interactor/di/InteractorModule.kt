package com.vladbakalo.location_alarm.interactor.di

import com.vladbakalo.location_alarm.data.repo.AlarmRepository
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Singleton
    @Provides
    fun provideLocationAlarmCreateEditInteractor(repositoryLoc: LocationAlarmRepository,
                                                 repositoryAlarm: AlarmRepository): LocationAlarmInteractor{
        return LocationAlarmInteractor(repositoryLoc, repositoryAlarm)
    }
}