package com.vladbakalo.location_alarm.common.helper.validator.rules.text

import com.vladbakalo.location_alarm.common.helper.validator.rules.BaseRule
import com.vladbakalo.location_alarm.view.MainEditText

abstract class BaseEditTextRule :BaseRule<MainEditText>{

    override fun setErrorState(view: MainEditText) {
        view.getInputTextLayout().error = getErrorText()
    }
}