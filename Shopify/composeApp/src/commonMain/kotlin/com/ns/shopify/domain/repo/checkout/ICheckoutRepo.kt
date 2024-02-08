package com.ns.shopify.domain.repo.checkout

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CheckoutCompleteWithCreditCardV2Mutation
import com.ns.shopify.CheckoutCreateMutation
import com.ns.shopify.CheckoutCustomerAssociateV2Mutation
import com.ns.shopify.CheckoutShippingAddressUpdateV2Mutation
import com.ns.shopify.CheckoutShippingLineUpdateMutation
import com.ns.shopify.type.CheckoutCreateInput
import com.ns.shopify.type.CreditCardPaymentInputV2
import com.ns.shopify.type.MailingAddressInput

/**
 * Created by Ashwani Kumar Singh on 07,February,2024.
 */
interface ICheckoutRepo {
    suspend fun checkoutCreate(input: CheckoutCreateInput): ApolloResponse<CheckoutCreateMutation.Data>
    suspend fun checkoutCustomerAssociateV2(checkoutId: String, customerAccessToken: String): ApolloResponse<CheckoutCustomerAssociateV2Mutation.Data>
    suspend fun checkoutShippingLineUpdate(checkoutId: String, shippingRateHandle: String): ApolloResponse<CheckoutShippingLineUpdateMutation.Data>
    suspend fun checkoutShippingAddressUpdateV2(checkoutId: String, shippingAddress: MailingAddressInput): ApolloResponse<CheckoutShippingAddressUpdateV2Mutation.Data>
    suspend fun checkoutCompleteWithCreditCardV2(checkoutId: String, creditCartPaymentInput: CreditCardPaymentInputV2): ApolloResponse<CheckoutCompleteWithCreditCardV2Mutation.Data>

}