package com.ns.shopify.domain.repo.product

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.GetCollectionsQuery

/**
 * Created by Ashwani Kumar Singh on 07,December,2023.
 */
interface ICategoryCollectionRepo {
    suspend fun getCategoryCollection(): ApolloResponse<GetCollectionsQuery.Data>
}