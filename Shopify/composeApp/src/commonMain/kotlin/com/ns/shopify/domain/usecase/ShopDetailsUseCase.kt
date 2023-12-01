package com.ns.shopify.domain.usecase

import com.ns.shopify.domain.model.SampleModel
import com.ns.shopify.domain.repo.ShopDetailsRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 28,November,2023.
 */
class ShopDetailsUseCase(private val getShopDetailsRepo: ShopDetailsRepo, dispatcher: CoroutineDispatcher): BaseUseCase<Any, SampleModel>(dispatcher) {
    override suspend fun block(param: Any):SampleModel {
        return getShopDetailsRepo.getShopDetails()
    }
}