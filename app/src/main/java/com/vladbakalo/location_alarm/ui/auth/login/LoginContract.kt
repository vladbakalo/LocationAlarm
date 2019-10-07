package com.vladbakalo.location_alarm.ui.auth.login

import com.vladbakalo.location_alarm.mvp.BaseContract

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