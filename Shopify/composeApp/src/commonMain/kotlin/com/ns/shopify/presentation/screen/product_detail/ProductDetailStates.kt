package com.ns.shopify.presentation.screen.product_detail

import androidx.compose.runtime.Stable
import com.ns.shopify.ProductDetailQuery


/**
 * Created by Ashwani Kumar Singh on 08,December,2023.
 */

@Stable
data class ProductDetailStates(
    val success: ProductDetailQuery.Product? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoaded: Boolean = false)
