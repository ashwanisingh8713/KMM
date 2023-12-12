package com.ns.shopify.presentation.screen.product_detail

import androidx.compose.ui.graphics.Color

/*data class ProductModel(
    val id: Int,
    val title: String,
    val images: List<String>,
    val colors: List<Color>,
    val rating: Double,
    val price: Double,
    val isFavourite: Boolean,
    val isPopular: Boolean,
    val description: String
)*/

data class ProductModel(
    val id: Int = 0,
    val title: String = "Product Title",
    val images: List<String> = listOf("https://th-i.thgim.com/public/incoming/gk4zzs/article67617232.ece/alternates/LANDSCAPE_1200/11908_10_8_2023_12_21_27_2_EMM_3412.JPG", "https://th-i.thgim.com/public/sci-tech/science/e9872k/article67606416.ece/alternates/LANDSCAPE_1200/GPS-IIR.jpg"),
    val colors: List<Color> = listOf(Color.Red, Color.Blue, Color.Green),
    val rating: Double = 4.5,
    val price: Double = 100.0,
    val isFavourite: Boolean = false,
    val isPopular: Boolean = false,
    val description: String = "Product Description"
)