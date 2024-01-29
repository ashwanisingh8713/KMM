package com.ns.shopify.domain.usecase.cart

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CartLinesUpdateMutation
import com.ns.shopify.domain.repo.cart.ICartRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import com.ns.shopify.type.CartLineInput
import com.ns.shopify.type.CartLineUpdateInput
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 29,January,2024.
 */
class CartUpdateUseCase (private val cartRepo: ICartRepo, coroutineDispatcher: CoroutineDispatcher):
    BaseUseCase<Pair<String, List<CartLineUpdateInput>>, ApolloResponse<CartLinesUpdateMutation.Data>>(coroutineDispatcher)  {
    override suspend fun block(param: Pair<String, List<CartLineUpdateInput>>): ApolloResponse<CartLinesUpdateMutation.Data> {
        return cartRepo.CartUpdate(param.first, param.second)
    }

}