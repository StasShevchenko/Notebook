package com.example.notebook.feature_notebook.domain.util

sealed class ErrorType{
    object NameError: ErrorType()
    object SecondNameError: ErrorType()
    object PatronymicError: ErrorType()
    object DateOfBirthdayError: ErrorType()
    object PhoneNumberError: ErrorType()

}
