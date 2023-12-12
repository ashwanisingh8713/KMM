package com.ns.shopify.domain.repo.login

import com.ns.shopify.CreateCustomerAccountMutation
import com.ns.shopify.LoginMutation
import com.ns.shopify.RecoverCustomerAccountMutation
import com.ns.shopify.type.CustomerAccessTokenCreateInput
import com.ns.shopify.type.CustomerCreateInput

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
interface ILoginModuleRepo {
    // For Signup
    suspend fun signUP(input: CustomerCreateInput):  CreateCustomerAccountMutation.CustomerCreate

    // For Login
    suspend fun login(input: CustomerAccessTokenCreateInput): LoginMutation.CustomerAccessTokenCreate

    // For Forgot Password
    suspend fun forgotPassword(email: String): RecoverCustomerAccountMutation.CustomerRecover
}