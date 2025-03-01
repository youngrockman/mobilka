package com.example.shoesapptest.screen.forgotpassword.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.common.CommonButton

@Composable
fun SendButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit) {
    CommonButton(
        onClick = onClick,
        modifier = modifier.padding(50.dp),
        buttonColors = ButtonColors(
            containerColor = MatuleTheme.colors.accent,
            contentColor = MatuleTheme.colors.background,
            disabledContainerColor = MatuleTheme.colors.accent,
            disabledContentColor = MatuleTheme.colors.accent
        ),
    ) {content()}
}