package com.example.shoesapptest.screen

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.R

@Composable
fun RegisterAccountScreen(){
    Scaffold (
        topBar = {
            Row(modifier = Modifier
                .padding(top = 35.dp)
                .fillMaxWidth()
                .height(40.dp)
            ){
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.back_arrow),
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            Row(modifier = Modifier
                .padding(bottom = 50.dp)
                .fillMaxWidth()
                .height(40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = "Есть аккаунт? Войти",
                    style = MatuleTheme.typography.bodyRegular16.copy(color = MatuleTheme.colors.text),
                    textAlign = TextAlign.Center
                )
            }
        }
    ){paddingValues -> RegisterAccountContent(paddingValues) }
}

@Composable
fun RegisterAccountContent(paddingValues: PaddingValues){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        TitleAndSubTitle(
            title = "Регистрация",
            subText = "Заполните Свои данные или продолжите через социальные медиа"
        )

        val name = remember { mutableStateOf("") }
        Spacer(modifier = Modifier.height(20.dp))
        NameTextField(
            labelText = "Ваше имя",
            value = name.value,
            onChangeValue = {name.value = it},
            placeHolderText = "xxxxxxxx"
        )

        val email = remember { mutableStateOf("") }
        Spacer(modifier = Modifier.height(20.dp))
        EmailTextField(
            labelText = "Email",
            value = email.value,
            onChangeValue = {email.value = it},
            placeHolderText = stringResource(R.string.template_email)
        )

        val password = remember { mutableStateOf("") }
        Spacer(modifier = Modifier.height(20.dp))
        PasswordTextField(
            labelText = "Пароль",
            value = password.value,
            onChangeValue = {password.value = it}
        )


        SimpleCheckbox()


        RegisterButton(
            modifier = Modifier
                .padding(top = 50.dp),
                buttonlabel = "Зарегистрироваться"
        ){}
    }
}


@Composable
fun TitleAndSubTitle(title:String, subText:String){
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text= title,
            style = MatuleTheme.typography.headingBold32.copy(color = MatuleTheme.colors.text),
            textAlign = TextAlign.Center
        )
        Text(
            text = subText,
            style = MatuleTheme.typography.subTitleRegular16.copy(color = MatuleTheme.colors.subTextDark),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RegisterButton(modifier: Modifier,buttonlabel:String,onClick: () -> Unit){
    Button(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MatuleTheme.colors.accent),
        colors = ButtonColors(
            contentColor = MatuleTheme.colors.accent,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = MatuleTheme.colors.accent,
            containerColor = Color.Transparent
        ),
        onClick = {}

    ) { Text(
        text = buttonlabel,
        style = MatuleTheme.typography.bodyRegular14.copy(color = MatuleTheme.colors.background),
        textAlign = TextAlign.Center

    ) }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameTextField(
    value: String,
    onChangeValue: (String) -> Unit,
    labelText: String? = null,
    placeHolderText: String? = null
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (labelText != null) {
            Text(
                text = labelText,
                style = MatuleTheme.typography.bodyRegular16.copy(color = MatuleTheme.colors.text),
                textAlign = TextAlign.Center
            )
        }

        val interaction = remember { MutableInteractionSource() }
        BasicTextField(
            value = value,
            onValueChange = { onChangeValue(it) },
            modifier = Modifier
                .fillMaxWidth()
                .background(MatuleTheme.colors.background)
                .clip(RoundedCornerShape(14.dp)),
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interaction,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MatuleTheme.colors.background,
                    disabledContainerColor = MatuleTheme.colors.background,
                    unfocusedContainerColor = MatuleTheme.colors.background,
                    errorContainerColor = MatuleTheme.colors.background,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    if (placeHolderText != null) {
                        Text(
                            text = placeHolderText,
                            style = MatuleTheme.typography.bodyRegular14.copy(color = MatuleTheme.colors.hint)
                        )
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    value: String,
    onChangeValue: (String) -> Unit,
    placeHolderText: String? = null,
    labelText: String? = null
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (labelText != null) {
            Text(
                text = labelText,
                style = MatuleTheme.typography.bodyRegular16.copy(MatuleTheme.colors.text),
                textAlign = TextAlign.Right
            )
        }
        val interaction = remember { MutableInteractionSource() }
        BasicTextField(
            value = value,
            onValueChange = { onChangeValue(it) },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .background(MatuleTheme.colors.background)
                .clip(RoundedCornerShape(14.dp)),
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                singleLine = true,
                innerTextField = innerTextField,
                enabled = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interaction,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MatuleTheme.colors.background,
                    disabledContainerColor = MatuleTheme.colors.background,
                    unfocusedContainerColor = MatuleTheme.colors.background,
                    errorContainerColor = MatuleTheme.colors.background,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    if (placeHolderText != null)
                        Text(
                            text = placeHolderText,
                            style = MatuleTheme.typography.bodyRegular14.copy(color = MatuleTheme.colors.hint),
                        )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    value: String,
    onChangeValue: (String) -> Unit,
    labelText: String? = null
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val passwordVisual = remember { mutableStateOf(false) } // Изначально пароль скрыт

        if (labelText != null) {
            Text(
                text = labelText,
                style = MatuleTheme.typography.bodyRegular16.copy(MatuleTheme.colors.text),
                textAlign = TextAlign.Right
            )
        }

        val interaction = remember { MutableInteractionSource() }
        BasicTextField(
            value = value,
            onValueChange = { onChangeValue(it) },
            modifier = Modifier
                .fillMaxWidth()
                .background(MatuleTheme.colors.background)
                .clip(RoundedCornerShape(12.dp)),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisual.value) VisualTransformation.None else PasswordVisualTransformation()
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                singleLine = true,
                innerTextField = innerTextField,
                enabled = true,
                visualTransformation = if (passwordVisual.value) VisualTransformation.None else PasswordVisualTransformation(),
                interactionSource = interaction,
                trailingIcon = {
                    IconButton(onClick = { passwordVisual.value = !passwordVisual.value }) {
                        Icon(
                            painter = painterResource(if (passwordVisual.value) R.drawable.eye_close else R.drawable.eye_open),
                            contentDescription = null
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    disabledContainerColor = MatuleTheme.colors.background,
                    errorContainerColor = MatuleTheme.colors.background,
                    focusedContainerColor = MatuleTheme.colors.background,
                    unfocusedContainerColor = MatuleTheme.colors.background,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
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
        Text(
            text = "Даю согласие на обработку персональных данных",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}



