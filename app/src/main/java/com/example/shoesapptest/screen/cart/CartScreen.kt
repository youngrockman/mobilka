package com.example.shoesapptest.screen.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.R
import com.example.shoesapptest.Screen
import com.example.shoesapptest.data.remote.network.NetworkResponse
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.dto.CartTotal
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.screen.home.component.TopPanel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel
) {
    val cartState by viewModel.cartState.collectAsState()
    val totalState by viewModel.totalState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCart()
    }

    Scaffold(
        topBar = {
            TopPanel(
                title = "Корзина",
                leftImage = painterResource(R.drawable.back_arrow),
                rightImage = null,
                textSize = 24,
                onLeftClick = { navController.navigate(Screen.Home.route)

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            when (cartState) {
                is NetworkResponseSneakers.Success -> {
                    val cartItems = (cartState as NetworkResponseSneakers.Success<List<PopularSneakersResponse>>).data

                    if (cartItems.isEmpty()) {
                        Text(
                            text = "Корзина пуста",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        return@Column
                    }

                    Text(
                        text = "${cartItems.size} товара",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(cartItems) { sneaker ->
                            CartItem(
                                sneaker = sneaker,
                                onRemoveClick = { viewModel.removeFromCart(sneaker.id) },
                                onIncrement = {
                                    val newQty = (sneaker.quantity ?: 1) + 1
                                    viewModel.updateQuantity(sneaker.id, newQty)
                                },
                                onDecrement = {
                                    val currentQty = sneaker.quantity ?: 1
                                    if (currentQty > 1) {
                                        viewModel.updateQuantity(sneaker.id, currentQty - 1)
                                    } else {
                                        viewModel.removeFromCart(sneaker.id)
                                    }
                                }
                            )
                            Divider()
                        }
                    }

                    when (totalState) {
                        is NetworkResponse.Success -> {
                            val total = (totalState as NetworkResponse.Success<CartTotal>).data
                            Column(
                                modifier = Modifier.padding(vertical = 16.dp)
                            ) {
                                PriceRow("Сумма", total.total)
                                PriceRow("Доставка", total.delivery)
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                PriceRow("Итого", total.finalTotal, isBold = true)
                            }
                        }
                        else -> Unit
                    }

                    Button(
                        onClick = { /* Оформление заказа */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MatuleTheme.colors.accent
                        )
                    ) {
                        Text(
                            text = "Оформить заказ",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                is NetworkResponseSneakers.Error -> {
                    Text(
                        text = "Ошибка загрузки корзины",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                NetworkResponseSneakers.Loading -> {
                    Text(
                        text = "Загрузка...",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
fun CartItem(
    sneaker: PopularSneakersResponse,
    onRemoveClick: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = sneaker.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                placeholder = painterResource(R.drawable.mainsneakers),
                error = painterResource(R.drawable.mainsneakers),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = sneaker.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "P${"%.2f".format(sneaker.price)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MatuleTheme.colors.accent
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDecrement) {
                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "Уменьшить",
                        tint = Color.Black
                    )
                }

                Text(
                    text = "${sneaker.quantity ?: 1}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                IconButton(onClick = onIncrement) {
                    Icon(
                        painter = painterResource(R.drawable.plus),
                        contentDescription = "Увеличить",
                        tint = Color.Black
                    )
                }

                IconButton(onClick = onRemoveClick) {
                    Icon(
                        painter = painterResource(R.drawable.trash),
                        contentDescription = "Удалить",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun PriceRow(label: String, value: Double, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = "P${"%.2f".format(value)}",
            fontSize = 16.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = if (isBold) MatuleTheme.colors.accent else Color.Black
        )
    }
}
