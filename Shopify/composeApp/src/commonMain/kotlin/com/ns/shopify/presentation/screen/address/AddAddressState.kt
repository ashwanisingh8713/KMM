package com.ns.shopify.presentation.screen.address

import com.ns.shopify.CustomerAddressCreateMutation

/**
 * Created by Ashwani Kumar Singh on 11,December,2023.
 */
data class AddAddressState(
    var success: CustomerAddressCreateMutation.CustomerAddressCreate? = null,
    var isLoading: Boolean = false,
    var error: String = "",
    var isLoaded: Boolean = false
)
