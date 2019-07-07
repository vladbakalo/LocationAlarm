package com.vladbakalo.location_alarm.application

import androidx.annotation.CallSuper

abstract class BasePresenter<T: BaseContract.View>: BaseContract.Presenter<T> {

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