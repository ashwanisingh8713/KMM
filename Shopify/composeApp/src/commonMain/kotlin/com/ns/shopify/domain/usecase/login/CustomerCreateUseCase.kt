package com.ns.shopify.domain.usecase.login

import com.ns.shopify.CreateCustomerAccountMutation
import com.ns.shopify.domain.repo.login.ILoginModuleRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import com.ns.shopify.type.CustomerCreateInput
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
class CustomerCreateUseCase(private val repo: ILoginModuleRepo, dispatcher: CoroutineDispatcher):
    BaseUseCase<CustomerCreateInput, CreateCustomerAccountMutation.CustomerCreate>(dispatcher) {
    override suspend fun block(param: CustomerCreateInput): CreateCustomerAccountMutation.CustomerCreate {
        return repo.signUP(param)
    }

}