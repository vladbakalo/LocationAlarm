package com.vladbakalo.location_alarm.ui.auth

import com.vladbakalo.location_alarm.mvp.BasePresenter
import com.vladbakalo.location_alarm.navigation.Screens
import ru.terrakok.cicerone.Router

class AuthPresenter(router: Router): BasePresenter<AuthContract.View>(router), AuthContract.Presenter {

    override fun onBackPressed() {
        router.exit()
    }

    override fun start() {
        router.replaceScreen(Screens.AuthLoginScreen)
    }

    override fun stop() {
    }
}