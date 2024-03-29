package com.ns.shopify.data.storage

import androidx.compose.runtime.MutableState
import com.ns.shopify.GetAddressQuery
import kotlinx.coroutines.flow.Flow


expect class CachingManager {

    suspend fun saveThemeIndex(index: Int)
    fun getThemeIndex(): Flow<Int>

    suspend fun saveLoggedInStatus(isLoggedIn: Boolean)
    fun getLoggedInStatus(): Flow<Boolean>

    suspend fun saveCartId(cartId: String)
    fun getCartId(): Flow<String>

    /*val cartId: Flow<String?>*/

    suspend fun saveCheckoutUrl(checkoutUrl: String)
    fun getCheckoutUrl(): Flow<String>

    suspend fun saveCartCount(count: Int)
    fun getCartCount(): Flow<Int>

    suspend fun saveCustomerAccessToken(customerAccessToken: String)
    fun getCustomerAccessToken(): Flow<String>

    suspend fun saveCustomerEmail(customerEmail: String)
    fun getCustomerEmail(): Flow<String>

    suspend fun saveCustomerPhone(phone: String)
    fun getCustomerPhone(): Flow<String>

    suspend fun saveCustomerAddressId(addressId: String)
    fun getCustomerAddressId(): Flow<String>

    suspend fun saveSelectedAddress(addressNode: GetAddressQuery.Node)
    fun getSelectedAddress(): Flow<String>

}