package com.vladbakalo.location_alarm.ui.alarm_create.di

import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor
import com.vladbakalo.location_alarm.ui.alarm_create.LocationAlarmCreateViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LocationAlarmCreateModule {

    @Provides
    fun provideViewModelFactory(interactor: LocationAlarmInteractor): ViewModelProvider.Factory{
        return LocationAlarmCreateViewModelFactory(interactor)
    }
}