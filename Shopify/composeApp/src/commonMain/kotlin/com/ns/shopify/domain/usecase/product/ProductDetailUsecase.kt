package com.ns.shopify.domain.usecase.product

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.ProductDetailQuery
import com.ns.shopify.domain.repo.product.IProductDetailRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 08,December,2023.
 */
class ProductDetailUsecase(private val productDetailRepo: IProductDetailRepo, coroutineDispatcher: CoroutineDispatcher):BaseUseCase<Any, ApolloResponse<ProductDetailQuery.Data>>(coroutineDispatcher) {
    override suspend fun block(param: Any): ApolloResponse<ProductDetailQuery.Data> {
        return productDetailRepo.getProductDetail(param as String)
    }
}