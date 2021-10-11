package com.vladbakalo.core.view

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.FrameLayout
import com.google.android.material.textfield.TextInputLayout
import com.vladbakalo.core.R
import com.vladbakalo.core.common.extentions.getLayoutInflater
import com.vladbakalo.core.databinding.ViewMainEditTextBinding


class MainEditText(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {

    private val binding: ViewMainEditTextBinding
    private var type: Type = Type.NAME

    private var textChangeListener: ((String) -> Unit)? = null
    private val textWatcher = object: TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(str: Editable?) {
            textChangeListener?.invoke(str?.toString() ?: "")
        }
    }

    init{
        binding = ViewMainEditTextBinding.inflate(context.getLayoutInflater(),this, true)
        context.theme.obtainStyledAttributes(attrs,
            R.styleable.MainEditText,
            0, 0).apply {
            try {
                binding.mainEditTextTilLayout.hint = getString(R.styleable.MainEditText_mainEditTextHint)
                binding.mainEditTextTilLayout.helperText = getString(R.styleable.MainEditText_mainEditTextHelper)
                type = Type.values()[getInt(R.styleable.MainEditText_mainEditTextType, 0)]
            } finally {
                recycle()
            }
        }

        applyType()
        binding.mainEditTextTilText.addTextChangedListener(textWatcher)
    }

    private fun applyType(){
        when (type){
            Type.LATITUDE, Type.LONGITUDE-> getInputEditText().inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
            Type.DISTANCE -> getInputEditText().inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
    }

    fun getInputEditText(): EditText = binding.mainEditTextTilText

    fun getInputTextLayout(): TextInputLayout = binding.mainEditTextTilLayout

    fun getText(): CharSequence = binding.mainEditTextTilText.text ?: ""

    fun setText(text: String?){
        getInputEditText().setText(text)
    }

    fun setTextChangeListener(listener: (String) -> Unit){
        textChangeListener = listener
    }

    override fun onSaveInstanceState(): Parcelable? {
        val baseState = super.onSaveInstanceState()
        val mainEditTextState = MainEditTextSavedState(baseState)
        mainEditTextState.errorText = getInputTextLayout().error?.toString() ?: ""

        return mainEditTextState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as MainEditTextSavedState
        super.onRestoreInstanceState(savedState.superState)

        getInputTextLayout().post {
            getInputTextLayout().error = savedState.errorText
        }
    }

    private class MainEditTextSavedState : BaseSavedState{
        var errorText: String? = null

        constructor(parcel: Parcel): super(parcel){
            errorText = parcel.readString() ?: ""
        }

        constructor(superState: Parcelable?): super(superState)

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeString(errorText)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR :Parcelable.Creator<MainEditTextSavedState> {
            override fun createFromParcel(parcel: Parcel): MainEditTextSavedState {
                return MainEditTextSavedState(parcel)
            }

            override fun newArray(size: Int): Array<MainEditTextSavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

    enum class Type{
        NAME, ADDRESS, LATITUDE, LONGITUDE, DISTANCE
    }
}