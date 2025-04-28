package com.example.shoesapptest.screen.home.component

import androidx.compose.material3.IconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoesapptest.R


@Composable
fun BottomBar() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(R.drawable.idk),
            contentDescription = "нижняя панель",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            // Левая часть
            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                IconButton(onClick = {  }) {
                    Image(
                        painter = painterResource(R.drawable.home),
                        "Дом",
                        Modifier.size(28.dp))
                }
                IconButton(onClick = {  }) {
                    Image(
                        painter = painterResource(R.drawable.heart),
                        "Избранное",
                        Modifier.size(28.dp))
                }
            }

            // Центральная кнопка (да я знаю где центр)
            IconButton(
                onClick = { /* ... */ },
                modifier = Modifier
                    .size(96.dp)
                    .offset(y = (-32).dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.shop),
                    contentDescription = "Корзина",
                    modifier = Modifier.size(88.dp)
                )
            }

            // Правая часть
            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                IconButton(onClick = {  }) {
                    Image(
                        painter = painterResource(R.drawable.bell),
                        "Уведомления",
                        Modifier.size(28.dp))
                }
                IconButton(onClick = {  }) {
                    Image(
                        painter = painterResource(R.drawable.man),
                        "Профиль",
                        Modifier.size(28.dp))
                }
            }
        }
    }
}