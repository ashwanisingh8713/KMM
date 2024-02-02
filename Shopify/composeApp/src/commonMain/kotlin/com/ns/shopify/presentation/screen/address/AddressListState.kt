package com.ns.shopify.presentation.screen.address

import androidx.compose.runtime.Stable
import com.ns.shopify.GetAddressQuery

/**
 * Created by Ashwani Kumar Singh on 01,February,2024.
 */

@Stable
data class AddressListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoaded: Boolean = false,
    val addresses: List<GetAddressQuery.Node> = emptyList()
)
