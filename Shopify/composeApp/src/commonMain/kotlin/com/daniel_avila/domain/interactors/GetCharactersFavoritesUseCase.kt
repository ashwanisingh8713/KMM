package com.daniel_avila.domain.interactors

import com.daniel_avila.domain.IRepository
import com.daniel_avila.domain.interactors.type.BaseUseCaseFlow
import com.daniel_avila.domain.model.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetCharactersFavoritesUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit, List<Character>>(dispatcher) {
    override suspend fun build(param: Unit): Flow<List<Character>> = repository.getCharactersFavorites()
}