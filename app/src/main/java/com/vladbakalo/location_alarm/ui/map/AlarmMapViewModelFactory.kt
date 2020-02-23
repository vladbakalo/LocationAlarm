package com.vladbakalo.location_alarm.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.common.live_data.LastLocationLiveData
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor

class AlarmMapViewModelFactory(private val interactor: LocationAlarmInteractor,
                               private val lastLocationLiveData: LastLocationLiveData): ViewModelProvider.Factory {
    override fun <T :ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmMapViewModel::class.java)){
            return AlarmMapViewModel(interactor, lastLocationLiveData) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}