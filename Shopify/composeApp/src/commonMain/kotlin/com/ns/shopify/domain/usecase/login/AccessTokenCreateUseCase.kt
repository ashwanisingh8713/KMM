package com.ns.shopify.domain.usecase.login

import com.ns.shopify.domain.model.SampleModel
import com.ns.shopify.domain.repo.login.ILoginModuleRepo
import com.ns.shopify.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
class AccessTokenCreateUseCase(private val repo: ILoginModuleRepo, dispatcher: CoroutineDispatcher):
    BaseUseCase<Any, SampleModel>(dispatcher) {
    override suspend fun block(params: Any): SampleModel {
        return repo.login(params)
    }
}