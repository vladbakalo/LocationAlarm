package com.vladbakalo.core.common.helper.validator.rules.text

import android.content.Context
import com.vladbakalo.core.R
import com.vladbakalo.core.view.MainEditText

class RegexEditTextRule(private val regex: String, context: Context): BaseEditTextRule(context) {
    override fun validate(view: MainEditText): Boolean {
        return view.getText().matches(Regex.fromLiteral(regex))
    }

    override fun getErrorText(): String {
        return context.getString(R.string.value_not_valid)
    }
}