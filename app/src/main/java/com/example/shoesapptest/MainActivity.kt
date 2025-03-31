package com.example.shoesapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoesapp.ui.screen.SignInScreen
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.screen.StartsScreens.FirstScreen
import com.example.shoesapptest.screen.StartsScreens.SlideScreen
import com.example.shoesapptest.screen.forgotpassword.ForgotPassScreen
import com.example.shoesapptest.screen.regscreen.RegisterAccountScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MatuleTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.FirstScreen.route
                ) {
                    composable(Screen.FirstScreen.route) {
                        FirstScreen {
                            navController.navigate(Screen.SlideScreen.route) {
                                popUpTo(Screen.FirstScreen.route) { inclusive = true }
                            }
                        }
                    }

                    composable(Screen.SlideScreen.route) {
                        SlideScreen {
                            navController.navigate(Screen.SignIn.route) {
                                popUpTo(Screen.SlideScreen.route) { inclusive = true }
                            }
                        }
                    }

                    composable(Screen.SignIn.route) {
                        SignInScreen(
                            onNavigationToRegScreen = {
                                navController.navigate(Screen.Registration.route)
                            },
                            onSignInSuccess = {
                                // Добавить навигацию после успешного входа
                            },
                            navController = navController
                        )
                    }

                    composable(Screen.ForgotPass.route) {
                        ForgotPassScreen(
                            onNavigateToSignInScreen = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(Screen.Registration.route) {
                        RegisterAccountScreen(
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