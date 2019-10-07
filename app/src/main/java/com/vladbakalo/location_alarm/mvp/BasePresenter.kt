package com.vladbakalo.location_alarm.mvp

import androidx.annotation.CallSuper
import ru.terrakok.cicerone.Router

abstract class BasePresenter<T: BaseContract.View>(val router: Router):
    BaseContract.Presenter<T> {

    var view: T? = null
        private set

    @CallSuper
    override fun bind(view: T) {
        this.view = view
    }

    @CallSuper
    override fun unbind() {
        view = null
    }

}