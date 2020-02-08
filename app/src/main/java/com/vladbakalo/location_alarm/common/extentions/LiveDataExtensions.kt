package com.vladbakalo.location_alarm.common.extentions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.setValueSafe(value: T) {
    if (getValue()?.equals(value)?.not() != false) {
        setValue(value)
    }
}