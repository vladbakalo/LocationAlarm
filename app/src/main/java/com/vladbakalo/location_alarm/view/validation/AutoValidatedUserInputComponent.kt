package com.vladbakalo.location_alarm.view.validation

interface AutoValidatedUserInputComponent {
    fun hasValidInput(): Boolean
    fun requestFocus(): Boolean
}