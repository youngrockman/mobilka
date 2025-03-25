package com.example.shoesapptest.screen.regscreen

data class RegistrationScreenState(
    var email: String = "",
    var name: String = "",
    var password: String = "",
    var isVisiblePassword: Boolean = false,
    var errorMessage: String? = null,
    var isLoading: Boolean = false,
    var isSignedIn: Boolean = false,
    var emailHasError: Boolean = false
)