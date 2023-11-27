package com.daniel_avila.domain.interactors

import com.daniel_avila.domain.IRepository
import com.daniel_avila.domain.interactors.type.BaseUseCase
import com.daniel_avila.domain.model.Character
import kotlinx.coroutines.CoroutineDispatcher

class GetCharacterUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, Character>(dispatcher){
    override suspend fun block(param: Int): Character = repository.getCharacter(param)
}