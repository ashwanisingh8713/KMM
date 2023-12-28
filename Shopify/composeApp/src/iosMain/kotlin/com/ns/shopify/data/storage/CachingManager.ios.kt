package com.ns.shopify.data.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */

actual class CachingManager {

    private val userDefault = NSUserDefaults.standardUserDefaults
    actual suspend fun saveThemeIndex(index: Int) {
        userDefault.setInteger(index.toLong(), "theme_index")
    }

    actual fun getThemeIndex(): Flow<Int> = flow {
        val s = userDefault.integerForKey("theme_index")
        emit(s.toInt())
    }

    actual suspend fun saveLoggedInStatus(isLoggedIn: Boolean) {
        userDefault.setBool(isLoggedIn, "logged_in")
    }

    actual fun getLoggedInStatus(): Flow<Boolean> = flow {
        val s = userDefault.boolForKey("logged_in")
        emit(s)
    }

    actual suspend fun saveCartId(cartId: String) {
        userDefault.setObject(cartId, "cart_id")
    }

    actual fun getCartId(): Flow<String> = flow {
        val s = userDefault.stringForKey("cart_id")
        emit(s.toString())
    }

}