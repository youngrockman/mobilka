package com.example.shoesapp.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.R


@Composable
fun SigninScreen(){
    SignInContent()
}



@Composable
fun SignInContent() {
    Column(){
        TitleWithSubtitleText(
            title = stringResource(R.string.hello),
            subText = stringResource(R.string.sign_in_subtitle)
        )

        AuthTextField()
    }
}


@Composable
fun TitleWithSubtitleText(title: String, subText: String){
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        Text(
            text = title,
            style = MatuleTheme.typography.headingBold32.copy(color = MatuleTheme.colors.text),
            textAlign = TextAlign.Center,
            color = Color.Black
            )
        Text(
            text = subText,
            maxLines = 2,
            style = MatuleTheme.typography.subTitleRegular16.copy(color = MatuleTheme.colors.subTextDark),
            textAlign = TextAlign.Center,
            color = Color.Black)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AuthTextField(){
    val value = remember { mutableStateOf("") }
    val interaction = remember { MutableInteractionSource() }
    BasicTextField(
        value = value.value,
        onValueChange = {
            value.value = it
        },
        modifier = Modifier
            .padding( horizontal = 20.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .clip(RoundedCornerShape(14.dp))
    ){
        innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value.value,
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
                Text(text = "xyz@gmail.com",
                    style = MatuleTheme.typography.bodyRegular14.copy(color = MatuleTheme.colors.hint),
                    color = Color.White)
            }
        )
    }
}