package com.example.shoesapptest.screen.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.R
import com.example.shoesapptest.screen.forgotpassword.component.ForgotPassField
import com.example.shoesapptest.screen.forgotpassword.component.SendButton
import com.example.shoesapptest.screen.signin.component.TitleWithSubtitleText


@Composable
fun ForgotPassScreen(onNavigateToSignInScreen: () -> Unit) {
    val forgotPassViewModel: ForgotPassViewModel = viewModel()
    val showDialog = remember { mutableStateOf(false) }

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
        }
    ) { paddingValues ->
        ForgotPassContent(paddingValues, showDialog, forgotPassViewModel)
    }


    if (showDialog.value) {
        EmailSentDialog(onDismiss = { showDialog.value = false })
    }
}

@Composable
fun ForgotPassContent(
    paddingValues: PaddingValues,
    showDialog: MutableState<Boolean>,
    forgotPassViewModel: ForgotPassViewModel
) {
    val changePass = forgotPassViewModel.changePass
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleWithSubtitleText(
            title = "Забыл пароль",
            subText = "Введите свою учетную запись\nдля сброса"
        )

        Spacer(modifier = Modifier.height(35.dp))

        ForgotPassField(
            value = changePass.value.email,
            onChangeValue = {forgotPassViewModel.setEmail(it)},
            isError = forgotPassViewModel.emailHasError.value,
            supportingText = { Text(text = stringResource(R.string.LoginError)) },
            placeholder = { Text(text = stringResource(R.string.template_email))},
            label = { Text(text = stringResource(R.string.email))}
        )


        SendButton(onClick = {showDialog.value = true}) {
            Text(text = "Отправить")
        }
    }
}


@Composable
fun EmailSentDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.email_image),
                        contentDescription = null,
                        tint = MatuleTheme.colors.accent,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Text(
                    text = "Проверьте Ваш Email",
                    style = MatuleTheme.typography.bodyRegular16.copy(color = MatuleTheme.colors.text),
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Text(
                text = "Мы отправили код восстановления пароля на вашу электронную почту.",
                textAlign = TextAlign.Center,
                style = MatuleTheme.typography.bodyRegular14.copy(color = MatuleTheme.colors.hint)
            )
        },
        modifier = Modifier.clip(RoundedCornerShape(14.dp)),
        containerColor = Color.White
    )
}
















