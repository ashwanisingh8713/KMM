package com.ns.shopify.presentation.screen.sign_in

import com.ns.shopify.LoginMutation

/**
 * Created by Ashwani Kumar Singh on 11,December,2023.
 */
data class SignInState(
    var success: LoginMutation.CustomerAccessTokenCreate? = null,
    var isLoading: Boolean = false,
    var error: String = "",
    var isLoaded: Boolean = false
)
