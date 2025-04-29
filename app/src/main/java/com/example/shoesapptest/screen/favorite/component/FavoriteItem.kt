package com.example.shoesapptest.screen.favorite.component

data class FavoriteItem(
    val id: Int,
    val title: String,
    val name: String,
    val price: String,
    val imageRes: Int,
    val isFavorite: Boolean
)