package com.vladbakalo.location_alarm.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor

class AlarmListViewModelFactory(val interactor: LocationAlarmInteractor): ViewModelProvider.Factory {
    override fun <T :ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmListViewModel::class.java)) {
            return AlarmListViewModel(interactor) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}