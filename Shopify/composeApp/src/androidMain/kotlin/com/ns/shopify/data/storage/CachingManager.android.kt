package com.ns.shopify.data.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.printLog
import com.ns.shopify.GetAddressQuery
import com.ns.shopify.data.utils.fullAddressFormatting
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
    private val checkoutUrl_key = stringPreferencesKey("checkoutUrl_key")
    private val cartCount_key = intPreferencesKey("cartCount_key")
    private val customerAccessToken_key = stringPreferencesKey("customerAccessToken_key")
    private val customerEmail_key = stringPreferencesKey("customerEmail_key")
    private val customerPhone_key = stringPreferencesKey("customerPhone_key")
    private val customerAddressId_key = stringPreferencesKey("customerAddressId_key")
    private val selectedAddress_key = stringPreferencesKey("selectedAddress_key")

    // ->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    // Saves the theme index
    actual suspend fun saveThemeIndex(index: Int) {
        context.dataStore.edit {
            it[theme_key] = index
        }
    }

    actual fun getThemeIndex(): Flow<Int> = context.dataStore.data.map {
        it[theme_key] ?: 0
    }

    // ->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    // Saves the logged in status
    actual suspend fun saveLoggedInStatus(isLoggedIn: Boolean) {
        context.dataStore.edit {
            it[loggedInStatus_key] = isLoggedIn
        }
    }

    actual fun getLoggedInStatus(): Flow<Boolean> = context.dataStore.data.map {
        it[loggedInStatus_key] ?: false
    }

    // ->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    // Saves the cart id
    actual suspend fun saveCartId(cartId: String) {
        context.dataStore.edit {
            it[cartId_key] = cartId
            printLog("Save Cart - Id :: ${it[cartId_key]}")
        }
    }

    // Get the cart id
    actual fun getCartId() = context.dataStore.data.map {
        val cartId = it[cartId_key] ?: ""
        printLog("Get Cart - Id :: $cartId")
        cartId
    }

    // ->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    // Saves the checkout url
    actual suspend fun saveCheckoutUrl(checkoutUrl: String) {
        context.dataStore.edit {
            it[checkoutUrl_key] = checkoutUrl
            printLog("Save Cart - Checkout Url :: $checkoutUrl")
        }
    }

    // Get the checkout url
    actual fun getCheckoutUrl(): Flow<String> = context.dataStore.data.map {
        val checkoutUrl = it[checkoutUrl_key] ?: ""
        printLog("Get Cart - Checkout Url :: $checkoutUrl")
        checkoutUrl
    }

    // ->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    // Saves the cart count
    actual suspend fun saveCartCount(count: Int) {
        context.dataStore.edit {
            it[cartCount_key] = count
            printLog("Save Cart - Count :: ${it[intPreferencesKey("cartCount_key")]}")
        }
    }

    // Get the cart count
    actual fun getCartCount(): Flow<Int> = context.dataStore.data.map {
        val cartCount = it[cartCount_key] ?: 0
        printLog("Get Cart - Count :: $cartCount")
        cartCount
    }

    // ->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    // Saves the customer access token
    actual suspend fun saveCustomerAccessToken(customerAccessToken: String) {
        context.dataStore.edit {
            it[customerAccessToken_key] = customerAccessToken
            printLog("CustomerAccessToken :: $customerAccessToken")
        }
    }

    // Get the customer access token
    actual fun getCustomerAccessToken(): Flow<String> = context.dataStore.data.map {
        val customerAccessToken = it[customerAccessToken_key] ?: ""
        printLog("CustomerAccessToken :: $customerAccessToken")
        customerAccessToken
    }
    // ->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    // Saves the customer email
    actual suspend fun saveCustomerEmail(customerEmail: String) {
        context.dataStore.edit {
            it[customerEmail_key] = customerEmail
        }
    }

    // Get the customer email
    actual fun getCustomerEmail(): Flow<String> = context.dataStore.data.map {
        it[customerEmail_key] ?: ""
    }

    // ->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    // Saves the customer phone
    actual suspend fun saveCustomerPhone(phone: String) {
        context.dataStore.edit {
            it[customerPhone_key] = phone
        }
    }

    // Get the customer phone
    actual fun getCustomerPhone(): Flow<String> = context.dataStore.data.map {
        it[customerPhone_key] ?: ""
    }

    // ->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    // Saves the customer address id
    actual suspend fun saveCustomerAddressId(addressId: String) {
        context.dataStore.edit {
            it[customerAddressId_key] = addressId
        }
    }

    // Get the customer address id
    actual fun getCustomerAddressId(): Flow<String> = context.dataStore.data.map {
        it[customerAddressId_key] ?: ""
    }

    actual suspend fun saveSelectedAddress(addressNode: GetAddressQuery.Node) {

        context.dataStore.edit {
            it[selectedAddress_key] = fullAddressFormatting(addressNode)
        }
    }

    actual fun getSelectedAddress(): Flow<String> = context.dataStore.data.map {
        it[selectedAddress_key] ?: ""
    }


}