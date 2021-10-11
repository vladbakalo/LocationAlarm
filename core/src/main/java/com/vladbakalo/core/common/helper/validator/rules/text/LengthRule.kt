package com.vladbakalo.core.common.helper.validator.rules.text

import android.content.Context
import com.vladbakalo.core.R
import com.vladbakalo.core.view.MainEditText
import java.util.*

class LengthRule(private val length: Int, context: Context): BaseEditTextRule(context) {
    override fun validate(view: MainEditText): Boolean {
        return view.getText().isNotBlank() && view.getText().length >= length
    }

    override fun getErrorText(): String {
        return String.format(Locale.getDefault(), context.getString(R.string.must_contains_more_d), length)
    }

    companion object{
        fun newSimpleRule(context: Context): LengthRule{
            return LengthRule(3, context)
        }
    }
}