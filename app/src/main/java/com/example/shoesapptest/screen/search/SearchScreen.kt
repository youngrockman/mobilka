package com.example.shoesapptest.screen.search

import ProductItem
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.shoesapptest.screen.home.component.TopPanel

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SneakersViewModel
) {
    val query by viewModel.query.collectAsState()
    val history by viewModel.history.collectAsState()
    val sneakersResponse by viewModel.searchResults.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SearchInput(query = query, onQueryChange = viewModel::onQueryChanged)

        Spacer(modifier = Modifier.height(16.dp))

        if (query.isEmpty()) {
            HistorySection(history, onClick = viewModel::onItemClicked)
        } else {
            when (sneakersResponse) {
                is NetworkResponseSneakers.Success -> {
                    val sneakers = (sneakersResponse as NetworkResponseSneakers.Success<List<PopularSneakersResponse>>)
                        .data.filter { it.name.contains(query, ignoreCase = true) }

                    if (sneakers.isEmpty()) {
                        EmptyResults()
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(sneakers.size) { index ->
                                val sneaker = sneakers[index]
                                ProductItem(
                                    sneaker = sneaker,
                                    onItemClick = { /* TODO: Навигация */ },
                                    onFavoriteClick = { _, _ -> },
                                    onAddToCart = { viewModel.addToCart(sneaker.id) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(0.85f)
                                )
                            }
                        }
                    }
                }

                is NetworkResponseSneakers.Error -> {
                    ErrorMessage((sneakersResponse as NetworkResponseSneakers.Error).errorMessage)
                }

                NetworkResponseSneakers.Loading -> {
                    LoadingIndicator()
                }
            }
        }
    }
}

@Composable
fun SearchInput(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Поиск") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun HistorySection(history: List<String>, onClick: (String) -> Unit) {
    Column {
        Text("История поиска", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        history.forEach { item ->
            Text(
                text = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onClick(item) }
            )
        }
    }
}

@Composable
fun EmptyResults() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Ничего не найдено", style = MaterialTheme.typography.bodyMedium)
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

@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Ошибка: $message", color = Color.Red)
    }
}