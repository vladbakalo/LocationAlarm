package com.vladbakalo.location_alarm.ui.list.di

import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor
import com.vladbakalo.location_alarm.ui.list.AlarmListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AlarmListFragmentModule {

    @Provides
    fun provideAlarmListViewModelFactory(interactor: LocationAlarmInteractor): ViewModelProvider.Factory{
        return AlarmListViewModelFactory(interactor)
    }
}