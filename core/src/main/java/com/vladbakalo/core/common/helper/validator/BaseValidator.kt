package com.vladbakalo.core.common.helper.validator

import android.view.View
import com.vladbakalo.core.common.helper.validator.rules.BaseRule

abstract class BaseValidator<T: View>(val view: T): IValidator {

    private val rules: ArrayList<BaseRule<T>> = ArrayList()

    override fun validate(): Boolean{
        for (item in rules){
            if (!item.validate(view)){
                item.setErrorState(view)
                return false
            }
        }
        return true
    }

    fun addRule(rule: BaseRule<T>): BaseValidator<T>{
        rules.add(rule)
        return this
    }
}