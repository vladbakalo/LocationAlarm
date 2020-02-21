package com.vladbakalo.location_alarm.common.helper.validator

import android.text.Editable
import android.text.TextWatcher
import com.vladbakalo.location_alarm.view.MainEditText

class TextValidator(mainEditText: MainEditText) :BaseValidator<MainEditText>(mainEditText){

    init {
        mainEditText.getInputEditText().addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainEditText.getInputTextLayout().error = ""
            }
        })
    }
}