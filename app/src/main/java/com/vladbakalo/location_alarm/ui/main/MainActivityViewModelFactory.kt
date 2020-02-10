package com.vladbakalo.location_alarm.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivityViewModelFactory :ViewModelProvider.Factory {
    override fun <T :ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}