package com.vladbakalo.location_alarm.ui.alarm_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.interactor.LocationAlarmCreateEditInteractor

class LocationAlarmCreateViewModelFactory(private val interactor: LocationAlarmCreateEditInteractor):
    ViewModelProvider.Factory {

    override fun <T :ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationAlarmCreateViewModel::class.java)){
            return LocationAlarmCreateViewModel(interactor) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}