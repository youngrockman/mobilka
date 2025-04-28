package com.example.shoesapptest.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopPanel(title: String,
             leftImage: Painter,
             rightImage: Painter,
             textSize: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(top = 35.dp, start = 16.dp)
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterStart)
                .padding(end = 16.dp)
        ) {
            Icon(
                painter = leftImage,
                contentDescription = "Назад"
            )
        }

        Text(text = title,
            fontWeight = FontWeight.Medium,
            fontSize = textSize.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            onClick = {},
            modifier = Modifier
                .size(44.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        ) {
            Image(
                painter = rightImage,
                contentDescription = "heart"
            )
        }

    }
}