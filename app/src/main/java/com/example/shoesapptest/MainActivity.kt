package com.example.shoesapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoesapp.ui.screen.SigninScreen
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.screen.forgotpassword.ForgotPassScreen
import com.example.shoesapptest.screen.regscreen.RegisterAccountScreen
import com.example.shoesapptest.screen.regscreen.RegistrationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatuleTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "signin"
                ) {
                    composable("signin") {
                        SigninScreen(
                            onNavigationToRegScreen = {
                                navController.navigate("registration")
                            },
                            navController = navController
                        )
                    }
                    composable("forgotpass") {
                        ForgotPassScreen(onNavigateToSignInScreen = {
                            navController.navigate("signin")
                        })
                    }
                    composable("registration") {
                        RegisterAccountScreen(
                            registrationScreen = RegistrationScreen("Test"),
                            onNavigationToSigninScreen = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}