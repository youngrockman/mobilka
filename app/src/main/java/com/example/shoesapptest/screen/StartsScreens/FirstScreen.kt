package com.example.shoesapptest.screen.StartsScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoesapptest.R
import kotlinx.coroutines.delay

@Composable
fun FirstScreen(OnnavifateToSlideScreen: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000)
        OnnavifateToSlideScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.horizontalGradient(listOf(Color(0xFF48B2E7), Color(0xFF0076B1))))
    ) {
        Text(
            text = "Matule",
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun SlideScreen(onNavigateToAuthScreen: () -> Unit) {
    var sliderValue by remember { mutableStateOf(0f) }
    val textList = listOf("ДОБРО ПОЖАЛОВАТЬ", "Начнем путешествие", "У вас есть сила, чтобы")
    val textListSecond = listOf("", "Умная, великолепная и модная коллекция Изучите сейчас", "В вашей комнате много красивых и привлекательных растений")
    val maxIndex = textList.size - 1

    val currentIndex = sliderValue.toInt()

    val imageId = when (currentIndex) {
         0 -> R.drawable.sneakers1
         1 -> R.drawable.sneakers2
         2 -> R.drawable.sneakers3
        else -> return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.horizontalGradient(listOf(Color(0xFF48B2E7), Color(0xFF0076B1)))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Рисунок",
            modifier = Modifier
                .width(670.dp)
                .height(600.dp)
                .padding(start = 20.dp)
        )

        Text(
            color = Color.White,
            fontStyle = FontStyle.Italic,
            text = textList[currentIndex],
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        Text(
            color = Color.White,
            fontStyle = FontStyle.Italic,
            text = textListSecond[currentIndex],
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        )

        Spacer(modifier = Modifier.weight(1f))


        Slider(
            value = sliderValue,
            onValueChange = {newValue -> sliderValue = newValue},
            valueRange = 0f..maxIndex.toFloat(),
            steps = maxIndex - 1,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color(0xFF0076B1),
                inactiveTrackColor = Color(0xFFB0B0B0)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (sliderValue < maxIndex) {
                    sliderValue++
                } else {
                    onNavigateToAuthScreen()
                }},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
            ) {
            Text(text = "Далее", color = Color.Black)
        }

    }
}
