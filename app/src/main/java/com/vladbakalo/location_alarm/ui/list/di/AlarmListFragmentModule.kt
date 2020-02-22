package com.vladbakalo.location_alarm.ui.list.di

import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import com.vladbakalo.location_alarm.interactor.LocationAlarmCreateEditInteractor
import com.vladbakalo.location_alarm.ui.list.AlarmListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AlarmListFragmentModule {

    @Provides
    fun provideAlarmListViewModelFactory(interactor: LocationAlarmCreateEditInteractor): ViewModelProvider.Factory{
        return AlarmListViewModelFactory(interactor)
    }
}