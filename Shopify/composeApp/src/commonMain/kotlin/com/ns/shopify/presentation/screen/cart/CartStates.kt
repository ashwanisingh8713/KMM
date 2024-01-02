package com.ns.shopify.presentation.screen.cart

import androidx.compose.runtime.Stable
import com.ns.shopify.CartCreateMutation
import com.ns.shopify.CartLinesAddMutation

/**
 * Created by Ashwani Kumar Singh on 28,December,2023.
 */

@Stable
data class CreateCartState(val isLoading: Boolean = true,
                           val success: CartCreateMutation.Cart? = null,
                           val isLoaded : Boolean = false,
                           val error: String? = null)
@Stable
data class AddMerchandiseState(val isLoading: Boolean = true,
                               val success: CartLinesAddMutation.Cart? = null,
                               val isLoaded : Boolean = false,
                               val error: String? = null)
@Stable
data class CartCountState(val isLoading: Boolean = true, val count: Int = 0, val error: String? = null)