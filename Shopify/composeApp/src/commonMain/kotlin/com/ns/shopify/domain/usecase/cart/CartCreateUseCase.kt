package com.ns.shopify.domain.usecase.cart

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CartCreateMutation
import com.ns.shopify.domain.repo.cart.ICartRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import com.ns.shopify.type.CartInput
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 26,December,2023.
 */
class CartCreateUseCase(private val cartRepo: ICartRepo, dispatcher: CoroutineDispatcher)
    :BaseUseCase<CartInput, ApolloResponse<CartCreateMutation.Data>>(dispatcher) {
    override suspend fun block(param: CartInput): ApolloResponse<CartCreateMutation.Data> {
        return cartRepo.createCart(param)
    }
}