package com.example.shoesapptest.screen.popular

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoesapptest.R
import com.example.shoesapptest.screen.home.component.ProductItem
import com.example.shoesapptest.screen.popular.component.PopularItemData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Популярное",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White, shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_arrow),
                            contentDescription = "Назад",
                            tint = Color.Black,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {  },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White, shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.heart),
                            contentDescription = "Избранное",
                            tint = Color.Black,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        PopularContent(
            modifier = Modifier.padding(paddingValues)
        )
    }
}


@Composable
fun PopularContent(modifier: Modifier = Modifier) {
    val popularItems = List(5) {
        PopularItemData(
            title = "BEST SELLER",
            name = "Nike Air Max",
            price = "₽732.00",
            imageRes = R.drawable.mainsneakers
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(popularItems) { item ->
            ProductItem(
                title = item.title,
                name = item.name,
                price = item.price,
                imageRes = painterResource(item.imageRes),
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.85f)
            )
        }
    }
}