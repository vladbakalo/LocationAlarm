package com.vladbakalo.core.view.validation

interface SimpleCallback<T> {
    fun call(payload: T?)
}