package com.example.shoesapptest.screen.signin

data class SignInState (
    var email: String = "",
    var password: String = "",
    var isVisiblePassword: Boolean = false,
    var isLoading: Boolean = false,
    var isSignIn: Boolean = false,
    var errorMessage: String? = null
)