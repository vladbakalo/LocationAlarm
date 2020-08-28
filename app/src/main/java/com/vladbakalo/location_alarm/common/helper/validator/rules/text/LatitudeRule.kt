package com.vladbakalo.location_alarm.common.helper.validator.rules.text

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.utils.StringUtils
import com.vladbakalo.location_alarm.view.MainEditText

class LatitudeRule: BaseEditTextRule() {
    override fun validate(view: MainEditText): Boolean {
        val value = view.getText().toString().toDoubleOrNull() ?: return false

        if (value < -90 || value > 90) {
            return false
        }
        return true
    }

    override fun getErrorText(): String {
        return StringUtils.getString(R.string.value_not_valid)
    }
}