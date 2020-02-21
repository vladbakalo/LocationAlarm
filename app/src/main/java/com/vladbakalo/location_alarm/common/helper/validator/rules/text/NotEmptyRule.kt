package com.vladbakalo.location_alarm.common.helper.validator.rules.text

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.utils.StringUtils
import com.vladbakalo.location_alarm.view.MainEditText

class NotEmptyRule :BaseEditTextRule() {

    override fun validate(view: MainEditText): Boolean {
        return !StringUtils.isEmpty(view.getText())
    }

    override fun getErrorText(): String {
        return StringUtils.getString(R.string.field_empty)
    }

}