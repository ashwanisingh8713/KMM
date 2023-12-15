package com.ns.shopify.data.repo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.ns.shopify.ProductDetailQuery
import com.ns.shopify.domain.repo.product.IProductDetailRepo

/**
 * Created by Ashwani Kumar Singh on 08,December,2023.
 */
class ProductDetailRepoImpl(private val  apolloClient: ApolloClient): IProductDetailRepo {
    override suspend fun getProductDetail(id: String): ApolloResponse<ProductDetailQuery.Data> {
        var response = apolloClient.query(ProductDetailQuery(first = Optional.present(10),  productId = Optional.present(id), optionsFirst2 = Optional.present(100), variantsFirst2 = Optional.present(100) )).execute()
        return response
    }
}