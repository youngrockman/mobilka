package com.example.shoesapptest.screen.regscreen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.common.CommonButton

@Composable
fun RegisterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit)
{
    CommonButton(
        modifier = Modifier.padding(50.dp),
        onClick = onClick,
        buttonColors = ButtonColors(
            containerColor = MatuleTheme.colors.accent,
            contentColor = MatuleTheme.colors.background,
            disabledContainerColor = MatuleTheme.colors.accent,
            disabledContentColor = MatuleTheme.colors.accent
        )
    ) { content()}
}