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
import com.example.shoesapptest.data.local.DataStore
import com.example.shoesapptest.data.remote.network.RetrofitClient
import com.example.shoesapptest.data.repository.AuthRepository
import com.example.shoesapptest.di.appModules
import com.example.shoesapptest.domain.usecase.AuthUseCase
import com.example.shoesapptest.screen.forgotpassword.ForgotPassScreen
import com.example.shoesapptest.screen.regscreen.RegisterAccountScreen
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MatuleTheme {

                if (GlobalContext.getOrNull() == null) {
                    startKoin {
                        modules(appModules)
                    }
                }

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.SignIn.route
                ) {
                    composable(Screen.SignIn.route) {
                        SigninScreen(
                            onNavigationToRegScreen = {
                                navController.navigate(Screen.Registration.route)
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