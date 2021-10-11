package com.vladbakalo.core.view.validation

interface AutoValidatedUserInputComponent {
    fun hasValidInput(): Boolean
    fun requestFocus(): Boolean
}