package com.example.shoesapptest


sealed class Screen(val route: String) {
    object SignIn : Screen("signin")
    object ForgotPass : Screen("forgotpass")
    object Registration : Screen("registration")
}