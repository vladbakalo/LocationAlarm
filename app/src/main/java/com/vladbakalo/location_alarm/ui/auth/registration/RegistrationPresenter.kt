package com.vladbakalo.location_alarm.ui.auth.registration

import com.vladbakalo.location_alarm.mvp.BaseFragmentPresenter
import ru.terrakok.cicerone.Router

class RegistrationPresenter(router: Router): BaseFragmentPresenter<RegistrationContract.View>(router), RegistrationContract.Presenter {

    override fun start() {
    }

    override fun stop() {
    }

    override fun onBackPressed() {
        router.exit()
    }
}