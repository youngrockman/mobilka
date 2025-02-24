package com.example.shoesapptest.common

import android.view.ViewAnimationUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shoesapp.ui.theme.MatuleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    value: String,
    onChangeValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable ()-> Unit = {},
    supportingText: @Composable ()-> Unit = {},
    placeHolder: @Composable ()-> Unit = {},
){

    val interaction = remember { MutableInteractionSource() }
    BasicTextField(
        value = value,
        onValueChange = { onChangeValue(it) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MatuleTheme.colors.background)
    ){ innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value,
            singleLine = true,
            innerTextField = innerTextField,
            enabled = true,
            visualTransformation = visualTransformation,
            interactionSource = interaction,
            trailingIcon = trailingIcon,
            isError = isError,
            supportingText = if (isError) supportingText else null,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MatuleTheme.colors.background,
                disabledContainerColor = MatuleTheme.colors.background,
                unfocusedContainerColor = MatuleTheme.colors.background,
                errorContainerColor = MatuleTheme.colors.background,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red
            ),
            placeholder = placeHolder
        )
    }
}