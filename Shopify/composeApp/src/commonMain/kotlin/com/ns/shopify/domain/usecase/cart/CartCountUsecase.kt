package com.ns.shopify.domain.usecase.cart

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CartCountQuery
import com.ns.shopify.domain.repo.cart.ICartRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 28,December,2023.
 */
class CartCountUsecase(private val cartRepo: ICartRepo, coroutineDispatcher: CoroutineDispatcher):BaseUseCase<String, ApolloResponse<CartCountQuery.Data>>(coroutineDispatcher) {
    override suspend fun block(param: String): ApolloResponse<CartCountQuery.Data> {
        return cartRepo.cartCount(param)
    }
}