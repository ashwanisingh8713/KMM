package com.ns.shopify.domain.repo.login

import com.ns.shopify.domain.model.SampleModel

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
interface ILoginModuleRepo {

    // For Signup
    suspend fun signUP(any: Any): SampleModel

    // For Login
    suspend fun login(any: Any): SampleModel

    // For Forgot Password
    suspend fun forgotPassword(email: String): SampleModel
}