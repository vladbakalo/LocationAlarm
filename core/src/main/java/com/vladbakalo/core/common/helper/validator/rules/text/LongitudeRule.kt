package com.vladbakalo.core.common.helper.validator.rules.text

import android.content.Context
import com.vladbakalo.core.R
import com.vladbakalo.core.view.MainEditText

class LongitudeRule (context: Context): BaseEditTextRule(context) {
    override fun validate(view: MainEditText): Boolean {
        val value = view.getText().toString().toDoubleOrNull() ?: return false

        if (value < -180 || value > 180) {
            return false
        }
        return true
    }

    override fun getErrorText(): String {
        return context.getString(R.string.value_not_valid)
    }
}