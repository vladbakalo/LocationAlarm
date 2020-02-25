package com.vladbakalo.location_alarm.ui.auth.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.manager.AuthManager

class AuthActivityViewModelFactory(private val authManager: AuthManager) :
    ViewModelProvider.Factory {
    override fun <T :ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthActivityViewModel::class.java)) {
            return AuthActivityViewModel(authManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}