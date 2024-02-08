package com.ns.shopify.domain.usecase.checkout

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CheckoutCompleteWithCreditCardV2Mutation
import com.ns.shopify.domain.repo.checkout.ICheckoutRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import com.ns.shopify.type.CreditCardPaymentInputV2
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 07,February,2024.
 */
class CheckoutCompleteWithCreditCardUseCase(private val checkoutRepo: ICheckoutRepo, coroutineDispatcher: CoroutineDispatcher):
    BaseUseCase<Pair<String, CreditCardPaymentInputV2>, ApolloResponse<CheckoutCompleteWithCreditCardV2Mutation.Data>>(coroutineDispatcher) {
    override suspend fun block(param: Pair<String, CreditCardPaymentInputV2>): ApolloResponse<CheckoutCompleteWithCreditCardV2Mutation.Data> {
        return checkoutRepo.checkoutCompleteWithCreditCardV2(param.first, param.second)
    }


}