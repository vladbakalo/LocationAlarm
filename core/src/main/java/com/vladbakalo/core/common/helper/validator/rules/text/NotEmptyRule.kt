package com.vladbakalo.core.common.helper.validator.rules.text

import android.content.Context
import com.vladbakalo.core.R
import com.vladbakalo.core.view.MainEditText

class NotEmptyRule (context: Context): BaseEditTextRule(context) {

    override fun validate(view: MainEditText): Boolean {
        return view.getText().isNotBlank()
    }

    override fun getErrorText(): String {
        return context.getString(R.string.field_empty)
    }
}