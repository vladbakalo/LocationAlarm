package com.vladbakalo.location_alarm.common.helper.validator.rules.text

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.utils.StringUtils
import com.vladbakalo.location_alarm.view.MainEditText

class RegexEditTextRule(private val regex: String): BaseEditTextRule() {
    override fun validate(view: MainEditText): Boolean {
        return view.getText().matches(Regex.fromLiteral(regex))
    }

    override fun getErrorText(): String {
        return StringUtils.getString(R.string.value_not_valid)
    }
}