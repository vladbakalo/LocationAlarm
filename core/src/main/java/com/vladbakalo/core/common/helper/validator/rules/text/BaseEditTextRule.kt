package com.vladbakalo.core.common.helper.validator.rules.text

import android.content.Context
import com.vladbakalo.core.common.helper.validator.rules.BaseRule
import com.vladbakalo.core.view.MainEditText

abstract class BaseEditTextRule(val context: Context) :BaseRule<MainEditText>{

    override fun setErrorState(view: MainEditText) {
        view.getInputTextLayout().error = getErrorText()
    }
}