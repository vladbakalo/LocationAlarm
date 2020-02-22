package com.vladbakalo.location_alarm.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import com.vladbakalo.location_alarm.interactor.LocationAlarmCreateEditInteractor

class AlarmListViewModelFactory(val interactor: LocationAlarmCreateEditInteractor): ViewModelProvider.Factory {
    override fun <T :ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmListViewModel::class.java)) {
            return AlarmListViewModel(interactor) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}