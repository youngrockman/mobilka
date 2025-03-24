package com.example.shoesapptest.screen.regscreen

data class Registration(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var isVisiblePassword: Boolean = false,
    var errorMessage: String? = null
)