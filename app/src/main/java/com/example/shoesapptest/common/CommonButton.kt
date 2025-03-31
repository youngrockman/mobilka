package com.example.shoesapptest.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shoesapp.ui.theme.MatuleTheme

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonColors: ButtonColors,
    enabled: Boolean = true,
    content: @Composable () -> Unit)
{
    Button(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MatuleTheme.colors.accent),
        colors = buttonColors,
        onClick = onClick
    ) {
        content()
    }
}