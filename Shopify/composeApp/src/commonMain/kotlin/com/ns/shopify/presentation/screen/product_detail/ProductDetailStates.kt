package com.ns.shopify.presentation.screen.product_detail

import com.ns.shopify.GetCollectionsQuery

/**
 * Created by Ashwani Kumar Singh on 08,December,2023.
 */
data class ProductDetailStates(
    val success: ProductModel? = ProductModel(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoaded: Boolean = false)
