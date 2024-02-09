package com.ns.shopify.presentation.screen.checkout

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CheckoutCompleteWithCreditCardV2Mutation
import com.ns.shopify.CheckoutCreateMutation
import com.ns.shopify.CheckoutCustomerAssociateV2Mutation
import com.ns.shopify.CheckoutShippingAddressUpdateV2Mutation
import com.ns.shopify.CheckoutShippingLineUpdateMutation

/**
 * Created by Ashwani Kumar Singh on 08,February,2024.
 */
data class CheckoutCreateState(val isLoaded: Boolean = false,
                               val isLoading: Boolean = false,
                               val success: ApolloResponse<CheckoutCreateMutation.Data>? = null,
                               val error: String = "")

data class CheckoutCustomerAssociateState(val isLoaded: Boolean = false,
                                          val isLoading: Boolean = false,
                                          val success: ApolloResponse<CheckoutCustomerAssociateV2Mutation.Data>? = null,
                                          val error: String = "")

data class CheckoutShippingLineUpdateState(val isLoaded: Boolean = false,
                                           val isLoading: Boolean = false,
                                           val success: ApolloResponse<CheckoutShippingLineUpdateMutation.Data>? = null,
                                           val error: String = "")

data class CheckoutShippingAddressUpdateState(val isLoaded: Boolean = false,
                                              val isLoading: Boolean = false,
                                              val success: ApolloResponse<CheckoutShippingAddressUpdateV2Mutation.Data>? = null,
                                              val error: String = "")

data class CheckoutCompleteWithCreditCardState(val isLoaded: Boolean = false,
                                               val isLoading: Boolean = false,
                                               val success: ApolloResponse<CheckoutCompleteWithCreditCardV2Mutation.Data>? = null,
                                               val error: String = "")

