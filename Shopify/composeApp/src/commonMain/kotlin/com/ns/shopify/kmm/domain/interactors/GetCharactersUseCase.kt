package com.ns.shopify.kmm.domain.interactors

import com.ns.shopify.kmm.domain.IRepository
import com.ns.shopify.kmm.domain.interactors.type.BaseUseCase
import com.ns.shopify.kmm.domain.model.Character
import kotlinx.coroutines.CoroutineDispatcher

class GetCharactersUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, List<Character>>(dispatcher){
    override suspend fun block(param: Unit): List<Character> = repository.getCharacters()
}