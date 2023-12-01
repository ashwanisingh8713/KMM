package com.ns.shopify.data.repo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.ns.shopify.LaunchListQuery
import com.ns.shopify.domain.model.SampleModel
import com.ns.shopify.domain.repo.ShopDetailsRepo

/**
 * Created by Ashwani Kumar Singh on 28,November,2023.
 */
class ShopDetailsRepoImpl(private val apolloClient: ApolloClient) : ShopDetailsRepo {
    override suspend fun getShopDetails(): SampleModel {
        val response = apolloClient.query(LaunchListQuery(Optional.present(""))).execute()
        val model = response.data?.launches?.launches?.filterNotNull().orEmpty()
        return SampleModel()
    }
}