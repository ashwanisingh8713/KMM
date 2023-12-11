package com.ns.shopify.domain.repo.product

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.ProductDetailQuery

/**
 * Created by Ashwani Kumar Singh on 08,December,2023.
 */
interface IProductDetailRepo {
    suspend fun getProductDetail(id: String): ApolloResponse<ProductDetailQuery.Data>

}