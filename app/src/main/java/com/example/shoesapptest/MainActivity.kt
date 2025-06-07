package com.example.shoesapptest


import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.bundle.Bundle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pypypy.ui.screen.home.HomeScreenHast
import com.example.shoesapp.ui.screen.SignInScreen
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.data.local.DataStoreManager
import com.example.shoesapptest.data.local.DataStoreOnBoarding
import com.example.shoesapptest.screen.StartsScreens.FirstScreen
import com.example.shoesapptest.screen.StartsScreens.SlideScreen
import com.example.shoesapptest.screen.cart.CartScreen
import com.example.shoesapptest.screen.cart.CartViewModel
import com.example.shoesapptest.screen.favorite.FavoriteScreen
import com.example.shoesapptest.screen.forgotpassword.ForgotPassScreen
import com.example.shoesapptest.screen.home.PopularSneakersViewModel
import com.example.shoesapptest.screen.listing.OutdoorScreen
import com.example.shoesapptest.screen.popular.PopularScreen
import com.example.shoesapptest.screen.regscreen.RegisterAccountScreen
import com.example.shoesapptest.screen.search.SearchScreen
import com.example.shoesapptest.screen.search.SneakersViewModel
import com.example.shoesapptest.screen.verification.VerificationScreen
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.viewmodel.koinViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    @SuppressLint("ComposableDestinationInComposeScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MatuleTheme {
                val navController = rememberNavController()
                val dataStore = DataStoreOnBoarding(LocalContext.current)
                val popularVm: PopularSneakersViewModel = getViewModel()
                val context = LocalContext.current
                val viewModel: SneakersViewModel = getViewModel()

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
                        SlideScreen(
                            onNavigateToAuthScreen = {
                                navController.navigate(Screen.SignIn.route) {
                                    popUpTo(Screen.SlideScreen.route) { inclusive = true }
                                }
                            },
                            dataStore = dataStore
                        )
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
                            onNavigateToSignInScreen = { navController.popBackStack() },
                            navController = navController
                        )
                    }

                    composable(Screen.Verification.route) {
                        VerificationScreen(navController = navController)
                    }

                    composable(Screen.Registration.route) {
                        RegisterAccountScreen(
                            onNavigationToSigninScreen = {
                                navController.popBackStack()
                            }
                        )
                    }


                    composable(Screen.Home.route) {
                        HomeScreenHast(
                            navController = navController,
                            viewModel = popularVm
                        )
                    }
                    composable(Screen.Popular.route) {
                        PopularScreen(navController)
                    }

                    composable(Screen.Favorite.route) {
                        FavoriteScreen(
                            navController = navController,
                            viewModel = popularVm
                        )
                    }
                    composable(Screen.Outdoor.route) {
                        OutdoorScreen(navController)
                    }

                    composable(Screen.SearchScreen.route) {
                        SearchScreen(
                            viewModel = viewModel, navController = navController
                        )
                    }


                    composable(Screen.Cart.route) {
                        val viewModel: CartViewModel = koinViewModel()
                        CartScreen(navController, viewModel)
                    }

                }
            }
        }
    }
}