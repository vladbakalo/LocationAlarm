package com.vladbakalo.core.data

import android.content.Context

class ErrorState{
    private var errorText: String? = null
    private var errorTextId: Int? = null

    constructor(errorTextStr: String){
        errorText = errorTextStr
    }

    constructor(errorTextResId: Int){
        errorTextId = errorTextResId
    }

    fun getErrorText(context: Context): String{
        if (errorText != null){
            return errorText!!
        }
        return context.getString(errorTextId!!)
    }
}