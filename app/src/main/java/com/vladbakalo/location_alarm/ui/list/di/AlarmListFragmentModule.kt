package com.vladbakalo.location_alarm.ui.list.di

import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.common.Logger
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import dagger.Module
import dagger.Provides

@Module
class AlarmListFragmentModule {

    @Provides
    fun provideAlarmListViewModelFactory(repo: LocationAlarmRepository): ViewModelProvider.Factory{
        Logger.dt("Agaga", "provideAlarmListViewModelFactory")
        return AlarmListViewModelFactory(repo)
    }
}