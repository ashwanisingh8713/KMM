package com.ns.shopify.domain.usecase.cart

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CartBuyerIdentityUpdateMutation
import com.ns.shopify.domain.repo.cart.ICartRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import com.ns.shopify.type.CartBuyerIdentityInput
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 05,February,2024.
 */
class CartBuyerIdentityUpdateUseCase(private val cartRepo: ICartRepo, coroutineDispatcher: CoroutineDispatcher):
    BaseUseCase<Pair<CartBuyerIdentityInput, String>, ApolloResponse<CartBuyerIdentityUpdateMutation.Data>>(coroutineDispatcher) {
    override suspend fun block(param: Pair<CartBuyerIdentityInput, String>): ApolloResponse<CartBuyerIdentityUpdateMutation.Data> {
        return cartRepo.CartBuyerIdentity(param.first, param.second)
    }
}