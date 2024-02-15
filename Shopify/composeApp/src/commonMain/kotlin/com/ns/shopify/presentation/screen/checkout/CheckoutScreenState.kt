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
                               val checkoutId: String = "",
                               val shippingRateHandle: String = "",
                               val totalPrice: CheckoutCreateMutation.TotalPrice? = null,
                               val error: String = "")

data class CheckoutCustomerAssociateState(val isLoaded: Boolean = false,
                                          val isLoading: Boolean = false,
                                          val checkoutId: String = "",
                                          val webUrl: String? = "",
                                          val error: String = "")

data class CheckoutShippingLineUpdateState(val isLoaded: Boolean = false,
                                           val isLoading: Boolean = false,
                                           val checkoutId: String = "",
                                           val webUrl: String = "",
                                           val shippingLineHandle: String = "",
                                           val error: String = "")

data class CheckoutShippingAddressUpdateState(val isLoaded: Boolean = false,
                                              val isLoading: Boolean = false,
                                              val checkoutId: String = "",
                                              val success: CheckoutShippingAddressUpdateV2Mutation.ShippingAddress? = null,
                                              val error: String = "")

data class CheckoutCompleteWithCreditCardState(val isLoaded: Boolean = false,
                                               val isLoading: Boolean = false,
                                               val checkoutId: String = "",
                                               val success: ApolloResponse<CheckoutCompleteWithCreditCardV2Mutation.Data>? = null,
                                               val error: String = "")

