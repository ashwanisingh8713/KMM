package com.ns.shopify.domain.usecase.address

import com.ns.shopify.CustomerAddressCreateMutation
import com.ns.shopify.domain.repo.address.IAddressModuleRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import com.ns.shopify.type.MailingAddressInput
import kotlinx.coroutines.CoroutineDispatcher

class AddAddressUseCase( private val repo: IAddressModuleRepo, dispatcher: CoroutineDispatcher):
    BaseUseCase<Pair<String, MailingAddressInput>, CustomerAddressCreateMutation.CustomerAddressCreate>(dispatcher) {
    override suspend fun block(param: Pair<String, MailingAddressInput>): CustomerAddressCreateMutation.CustomerAddressCreate {
        return repo.createAddress(customerAccessToken = param.first, input = param.second )
    }
}