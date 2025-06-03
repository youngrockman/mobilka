package com.example.shoesapptest


sealed class Screen(val route: String) {
    object FirstScreen : Screen("first")
    object SlideScreen : Screen("slide")
    object SignIn : Screen("signin")
    object ForgotPass : Screen("forgotpass")
    object Registration : Screen("registration")
    object Home : Screen("home")
    object Popular : Screen("popular")
    object Favorite : Screen("favorite")
    object Outdoor : Screen("listing")
    object Verification : Screen("verification")
    object SearchScreen: Screen("search")
}