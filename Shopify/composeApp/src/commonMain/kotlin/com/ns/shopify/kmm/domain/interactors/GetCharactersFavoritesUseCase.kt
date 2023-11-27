package com.ns.shopify.kmm.domain.interactors

import com.ns.shopify.kmm.domain.IRepository
import com.ns.shopify.kmm.domain.interactors.type.BaseUseCaseFlow
import com.ns.shopify.kmm.domain.model.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetCharactersFavoritesUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit,List<Character>>(dispatcher) {
    override suspend fun build(param: Unit): Flow<List<Character>> = repository.getCharactersFavorites()
}