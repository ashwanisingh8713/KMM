package com.ns.domain.graphql

import com.daniel_avila.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 28,November,2023.
 */
class GetShopDetailsUseCase(private val getShopDetailsRepo: GetShopDetailsRepo, dispatcher: CoroutineDispatcher): BaseUseCase<Unit, Unit>(dispatcher) {
    override suspend fun block(param: Unit) {
        getShopDetailsRepo.getShopDetails()
    }
}