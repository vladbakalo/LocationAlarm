package com.vladbakalo.core.common.helper.validator.rules

import android.view.View

interface BaseRule<T :View> {
    fun validate(view: T): Boolean

    fun getErrorText(): String

    fun setErrorState(view: T)
}