package com.example.shoesapptest.screen.search

import ProductItem
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.R
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.screen.home.component.TopPanel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SneakersViewModel
) {
    val query by viewModel.query.collectAsState()
    val history by viewModel.history.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopPanel(
                title = "Поиск",
                leftImage = painterResource(R.drawable.back_arrow),
                rightImage = null,
                textSize = 24,
                onLeftClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            SearchInput(
                query = query,
                onQueryChange = viewModel::onQueryChanged
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (query.isEmpty()) {
                SearchHistory(
                    history = history,
                    onItemClick = { item ->
                        coroutineScope.launch {
                            viewModel.onItemClicked(item)
                        }
                    }
                )
            } else {
                when (searchResults) {
                    is NetworkResponseSneakers.Success -> {
                        val sneakers = (searchResults as NetworkResponseSneakers.Success<List<PopularSneakersResponse>>).data
                        if (sneakers.isEmpty()) {
                            EmptyResults()
                        } else {
                            SearchResultsGrid(
                                sneakers = sneakers,
                                onAddToCart = viewModel::addToCart,
                                onAddToFavorite = { viewModel.addToFavorite(it) },
                                onRemoveFromFavorite = { viewModel.removeFromFavorite(it) },
                                navController = navController
                            )

                        }
                    }
                    is NetworkResponseSneakers.Error -> {
                        ErrorMessage((searchResults as NetworkResponseSneakers.Error).errorMessage)
                    }
                    NetworkResponseSneakers.Loading -> {
                        LoadingIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultsGrid(
    sneakers: List<PopularSneakersResponse>,
    onAddToCart: (Int) -> Unit,
    onAddToFavorite: (Int) -> Unit,
    onRemoveFromFavorite: (Int) -> Unit,
    navController: NavController
) {
    var sneakersState by remember { mutableStateOf(sneakers) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sneakersState, key = { it.id }) { sneaker ->
            ProductItem(
                sneaker = sneaker,
                onItemClick = { navController.navigate("details/${sneaker.id}") },
                onFavoriteClick = { id, isFavorite ->
                    sneakersState = sneakersState.map {
                        if (it.id == id) it.copy(isFavorite = !isFavorite) else it
                    }
                    if (isFavorite) {
                        onRemoveFromFavorite(id)
                    } else {
                        onAddToFavorite(id)
                    }
                },
                onAddToCart = { onAddToCart(sneaker.id) },
                modifier = Modifier.aspectRatio(0.85f)
            )
        }
    }
}





@Composable
private fun SearchInput(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Поиск") },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.lupa),
                contentDescription = "Поиск",
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        painter = painterResource(R.drawable.microphone),
                        contentDescription = "Очистить",
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                IconButton(onClick = { /* Голосовой поиск */ }) {
                    Icon(
                        painter = painterResource(R.drawable.microphone),
                        contentDescription = "Голосовой поиск",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun SearchHistory(
    history: List<String>,
    onItemClick: (String) -> Unit
) {
    Column {
        Text(
            text = "История поиска",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (history.isEmpty()) {
            Text(
                text = "История поиска пуста",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            LazyColumn {
                items(history) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(item) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.time_table),
                            contentDescription = "История",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = item, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyResults() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Ничего не найдено", fontSize = 16.sp, color = Color.Gray)
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Ошибка: $message", fontSize = 16.sp, color = Color.Red)
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
