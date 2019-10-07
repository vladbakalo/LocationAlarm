package com.vladbakalo.location_alarm.navigation

import com.vladbakalo.location_alarm.ui.auth.login.LoginFragment
import com.vladbakalo.location_alarm.ui.auth.registration.RegistrationFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object AuthLoginScreen: SupportAppScreen(){
        override fun getFragment() = LoginFragment.newInstance()
    }

    object AuthRegisterScreen: SupportAppScreen(){
        override fun getFragment() = RegistrationFragment.newInstance()
    }
}