package com.ns.shopify.domain.usecase.cart

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CartQuery
import com.ns.shopify.domain.repo.cart.ICartRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 03,January,2024.
 */
class CartQueryUseCase(private val iCartRepo: ICartRepo, dispatcher: CoroutineDispatcher)
    : BaseUseCase<String, ApolloResponse<CartQuery.Data>>(dispatcher) {
    override suspend fun block(param: String): ApolloResponse<CartQuery.Data> {
        return iCartRepo.CartQuery(param)
    }
}