package com.example.shoesapptest


import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.bundle.Bundle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pypypy.ui.screen.home.HomeScreenHast
import com.example.shoesapp.ui.screen.SignInScreen
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.screen.StartsScreens.FirstScreen
import com.example.shoesapptest.screen.StartsScreens.SlideScreen
import com.example.shoesapptest.screen.forgotpassword.ForgotPassScreen
import com.example.shoesapptest.screen.popular.PopularScreen
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
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.SignIn.route) { inclusive = true }
                                }
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


                    composable(Screen.Home.route) {
                        HomeScreenHast(navController)
                    }

                    composable(Screen.Popular.route) {
                        PopularScreen(navController)
                    }

                }
            }
        }
    }
}