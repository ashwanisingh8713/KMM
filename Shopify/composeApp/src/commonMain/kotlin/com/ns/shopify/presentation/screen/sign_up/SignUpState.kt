package com.ns.shopify.presentation.screen.sign_up

import com.ns.shopify.CreateCustomerAccountMutation

/**
 * Created by Ashwani Kumar Singh on 11,December,2023.
 */
data class SignUpState(
    var success: CreateCustomerAccountMutation.CustomerCreate? = null,
    var isLoading: Boolean = false,
    var error: String? = null,
    var isLoaded: Boolean = false
)
