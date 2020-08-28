package com.vladbakalo.location_alarm.common.utils

import androidx.annotation.StringRes
import com.vladbakalo.location_alarm.application.App

object StringUtils {
    fun isEmpty(str: CharSequence?): Boolean{
        return str == null || str.trim().isEmpty()
    }

    fun getString(@StringRes resId: Int): String{
        return App.context!!.getString(resId)
    }
}