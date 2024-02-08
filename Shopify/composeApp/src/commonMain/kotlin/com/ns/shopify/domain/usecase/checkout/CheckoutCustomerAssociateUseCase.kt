package com.ns.shopify.domain.usecase.checkout

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CheckoutCustomerAssociateV2Mutation
import com.ns.shopify.domain.repo.checkout.ICheckoutRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 07,February,2024.
 */
class CheckoutCustomerAssociateUseCase(private val checkoutRepo: ICheckoutRepo, coroutineDispatcher: CoroutineDispatcher):
    BaseUseCase<Pair<String, String>, ApolloResponse<CheckoutCustomerAssociateV2Mutation.Data>>(coroutineDispatcher) {
    override suspend fun block(param: Pair<String, String>): ApolloResponse<CheckoutCustomerAssociateV2Mutation.Data> {
        return checkoutRepo.checkoutCustomerAssociateV2(param.first, param.second)
    }

}