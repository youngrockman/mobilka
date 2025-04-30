package com.example.shoesapptest.screen.regscreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.shoesapptest.R
import com.example.shoesapptest.common.CommonTextField


@Composable
fun RegistrationTextField(
    value: String,
    onChangeValue: (String)-> Unit,
    isError: Boolean,
    isPassword: Boolean = false,
    supportingText: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
    label: @Composable () -> Unit
) {
    val passwordVisible = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        label()
        CommonTextField(
            value = value,
            onChangeValue = onChangeValue,
            isError = isError,
            visualTransformation = if (isPassword && !passwordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible.value) R.drawable.eye_open
                                else R.drawable.eye_close
                            ),
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }
            },
            supportingText = supportingText,
            placeHolder = placeholder
        )
    }
}