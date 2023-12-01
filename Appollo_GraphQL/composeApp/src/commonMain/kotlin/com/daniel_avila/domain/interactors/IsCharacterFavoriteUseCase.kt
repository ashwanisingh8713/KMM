package com.daniel_avila.domain.interactors

import com.daniel_avila.domain.IRepository
import com.daniel_avila.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class IsCharacterFavoriteUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, Boolean>(dispatcher) {
    override suspend fun block(param: Int): Boolean = repository.isCharacterFavorite(param)
}