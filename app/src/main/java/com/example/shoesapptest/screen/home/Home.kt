


package com.example.pypypy.ui.screen.home

import androidx.compose.material3.IconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.R
import com.example.shoesapptest.screen.home.component.BottomBar
import com.example.shoesapptest.screen.home.component.TopPanel
import com.example.shoesapptest.screen.home.popular.PopularSneakersViewModel


@Composable
fun HomeScreenHast() {
    val sneakersViewModel: PopularSneakersViewModel = koinViewModel<PopularSneakersViewModel>()

    Scaffold(
        topBar = {
            TopPanel(title = "Главная",
                leftImage = painterResource(R.drawable.menu),
                rightImage = painterResource(R.drawable.black_shop),
                textSize = 32
            )
        },

        bottomBar = { BottomBar() }
    ) {
            paddingValues ->
        HomeScreenContent(paddingValues = paddingValues, sneakersViewModel);
    }
}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    viewModel: PopularSneakersViewModel
) {
    val message = remember{mutableStateOf("")}

    Row(
        modifier = Modifier.padding(start = 22.dp, top = 100.dp, end = 22.dp)
    ) {
        TextField(
            value = message.value,
            onValueChange = {newText -> message.value = newText},
            placeholder = { Text("Поиск") },
            leadingIcon = {Icon(painterResource(R.drawable.check), contentDescription = "Поиск")},
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MatuleTheme.colors.block,
                disabledContainerColor = MatuleTheme.colors.background,
                unfocusedContainerColor = MatuleTheme.colors.background,
                errorContainerColor = MatuleTheme.colors.background,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red
            )
        )
        IconButton(onClick = {  }) {
            Image(painterResource(R.drawable.sort), "Сортировка", Modifier.size(72.dp))
        }
    }
}