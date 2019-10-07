package com.vladbakalo.location_alarm.ui.auth.login

import com.vladbakalo.location_alarm.mvp.BaseFragmentPresenter
import com.vladbakalo.location_alarm.navigation.Screens
import ru.terrakok.cicerone.Router

class LoginPresenter(router: Router): BaseFragmentPresenter<LoginContract.View>(router),
    LoginContract.Presenter {

    override fun onBackPressed() {
        router.exit()
    }

    override fun start() {

    }

    override fun stop() {

    }

    override fun onEmailLoginClick() {
    }

    override fun onFacebookLoginClick() {
    }

    override fun onGoogleLoginClick() {
    }

    override fun onRegisterClick() {
        router.navigateTo(Screens.AuthRegisterScreen)
    }

}