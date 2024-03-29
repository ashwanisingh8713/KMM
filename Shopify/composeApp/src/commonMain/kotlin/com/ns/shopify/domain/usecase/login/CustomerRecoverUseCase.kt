package com.ns.shopify.domain.usecase.login

import com.ns.shopify.RecoverCustomerAccountMutation
import com.ns.shopify.domain.model.SampleModel
import com.ns.shopify.domain.repo.login.ILoginModuleRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
class CustomerRecoverUseCase(private val repo: ILoginModuleRepo, dispatcher: CoroutineDispatcher):
    BaseUseCase<String, RecoverCustomerAccountMutation.CustomerRecover>(dispatcher) {
    override suspend fun block(email: String): RecoverCustomerAccountMutation.CustomerRecover {
        return repo.forgotPassword(email)
    }
}