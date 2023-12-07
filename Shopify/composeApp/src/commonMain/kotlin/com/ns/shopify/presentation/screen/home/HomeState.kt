package com.ns.shopify.presentation.screen.home

import com.ns.shopify.GetCollectionsQuery

/**
 * Created by Ashwani Kumar Singh on 07,December,2023.
 */
data class HomeState(
    val success: List<GetCollectionsQuery.Node> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoaded: Boolean = false
)
