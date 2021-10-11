package com.vladbakalo.core.view.validation

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import com.google.android.material.textfield.TextInputEditText


class AutoValidatedTextInputEditText :TextInputEditText, AutoValidatedUserInputComponent {

    private var validatorList: MutableList<TextValidator> = ArrayList()
    private var isValid = false

    constructor(context: Context) :super(context) {
//        val callback: SimpleCallback =
//        onFocusChangeListener = OnFocusChangeListener(callback)
//        addTextChangedListener(OnTextChangeListener(callback))
    }

    constructor(context: Context, vararg validators: TextValidator) :super(context) {
//        val callback = { payload -> validate() }
//        onFocusChangeListener = OnFocusChangeListener(callback)
//        addTextChangedListener(OnTextChangeListener(callback))

        addValidators(*validators)
    }

    constructor(context: Context, attrs: AttributeSet) :super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :super(context, attrs,
        defStyleAttr)

    /* Could not use streams to ensure backwards compatibility */
    private fun validate() {
        val input = this.text.toString()
        for (validator in validatorList) {
            val output = validator.validate(input)
            if (output != null) {
                error = output
                isValid = false
                return
            }
        }
        isValid = true
    }

    fun addValidators(vararg validators: TextValidator) {
        for (validator in validators) {
            validatorList.add(validator)
        }
    }

    /* Implement the AutoValidatedUserInputComponent interface.
       requestFocus() is already implemented on the View */
    override fun hasValidInput(): Boolean {
        validate()
        return isValid
    }

    /* Simple View.OnFocusChangeListener that calls the provided callback whenever focus is lost */
    private inner class OnFocusChangeListener(var focusLostCallback: SimpleCallback<Void>) :
        View.OnFocusChangeListener {

        override fun onFocusChange(v: View, hasFocus: Boolean) {
            if (!v.hasFocus()) {
                focusLostCallback.call(null)
            }
        }
    }

    /* Simple TextWatcher that calls the provided callback whenever the text is changed. */
    private inner class OnTextChangeListener(var textChangedCallback: SimpleCallback<Void>) :
        TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            textChangedCallback.call(null)
        }

        override fun afterTextChanged(s: Editable) {}
    }

    interface TextValidator {
        /*Outputs null for no errors, or else the error in a user friendly text*/
        fun validate(content: String): String?
    }
}