package com.example.shoesapptest.screen.forgotpassword.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.R

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