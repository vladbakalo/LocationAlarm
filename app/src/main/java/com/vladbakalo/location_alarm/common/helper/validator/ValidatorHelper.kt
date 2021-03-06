package com.vladbakalo.location_alarm.common.helper.validator

import com.vladbakalo.location_alarm.common.helper.validator.rules.text.BaseEditTextRule
import com.vladbakalo.location_alarm.view.MainEditText

class ValidatorHelper {
    private val validatorList: ArrayList<IValidator> = ArrayList()

    fun validate(): Boolean{
        var isValid = true
        for (item in validatorList){
            isValid = item.validate() && isValid
        }
        return isValid
    }

    fun addValidator(validator: IValidator): ValidatorHelper{
        validatorList.add(validator)
        return this
    }

    fun addEditTextValidator(view: MainEditText, vararg rules: BaseEditTextRule){
        val textValidator = TextValidator(view)
        for (rule in rules){
            textValidator.addRule(rule)
        }
        validatorList.add(textValidator)
    }
}