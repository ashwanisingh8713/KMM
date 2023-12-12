package com.ns.shopify.domain.usecase.product

import com.apollographql.apollo3.api.ApolloResponse
import com.ns.shopify.GetCollectionsQuery
import com.ns.shopify.domain.repo.product.ICategoryCollectionRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 07,December,2023.
 */
class CategoryCollectionUsecase(
    private val categoryCollectionRepo: ICategoryCollectionRepo,
    coroutineDispatcher: CoroutineDispatcher
) : BaseUseCase<Any, ApolloResponse<GetCollectionsQuery.Data>>(coroutineDispatcher) {
    override suspend fun block(param: Any): ApolloResponse<GetCollectionsQuery.Data> {
        return categoryCollectionRepo.getCategoryCollection()
    }

}