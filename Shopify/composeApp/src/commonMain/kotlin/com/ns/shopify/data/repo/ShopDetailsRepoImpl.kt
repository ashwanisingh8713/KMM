package com.ns.shopify.data.repo

import com.apollographql.apollo3.ApolloClient
import com.ns.shopify.GetShopDetailsQuery
import com.ns.shopify.domain.repo.IShopDetailsRepo

/**
 * Created by Ashwani Kumar Singh on 28,November,2023.
 */
class ShopDetailsRepoImpl(private val apolloClient: ApolloClient) : IShopDetailsRepo {
    override suspend fun getShopDetails(): GetShopDetailsQuery {
//        val response = apolloClient.query(LaunchListQuery(Optional.present(""))).execute()
//        val model = response.data?.launches?.launches?.filterNotNull().orEmpty()
        return GetShopDetailsQuery()
    }
}