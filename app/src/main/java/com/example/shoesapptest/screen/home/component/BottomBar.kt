package com.example.shoesapptest.screen.home.component

import android.widget.ImageButton
import androidx.compose.material3.IconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
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
            painter = painterResource(R.drawable.vector_1789),
            contentDescription = "нижняя панель",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 38.dp)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.Top
        ) {
            Row(
                modifier = Modifier.weight(1f, fill = false).padding(end = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.Start)
            ) {
                IconButton(onClick = {  }) {
                    Image(painterResource(R.drawable.home), "Дом", Modifier.size(24.dp))
                }
                IconButton(onClick = {  }) {
                    Image(painterResource(R.drawable.heart), "Избранное", Modifier.size(24.dp))
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                IconButton(
                    onClick = { /* ... */ },
                    modifier = Modifier.size(32.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.shop),
                        contentDescription = "Корзина",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.weight(1f, fill = false).padding(start = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.End)
            ) {
                IconButton(onClick = {  }) {
                    Image(painterResource(R.drawable.colocol), "Уведомления", Modifier.size(24.dp))
                }
                IconButton(onClick = {  }) {
                    Image(painterResource(R.drawable.people), "Профиль", Modifier.size(24.dp))
                }
            }
        }
    }
}