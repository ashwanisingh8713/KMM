package com.ns.shopify.data.repo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.CheckoutCompleteWithCreditCardV2Mutation
import com.ns.shopify.CheckoutCreateMutation
import com.ns.shopify.CheckoutCustomerAssociateV2Mutation
import com.ns.shopify.CheckoutShippingAddressUpdateV2Mutation
import com.ns.shopify.CheckoutShippingLineUpdateMutation
import com.ns.shopify.domain.repo.checkout.ICheckoutRepo
import com.ns.shopify.type.CheckoutCreateInput
import com.ns.shopify.type.CreditCardPaymentInputV2
import com.ns.shopify.type.MailingAddressInput

/**
 * Created by Ashwani Kumar Singh on 08,February,2024.
 */
class CheckoutRepoImpl(private val apolloClient: ApolloClient):ICheckoutRepo {
    override suspend fun checkoutCreate(input: CheckoutCreateInput): ApolloResponse<CheckoutCreateMutation.Data> {
        return apolloClient.mutation(CheckoutCreateMutation(input)).execute()
    }

    override suspend fun checkoutCustomerAssociateV2(
        checkoutId: String,
        customerAccessToken: String
    ): ApolloResponse<CheckoutCustomerAssociateV2Mutation.Data> {
        return apolloClient.mutation(CheckoutCustomerAssociateV2Mutation(checkoutId, customerAccessToken)).execute()
    }

    override suspend fun checkoutShippingLineUpdate(
        checkoutId: String,
        shippingRateHandle: String
    ): ApolloResponse<CheckoutShippingLineUpdateMutation.Data> {
        return apolloClient.mutation(CheckoutShippingLineUpdateMutation(checkoutId, shippingRateHandle)).execute()
    }

    override suspend fun checkoutShippingAddressUpdateV2(
        checkoutId: String,
        shippingAddress: MailingAddressInput
    ): ApolloResponse<CheckoutShippingAddressUpdateV2Mutation.Data> {
        return apolloClient.mutation(CheckoutShippingAddressUpdateV2Mutation(checkoutId, shippingAddress)).execute()
    }

    override suspend fun checkoutCompleteWithCreditCardV2(
        checkoutId: String,
        creditCartPaymentInput: CreditCardPaymentInputV2
    ): ApolloResponse<CheckoutCompleteWithCreditCardV2Mutation.Data> {
        return apolloClient.mutation(CheckoutCompleteWithCreditCardV2Mutation(checkoutId, creditCartPaymentInput)).execute()
    }

}