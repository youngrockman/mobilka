package com.example.shoesapptest.domain.usecase.validator

interface Validator {
    fun <T> validate(value: T ): Boolean
}