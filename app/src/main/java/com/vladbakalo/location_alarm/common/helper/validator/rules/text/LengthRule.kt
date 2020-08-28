package com.vladbakalo.location_alarm.common.helper.validator.rules.text

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.utils.StringUtils
import com.vladbakalo.location_alarm.view.MainEditText
import java.util.*

class LengthRule(private val length: Int): BaseEditTextRule() {
    override fun validate(view: MainEditText): Boolean {
        return !StringUtils.isEmpty(view.getText()) && view.getText().length >= length
    }

    override fun getErrorText(): String {
        return String.format(Locale.getDefault(), StringUtils.getString(R.string.must_contains_more_d), length)
    }

    companion object{
        fun newSimpleRule(): LengthRule{
            return LengthRule(3)
        }
    }
}