package com.ns.shopify.data.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */

actual class CachingManager(private val context: Context) {

    private val Context.dataStore by preferencesDataStore("theme_cache")

    private val theme_key = intPreferencesKey("index_key")
    private val loggedInStatus_key = booleanPreferencesKey("loggedInStatus_key")
    private val cartId_key = stringPreferencesKey("cartId_key")
    actual suspend fun saveThemeIndex(index: Int) {
        context.dataStore.edit {
            it[theme_key] = index
        }
    }

    actual fun getThemeIndex(): Flow<Int> = context.dataStore.data.map {
        it[theme_key] ?: 0
    }

    actual suspend fun saveLoggedInStatus(isLoggedIn: Boolean) {
        context.dataStore.edit {
            it[loggedInStatus_key] = isLoggedIn
        }
    }

    actual fun getLoggedInStatus(): Flow<Boolean> = context.dataStore.data.map {
        it[loggedInStatus_key] ?: false
    }

    actual suspend fun saveCartId(cartId: String) {
        context.dataStore.edit {
            it[cartId_key] = cartId
        }
    }

    actual fun getCartId() = context. dataStore.data.map {
        it[cartId_key] ?: ""
    }
}