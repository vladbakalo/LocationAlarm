package com.vladbakalo.location_alarm.ui.auth.login

import androidx.databinding.ObservableField
import com.vladbakalo.location_alarm.application.BaseViewModel

class LoginFragmentViewModel: BaseViewModel() {
    val isLoading = ObservableField<Boolean>()

//    override fun onBackPressed() {
//        router.exit()
//    }

    fun onEmailLoginClick() {
        isLoading.set(true)
    }

    fun onFacebookLoginClick() {

    }

    fun onGoogleLoginClick() {

    }

    fun onRegisterClick() {
        isLoading.set(false)
        //router.navigateTo(Screens.AuthRegisterScreen)
    }
}