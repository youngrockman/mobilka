package com.example.shoesapptest.screen.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoesapptest.R
import com.example.shoesapptest.screen.favorite.component.FavoriteItem
import com.example.shoesapptest.screen.home.component.BottomBar
import com.example.shoesapptest.screen.home.component.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Избранное",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = "Назад",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(64.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.red_heart),
                            contentDescription = "Домой",
                            modifier = Modifier.size(48.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        FavoriteContent(
            modifier = Modifier.padding(paddingValues),
            navController = navController
        )
    }
}


@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val favoriteItems = remember { mutableStateListOf<FavoriteItem>().apply {
        addAll(List(5) {
            FavoriteItem(
                id = it,
                title = "BEST SELLER",
                name = "Nike Air Max",
                price = "₽732.00",
                imageRes = R.drawable.mainsneakers,
                isFavorite = true
            )
        })
    }}

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(favoriteItems) { item ->
            ProductItem(
                title = item.title,
                name = item.name,
                price = item.price,
                imageRes = painterResource(item.imageRes),
                onClick = { /* Обработка клика на товар */ },
                isFavorite = item.isFavorite,
                onFavoriteClick = {
                    favoriteItems.removeAll { it.id == item.id }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.85f)
            )
        }
    }
}
