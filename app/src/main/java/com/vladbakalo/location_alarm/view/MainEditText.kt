package com.vladbakalo.location_alarm.view

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.common.extentions.getLayoutInflater
import com.vladbakalo.location_alarm.databinding.ViewMainEditTextBinding

class MainEditText(context: Context, attrs: AttributeSet): TextInputLayout(context, attrs) {

    private val binding: ViewMainEditTextBinding

    init{
        binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.view_main_edit_text, this, true)
        context.theme.obtainStyledAttributes(attrs,
            R.styleable.MainEditText,
            0, 0).apply {
            try {
                binding.mainEditTextTilLayout.hint = getString(R.styleable.MainEditText_mainEditTextHint)
            } finally {
                recycle()
            }
        }
    }
}