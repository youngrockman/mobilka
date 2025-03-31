package com.example.shoesapptest.screen.signin.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.common.CommonButton


@Composable
fun AuthButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    CommonButton(
        onClick = onClick,
        modifier = modifier.padding(50.dp),
        enabled = enabled,
        buttonColors = ButtonColors(
            contentColor = MatuleTheme.colors.background,
            containerColor = MatuleTheme.colors.accent,
            disabledContainerColor = MatuleTheme.colors.accent,
            disabledContentColor = MatuleTheme.colors.accent
        )
    ) {
        content()
    }
}