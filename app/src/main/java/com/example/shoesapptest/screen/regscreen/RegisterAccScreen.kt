package com.example.shoesapptest.screen.regscreen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.R
import com.example.shoesapptest.screen.regscreen.component.RegisterButton
import com.example.shoesapptest.screen.regscreen.component.RegistrationTextField
import com.example.shoesapptest.screen.regscreen.component.TitleAndSubTitle
import com.example.shoesapptest.screen.signin.component.AuthTextField

data class RegistrationScreen(val sampleText: String)

@Composable
fun RegisterAccountScreen(
    registrationScreen: RegistrationScreen,
    onNavigationToSigninScreen: () -> Unit
) {

    val registrationViewModel: RegistrationViewModel = viewModel()
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 35.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.back_arrow),
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Есть аккаунт? Войти",
                    style = MatuleTheme.typography.bodyRegular16.copy(color = MatuleTheme.colors.text),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { paddingValues ->
        RegisterAccountContent(
            paddingValues = paddingValues,
            registrationViewModel = registrationViewModel,
            registrationScreen = registrationScreen,
            onNavigationToSigninScreen = onNavigationToSigninScreen
        )
    }
}

@Composable
fun RegisterAccountContent(
    paddingValues: PaddingValues,
    registrationViewModel: RegistrationViewModel,
    registrationScreen: RegistrationScreen,
    onNavigationToSigninScreen: () -> Unit
) {
    val reg = registrationViewModel.registration
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        TitleAndSubTitle(
            title = "Регистрация",
            subText = "Заполните Свои данные или продолжите через социальные медиа"
        )

        Spacer(modifier = Modifier.height(20.dp))

        RegistrationTextField(
            value = reg.value.name,
            onChangeValue = { registrationViewModel.setUserName(it) },
            isError = false,
            supportingText = { Text(text = "Неверное имя пользователя") },
            placeholder = { Text(text = stringResource(R.string.PasswordPlaceHolder)) },
            label = { Text(text = "Ваше имя") }
        )

        RegistrationTextField(
            value = reg.value.email,
            onChangeValue = { registrationViewModel.setEmail(it) },
            isError = registrationViewModel.emailHasError.value,
            supportingText = { Text(text = stringResource(R.string.LoginError)) },
            placeholder = { Text(text = stringResource(R.string.template_email)) },
            label = { Text(text = stringResource(R.string.email)) },
        )

        RegistrationTextField(
            value = reg.value.password,
            onChangeValue = { registrationViewModel.setPassword(it) },
            isError = false,
            supportingText = { Text(text = stringResource(R.string.PasswordError)) },
            placeholder = { Text(text = stringResource(R.string.PasswordPlaceHolder)) },
            label = { Text(text = stringResource(R.string.Password)) }
        )

        SimpleCheckbox()

        Text(registrationScreen.sampleText)
        RegisterButton(onClick = onNavigationToSigninScreen) {
            Text(text = stringResource(R.string.Registration))
        }
    }
}

@Composable
fun SimpleCheckbox() {
    val isChecked = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it }
        )
        ClickableText(
            text = AnnotatedString(
                "Даю согласие на обработку персональных данных"
            ),
            onClick = {},
            style = MatuleTheme.typography.bodyRegular16.copy(color = MatuleTheme.colors.text),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}