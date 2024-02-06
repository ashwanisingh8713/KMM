package com.ns.shopify.domain.repo.cart

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CartBuyerIdentityUpdateMutation
import com.ns.shopify.CartCountQuery
import com.ns.shopify.CartCreateMutation
import com.ns.shopify.CartLinesAddMutation
import com.ns.shopify.CartLinesUpdateMutation
import com.ns.shopify.CartQuery
import com.ns.shopify.type.CartBuyerIdentityInput
import com.ns.shopify.type.CartInput
import com.ns.shopify.type.CartLineInput
import com.ns.shopify.type.CartLineUpdateInput

/**
 * Created by Ashwani Kumar Singh on 26,December,2023.
 */
interface ICartRepo {

    // It creates new cart with given merchandise
    suspend fun createCart(cartInput: CartInput):ApolloResponse<CartCreateMutation.Data>

    // It adds the selected merchandise in cart
    suspend fun addMerchandise(cartId: String, cartLineInputs: List<CartLineInput>): ApolloResponse<CartLinesAddMutation.Data>
    // It returns total number of items in Cart
    suspend fun cartCount(cartId: String):ApolloResponse<CartCountQuery.Data>

    suspend fun CartQuery(cartId: String):ApolloResponse<CartQuery.Data>

    suspend fun CartBuyerIdentity(buyerIdentity: CartBuyerIdentityInput, cartId: String):ApolloResponse<CartBuyerIdentityUpdateMutation.Data>

    suspend fun CartUpdate(cartId: String, cartLineInputs: List<CartLineUpdateInput>): ApolloResponse<CartLinesUpdateMutation.Data>
}