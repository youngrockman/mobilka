package com.example.shoesapptest.domain.usecase.validator

class EmailValidator: Validator {
    override fun <T> validate(value: T): Boolean {
        val email = value as? String ?: return false

        val emailChars = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2}\$".toRegex()

        return email.matches(emailChars)
    }
}