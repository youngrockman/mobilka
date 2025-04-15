package com.example.shoesapptest.domain.usecase.validator

class PasswordValidator : Validator {
    override fun <T> validate(value: T): Boolean {
        val password = value as? String ?: return false


        if (password.length < 8) return false

        if (!password.any { it.isUpperCase() }) return false

        if (!password.any { it.isDigit() }) return false

        val specialChars = setOf('!', '@', '#', '$', '%', '^', '&', '*')
        if (!password.any { it in specialChars }) return false

        return true
    }
}