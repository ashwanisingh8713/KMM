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
