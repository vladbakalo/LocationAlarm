package com.vladbakalo.core.common.extentions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.setValueSafe(value: T) {
    if (getValue()?.equals(value) == false) {
        setValue(value)
    }
}

fun <T> MutableLiveData<T>.notify() {
    postValue(value)
}