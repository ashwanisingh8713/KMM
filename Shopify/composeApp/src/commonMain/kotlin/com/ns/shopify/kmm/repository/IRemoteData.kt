package com.ns.shopify.kmm.repository

import com.ns.shopify.kmm.domain.model.Character

interface IRemoteData {
    suspend fun getCharactersFromApi(): List<Character>
    suspend fun getCharacterFromApi(id: Int): Character
}