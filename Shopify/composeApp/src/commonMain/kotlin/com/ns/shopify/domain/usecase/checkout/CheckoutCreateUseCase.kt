package com.ns.shopify.domain.usecase.checkout

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CheckoutCreateMutation
import com.ns.shopify.domain.repo.checkout.ICheckoutRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import com.ns.shopify.type.CheckoutCreateInput
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 07,February,2024.
 */
class CheckoutCreateUseCase(private val checkoutRepo: ICheckoutRepo, coroutineDispatcher: CoroutineDispatcher):
    BaseUseCase<CheckoutCreateInput, ApolloResponse<CheckoutCreateMutation.Data>>(coroutineDispatcher) {
    override suspend fun block(param: CheckoutCreateInput): ApolloResponse<CheckoutCreateMutation.Data> {
        return checkoutRepo.checkoutCreate(param)
    }

}