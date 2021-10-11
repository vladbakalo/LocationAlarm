package com.vladbakalo.core.common.helper.validator.rules.text

import android.content.Context
import com.vladbakalo.core.R
import com.vladbakalo.core.view.MainEditText
import java.util.*

class DistanceRule (context: Context): BaseEditTextRule(context) {
    private var isGreater = true

    override fun validate(view: MainEditText): Boolean {
        val value = view.getText().toString().toDoubleOrNull() ?: return false

        if (value > MAX_DISTANCE) {
            isGreater = true
            return false
        } else if (value < MIN_DISTANCE){
            isGreater = false
            return false
        }
        return true
    }

    override fun getErrorText(): String {
        return if (isGreater){
            String.format(Locale.getDefault(), context.getString(R.string.max_distance_d),
                MAX_DISTANCE)
        } else {
            String.format(Locale.getDefault(), context.getString(R.string.min_distance_d),
                MIN_DISTANCE)
        }
    }

    companion object{
        private const val MIN_DISTANCE = 20
        private const val MAX_DISTANCE = 2000
    }
}