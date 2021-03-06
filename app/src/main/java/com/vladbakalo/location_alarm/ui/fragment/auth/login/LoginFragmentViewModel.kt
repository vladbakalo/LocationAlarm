package com.vladbakalo.location_alarm.ui.fragment.auth.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.vladbakalo.location_alarm.application.base.BaseViewModel
import com.vladbakalo.location_alarm.common.extentions.setValueSafe

class LoginFragmentViewModel :BaseViewModel() {
    val isLoading = ObservableField<Boolean>()
    private val emailData: MutableLiveData<String> = MutableLiveData()
    private val passwordData: MutableLiveData<String> = MutableLiveData()

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

    fun getEmail(): String {
        return emailData.value ?: ""
    }

    fun setEmail(value: String) {
        emailData.setValueSafe(value)
    }
}