package com.ns.shopify.domain.usecase.cart

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CartLinesAddMutation
import com.ns.shopify.domain.repo.cart.ICartRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import com.ns.shopify.type.CartLineInput
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 28,December,2023.
 */
class AddMerchandiseUseCase(private val cartRepo:ICartRepo, coroutineDispatcher: CoroutineDispatcher):BaseUseCase<Pair<String, List<CartLineInput>>, ApolloResponse<CartLinesAddMutation.Data>>(coroutineDispatcher) {
    override suspend fun block(param: Pair<String, List<CartLineInput>>): ApolloResponse<CartLinesAddMutation.Data> {
        return cartRepo.addMerchandise(param.first, param.second)
    }

}