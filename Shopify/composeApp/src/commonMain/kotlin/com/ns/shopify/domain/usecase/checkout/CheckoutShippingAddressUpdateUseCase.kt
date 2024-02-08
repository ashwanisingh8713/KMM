package com.ns.shopify.domain.usecase.checkout

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CheckoutShippingAddressUpdateV2Mutation
import com.ns.shopify.domain.repo.checkout.ICheckoutRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import com.ns.shopify.type.MailingAddressInput
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 07,February,2024.
 */
class CheckoutShippingAddressUpdateUseCase(private val checkoutRepo: ICheckoutRepo, coroutineDispatcher: CoroutineDispatcher):
    BaseUseCase<Pair<String, MailingAddressInput>, ApolloResponse<CheckoutShippingAddressUpdateV2Mutation.Data>>(coroutineDispatcher) {
    override suspend fun block(param: Pair<String, MailingAddressInput>): ApolloResponse<CheckoutShippingAddressUpdateV2Mutation.Data> {
        return checkoutRepo.checkoutShippingAddressUpdateV2(param.first, param.second)
    }

}