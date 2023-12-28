package com.ns.shopify.domain.repo.cart

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CartCountQuery
import com.ns.shopify.CartCreateMutation
import com.ns.shopify.CartLinesAddMutation
import com.ns.shopify.type.CartInput
import com.ns.shopify.type.CartLineInput

/**
 * Created by Ashwani Kumar Singh on 26,December,2023.
 */
interface ICartRepo {

    // It creates new cart with given merchandise
    suspend fun createCart(cartInput: CartInput):ApolloResponse<CartCreateMutation.Data>

    // It adds the selected merchandise in cart
    suspend fun addMerchandise(cartId: String, cartLineInputs: List<CartLineInput>): ApolloResponse<CartLinesAddMutation.Data>
    // It returns total number of items in Cart
    suspend fun cartCount(cartID: String):ApolloResponse<CartCountQuery.Data>
}