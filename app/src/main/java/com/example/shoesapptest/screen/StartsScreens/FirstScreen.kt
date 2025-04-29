package com.example.shoesapptest.screen.StartsScreens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoesapptest.R
import com.example.shoesapptest.data.local.DataStoreOnBoarding
import com.example.shoesapptest.data.local.OnboardingPage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlideScreen(
    onNavigateToAuthScreen: () -> Unit,
    dataStore: DataStoreOnBoarding
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(
            title = "ДОБРО ПОЖАЛОВАТЬ",
            description = "Умная, великолепная и модная коллекция\nИзучите сейчас",
            image = R.drawable.sneakers1
        ),
        OnboardingPage(
            title = "Начнем путешествие",
            description = "В вашей комнате много красивых\nи привлекательных растений",
            image = R.drawable.sneakers2
        ),
        OnboardingPage(
            title = "У Вас Есть Сила,\nЧтобы",
            description = "Создать свой уникальный стиль",
            image = R.drawable.sneakers3
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Color(0xFF48B2E7), Color(0xFF0076B1)))
            )
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp)
                ) {
                    Image(
                        painter = painterResource(id = pages[page].image),
                        contentDescription = null,
                        contentScale = ContentScale.Fit, // Вмещает изображение в контейнер
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(400.dp)
                            .padding(bottom = 40.dp)
                    )

                    Text(
                        text = pages[page].title,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Text(
                        text = pages[page].description,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(top = 24.dp)
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pages.size) { index ->
                val isSelected = pagerState.currentPage == index

                val width by animateDpAsState(
                    targetValue = if (isSelected) 40.dp else 16.dp,
                    label = "indicator_width"
                )

                val color by animateColorAsState(
                    targetValue = if (isSelected) Color.White else Color.White.copy(alpha = 0.4f),
                    label = "indicator_color"
                )

                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .width(width)
                        .background(
                            color = color,
                            shape = RoundedCornerShape(50)
                        )
                )

                if (index != pages.lastIndex) {
                    Spacer(modifier = Modifier.width(8.dp)) // Расстояние между линиями
                }
            }
        }



        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < pages.lastIndex) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        dataStore.setOnboardingCompleted(true)
                        onNavigateToAuthScreen()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(60.dp)
        ) {
            Text(
                text = when (pagerState.currentPage) {
                    0 -> "Начать"
                    1 -> "Далее"
                    2 -> "Далее"
                    else -> ""
                },
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}