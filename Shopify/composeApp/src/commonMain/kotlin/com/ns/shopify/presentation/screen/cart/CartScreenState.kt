package com.ns.shopify.presentation.screen.cart

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ns.shopify.CartBuyerIdentityUpdateMutation
import com.ns.shopify.CartCreateMutation
import com.ns.shopify.CartLinesAddMutation
import com.ns.shopify.type.CurrencyCode

/**
 * Created by Ashwani Kumar Singh on 26,December,2023.
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

@Immutable
data class CartScreenStateMapper(
    val success: List<UserCartUiData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoaded: Boolean = false,
    val totalAmount: Double = 0.0,
    val subTotalAmount: Double = 0.0,
    val taxAmount: Double = 0.0,
    val currencyCode: CurrencyCode = CurrencyCode.INR
)


data class UserCartUiData(
    val productId: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
    val lineId: String = "",
    val currencyCode: CurrencyCode = CurrencyCode.INR
)



data class CartBuyerIdentityUpdateState(val isLoading: Boolean = true,
                                        val success: CartBuyerIdentityUpdateMutation.Data? = null,
                                        val isLoaded : Boolean = false,
                                        val error: String = "")
