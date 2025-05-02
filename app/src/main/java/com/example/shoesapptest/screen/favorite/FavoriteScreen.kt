package com.example.shoesapptest.screen.favorite

import ProductItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoesapptest.R
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.screen.home.component.BottomBar
import com.example.shoesapptest.screen.home.PopularSneakersViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: PopularSneakersViewModel = koinViewModel()
) {
    val favoritesState by viewModel.favoritesState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchFavorites()
    }

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
        when (favoritesState) {
            is NetworkResponseSneakers.Success -> {
                val favorites = (favoritesState as NetworkResponseSneakers.Success<List<PopularSneakersResponse>>).data
                FavoriteContent(
                    modifier = Modifier.padding(paddingValues),
                    favorites = favorites,
                    onItemClick = { id ->
                    },
                    onFavoriteClick = { id, isFavorite ->
                        viewModel.toggleFavorite(id, isFavorite)
                    },
                    navController = navController
                )
            }
            is NetworkResponseSneakers.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Ошибка загрузки",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            NetworkResponseSneakers.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Загрузка...",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    favorites: List<PopularSneakersResponse>,
    onItemClick: (Int) -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = favorites,
            key = { it.id }
        ) { sneaker ->
            ProductItem(
                sneaker = sneaker,
                onItemClick = { onItemClick(sneaker.id) },
                onFavoriteClick = { _, isFavorite ->
                    onFavoriteClick(sneaker.id, isFavorite)
                },
                onAddToCart = { /* ... */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.85f)
            )
        }
    }
}