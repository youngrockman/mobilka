import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shoesapptest.R
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapp.ui.theme.MatuleTheme

fun getDrawableId(imageName: String): Int {
    return when (imageName) {
        "mainsneakers" -> R.drawable.mainsneakers
        "sneakers2" -> R.drawable.sneakers2
        else -> R.drawable.mainsneakers
    }
}


@Composable
fun ProductItem(
    sneaker: PopularSneakersResponse,
    onItemClick: () -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(240.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(onClick = onItemClick),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                painter = painterResource(id = getDrawableId(sneaker.imageUrl)),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = {
                    onFavoriteClick(sneaker.id, sneaker.isFavorite)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(
                        if (sneaker.isFavorite) R.drawable.red_heart
                        else R.drawable.heart
                    ),
                    contentDescription = "Избранное",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        ) {
            Text(
                text = sneaker.category.uppercase(),
                color = MatuleTheme.colors.accent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = sneaker.name,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 16.sp
                ),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp),
                maxLines = 2
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₽${"%.2f".format(sneaker.price)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                IconButton(
                    onClick = onAddToCart,
                    modifier = Modifier.size(36.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.add_plus),
                        contentDescription = "Add to cart",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
