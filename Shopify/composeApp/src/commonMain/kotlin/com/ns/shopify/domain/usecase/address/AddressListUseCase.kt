package com.ns.shopify.domain.usecase.address

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.GetAddressQuery
import com.ns.shopify.domain.repo.address.IAddressModuleRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 01,February,2024.
 */
class AddressListUseCase(private val addAddressListRepo: IAddressModuleRepo, coroutineDispatcher: CoroutineDispatcher)
    : BaseUseCase<String, ApolloResponse<GetAddressQuery.Data>>(coroutineDispatcher) {
    override suspend fun block(customerAccessToken: String): ApolloResponse<GetAddressQuery.Data> {
        return addAddressListRepo.getAddresses(customerAccessToken)
    }
}