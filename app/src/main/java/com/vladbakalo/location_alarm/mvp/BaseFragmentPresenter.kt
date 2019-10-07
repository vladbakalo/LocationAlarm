package com.vladbakalo.location_alarm.mvp

import ru.terrakok.cicerone.Router

abstract class BaseFragmentPresenter<T: BaseContract.View>(router: Router): BasePresenter<T>(router) {

    abstract fun onBackPressed()
}