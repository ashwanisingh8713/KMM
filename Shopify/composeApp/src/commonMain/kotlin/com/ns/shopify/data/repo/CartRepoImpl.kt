package com.ns.shopify.data.repo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.ns.shopify.CartCountQuery
import com.ns.shopify.CartCreateMutation
import com.ns.shopify.CartLinesAddMutation
import com.ns.shopify.domain.repo.cart.ICartRepo
import com.ns.shopify.type.CartInput
import com.ns.shopify.type.CartLineInput

/**
 * Created by Ashwani Kumar Singh on 28,December,2023.
 */
class CartRepoImpl(private val apolloClient: ApolloClient) : ICartRepo {
    override suspend fun createCart(cartInput: CartInput): ApolloResponse<CartCreateMutation.Data> {
        return apolloClient.mutation(CartCreateMutation(cartInput = Optional.present(cartInput)))
            .execute()
    }

    override suspend fun addMerchandise(
        cartId: String,
        cartLineInputs: List<CartLineInput>
    ): ApolloResponse<CartLinesAddMutation.Data> {
        return apolloClient.mutation(CartLinesAddMutation(cartId = cartId, lines = cartLineInputs))
            .execute()
    }

    override suspend fun cartCount(cartID: String): ApolloResponse<CartCountQuery.Data> {
        return apolloClient.query(CartCountQuery(cartId = cartID)).execute()
    }
}