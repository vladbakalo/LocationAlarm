package com.vladbakalo.location_alarm.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository

class AlarmListViewModelFactory(val repo: LocationAlarmRepository): ViewModelProvider.Factory {
    override fun <T :ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmListViewModel::class.java)) {
            return AlarmListViewModel(repo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}