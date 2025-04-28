package com.example.shoesapptest


sealed class Screen(val route: String) {
    object FirstScreen : Screen("first")
    object SlideScreen : Screen("slide")
    object SignIn : Screen("signin")
    object ForgotPass : Screen("forgotpass")
    object Registration : Screen("registration")
    object Home : Screen("home")
    object Popular : Screen("popular")
}