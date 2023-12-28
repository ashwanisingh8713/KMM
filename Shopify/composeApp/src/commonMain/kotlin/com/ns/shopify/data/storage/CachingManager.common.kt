package com.ns.shopify.data.storage

import kotlinx.coroutines.flow.Flow


expect class CachingManager {
    suspend fun saveThemeIndex(index: Int)
    fun getThemeIndex(): Flow<Int>

    suspend fun saveLoggedInStatus(isLoggedIn: Boolean)
    fun getLoggedInStatus(): Flow<Boolean>

    suspend fun saveCartId(cartId: String)
    fun getCartId(): Flow<String>


}