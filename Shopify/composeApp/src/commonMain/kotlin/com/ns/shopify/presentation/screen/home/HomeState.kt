package com.ns.shopify.presentation.screen.home

import com.ns.shopify.GetCollectionsQuery

/**
 * Created by Ashwani Kumar Singh on 07,December,2023.
 */
data class HomeState(
    var success: List<GetCollectionsQuery.Node> = emptyList(),
    var isLoading: Boolean = false,
    var error: String = "",
    var isLoaded: Boolean = false
)
