package com.ns.shopify.data.repo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.app.printLog
import com.ns.shopify.GetCollectionsQuery
import com.ns.shopify.domain.repo.category.ICategoryCollectionRepo

/**
 * Created by Ashwani Kumar Singh on 07,December,2023.
 */
class CategoryCollectionRepoImpl(private val apolloClient: ApolloClient): ICategoryCollectionRepo {
    override suspend fun getCategoryCollection(): ApolloResponse<GetCollectionsQuery.Data> {
        var response = apolloClient.query(GetCollectionsQuery(first = Optional.present(3),  productsFirst = Optional.present(3))).execute()
        return response
    }
}