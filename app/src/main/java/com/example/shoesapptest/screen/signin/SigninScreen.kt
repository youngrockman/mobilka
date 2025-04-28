package com.example.shoesapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.R
import com.example.shoesapptest.Screen
import com.example.shoesapptest.common.CommonButton
import com.example.shoesapptest.screen.signin.SignInViewModel
import com.example.shoesapptest.screen.signin.component.AuthButton
import com.example.shoesapptest.screen.signin.component.AuthTextField
import com.example.shoesapptest.screen.signin.component.TitleWithSubtitleText
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(
    onNavigationToRegScreen: () -> Unit,
    onSignInSuccess: () -> Unit,
    navController: NavController
) {
    val viewModel: SignInViewModel = koinViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.signInState.value.isSignIn) {
        if (viewModel.signInState.value.isSignIn) {
            onSignInSuccess()
        }
    }

    LaunchedEffect(viewModel.signInState.value.errorMessage) {
        viewModel.signInState.value.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.setErrorMessage(null)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 35.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(R.drawable.back_arrow),
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onNavigationToRegScreen() }
            ) {
                Text(
                    stringResource(R.string.sign_up),
                    style = MatuleTheme.typography.bodyRegular16.copy(color = MatuleTheme.colors.text),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { paddingValues ->
        SignInContent(
            paddingValues = paddingValues,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun SignInContent(
    paddingValues: PaddingValues,
    viewModel: SignInViewModel,
    navController: NavController
) {
    val state = viewModel.signInState.value

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleWithSubtitleText(
            title = stringResource(R.string.hello),
            subText = stringResource(R.string.sign_in_subtitle)
        )
        Spacer(modifier = Modifier.height(35.dp))

        AuthTextField(
            value = state.email,
            onChangeValue = { viewModel.setEmail(it) },
            isError = viewModel.emailHasError.value,
            supportingText = { Text(text = stringResource(R.string.LoginError))},
            placeholder = { Text(text = stringResource(R.string.template_email)) },
            label = { Text(text = stringResource(R.string.email)) }
        )

        AuthTextField(
            value = state.password,
            onChangeValue = { viewModel.setPassword(it) },
            isError = false,
            supportingText = { Text(text = "Неверный пароль")},
            placeholder = { Text(text = stringResource(R.string.PasswordPlaceHolder)) },
            label = { Text(text = stringResource(R.string.Password)) }
        )

        Text(
            text = "Забыл пароль",
            style = MatuleTheme.typography.bodyRegular16.copy(color = MatuleTheme.colors.text),
            modifier = Modifier
                .clickable {
                    navController.navigate("forgotpass")
                }
                .padding(top = 8.dp)
        )


        AuthButton(
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.SignIn.route) { inclusive = true }
                }
            },
            enabled = true
        ) {
            Text(stringResource(R.string.Sign_In))
        }
    }
}






