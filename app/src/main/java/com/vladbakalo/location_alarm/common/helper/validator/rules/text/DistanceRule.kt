package com.vladbakalo.location_alarm.common.helper.validator.rules.text

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.utils.StringUtils
import com.vladbakalo.location_alarm.view.MainEditText
import java.util.*

class DistanceRule: BaseEditTextRule() {
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
            String.format(Locale.getDefault(), StringUtils.getString(R.string.max_distance_d),
                MAX_DISTANCE)
        } else {
            String.format(Locale.getDefault(), StringUtils.getString(R.string.min_distance_d),
                MIN_DISTANCE)
        }
    }

    companion object{
        private const val MIN_DISTANCE = 20
        private const val MAX_DISTANCE = 2000
    }
}