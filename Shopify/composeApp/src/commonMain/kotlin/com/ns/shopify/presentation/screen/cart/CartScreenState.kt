package com.ns.shopify.presentation.screen.cart

import androidx.compose.runtime.Immutable
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

@Immutable
data class CartScreenStateMapper(
    val success: List<UserCartUiData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoaded: Boolean = false
)


data class UserCartUiData(
    val productId: String = "",
    val price: String = "",
    val quantity: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
    val lineId: String = ""
)
