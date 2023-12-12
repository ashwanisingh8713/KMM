package com.ns.shopify.domain.usecase.login

import com.ns.shopify.LoginMutation
import com.ns.shopify.domain.repo.login.ILoginModuleRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import com.ns.shopify.type.CustomerAccessTokenCreateInput
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
class AccessTokenCreateUseCase(private val repo: ILoginModuleRepo, dispatcher: CoroutineDispatcher):
    BaseUseCase<CustomerAccessTokenCreateInput, LoginMutation.CustomerAccessTokenCreate>(dispatcher) {
    override suspend fun block(input: CustomerAccessTokenCreateInput): LoginMutation.CustomerAccessTokenCreate {
        return repo.login(input)
    }
}