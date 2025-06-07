package com.example.shoesapptest.screen.search


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse


@Composable
fun SearchMainScreen(
    navController: NavController,
    searchResults: List<PopularSneakersResponse>,
    onAddToCart: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        items(searchResults) { sneaker ->
            SearchResultItem(sneaker = sneaker, onAddToCart = onAddToCart)
            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@Composable
fun SearchResultItem(
    sneaker: PopularSneakersResponse,
    onAddToCart: (Int) -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sneaker.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Column {
                    Text(
                        text = "BEST SELLER",
                        color = MatuleTheme.colors.accent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = sneaker.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "P${"%.2f".format(sneaker.price)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MatuleTheme.colors.accent
                    )
                }
            }

            AsyncImage(
                model = sneaker.imageUrl,
                contentDescription = sneaker.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.mainsneakers),
                error = painterResource(R.drawable.mainsneakers)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onAddToCart(sneaker.id) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MatuleTheme.colors.accent
            )
        ) {
            Text(text = "+", fontSize = 18.sp)
        }
    }
}