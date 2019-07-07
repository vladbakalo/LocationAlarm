package com.vladbakalo.location_alarm.ui.login

import com.vladbakalo.location_alarm.application.BaseContract

interface LoginContract {

    interface Presenter: BaseContract.Presenter<View>{
        fun onEmailLoginClick()

        fun onFacebookLoginClick()

        fun onGoogleLoginClick()

        fun onRegisterClick()
    }

    interface View: BaseContract.View{

    }
}