package com.vladbakalo.location_alarm.common.helper.validator.rules.text

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.utils.StringUtils
import com.vladbakalo.location_alarm.view.MainEditText

class LongitudeRule: BaseEditTextRule() {
    override fun validate(view: MainEditText): Boolean {
        val value = view.getText().toString().toDoubleOrNull() ?: return false

        if (value < -180 || value > 180) {
            return false
        }
        return true
    }

    override fun getErrorText(): String {
        return StringUtils.getString(R.string.value_not_valid)
    }
}