package com.ns.shopify.presentation.screen.address

import com.ns.shopify.RecoverCustomerAccountMutation

/**
 * Created by Ashwani Kumar Singh on 11,December,2023.
 */
data class AddressState(
    var success: RecoverCustomerAccountMutation.CustomerRecover? = null,
    var isLoading: Boolean = false,
    var error: String = "",
    var isLoaded: Boolean = false
)
