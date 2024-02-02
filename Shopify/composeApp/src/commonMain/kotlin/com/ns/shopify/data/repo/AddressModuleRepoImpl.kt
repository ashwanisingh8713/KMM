package com.ns.shopify.data.repo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.ns.shopify.CustomerAddressCreateMutation
import com.ns.shopify.GetAddressQuery
import com.ns.shopify.domain.repo.address.IAddressModuleRepo
import com.ns.shopify.type.MailingAddressInput

/**
 * Created by Ashwani Kumar Singh on 31,January,2024.
 */
class AddressModuleRepoImpl(private val apolloClient: ApolloClient): IAddressModuleRepo {
    override suspend fun createAddress(customerAccessToken: String, input: MailingAddressInput): CustomerAddressCreateMutation.CustomerAddressCreate {
        return apolloClient.mutation(CustomerAddressCreateMutation(customerAccessToken = customerAccessToken, address = input)).execute().data?.customerAddressCreate!!
    }

    override suspend fun getAddresses(customerAccessToken: String): ApolloResponse<GetAddressQuery.Data> {
        return apolloClient.query(GetAddressQuery(customerAccessToken = customerAccessToken, first = Optional.Present(10))).execute()
    }

}