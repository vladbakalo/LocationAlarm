package com.vladbakalo.location_alarm.ui.map.di

import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.common.live_data.LastLocationLiveData
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor
import com.vladbakalo.location_alarm.ui.map.AlarmMapViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AlarmMapFragmentModule {

    @Provides
    fun provideAlarmMapViewModelFactory(interactor: LocationAlarmInteractor,
                                        lastLocationLiveData: LastLocationLiveData): ViewModelProvider.Factory{
        return AlarmMapViewModelFactory(interactor, lastLocationLiveData)
    }
}