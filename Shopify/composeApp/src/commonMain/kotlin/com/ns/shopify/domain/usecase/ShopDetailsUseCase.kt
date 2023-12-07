package com.ns.shopify.domain.usecase

import com.ns.shopify.GetShopDetailsQuery
import com.ns.shopify.domain.model.SampleModel
import com.ns.shopify.domain.repo.IShopDetailsRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 28,November,2023.
 */
class ShopDetailsUseCase(private val getShopDetailsRepo: IShopDetailsRepo, dispatcher: CoroutineDispatcher): BaseUseCase<Any, GetShopDetailsQuery>(dispatcher) {
    override suspend fun block(param: Any):GetShopDetailsQuery {
        return getShopDetailsRepo.getShopDetails()
    }
}