package com.ns.shopify.presentation.screen.cart

import androidx.compose.runtime.Stable
import com.ns.shopify.CartCreateMutation

/**
 * Created by Ashwani Kumar Singh on 26,December,2023.
 */

@Stable
data class CartScreenState(
    val success: CartCreateMutation.CartCreate? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoaded: Boolean = false
)

data class UserCartUiData(
    val userId: String = "",
    val productId: Int = 0,
    val price: Int = 0,
    val quantity: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
)
