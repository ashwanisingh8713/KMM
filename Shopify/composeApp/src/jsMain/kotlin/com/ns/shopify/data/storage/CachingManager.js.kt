package com.ns.shopify.data.storage

import com.ns.shopify.GetAddressQuery
import com.russhwolf.settings.StorageSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

actual class CachingManager {

    val storage = StorageSettings()

    /*val cartId: Flow<String?>*/
    actual suspend fun saveThemeIndex(index: Int) {
        storage.putInt("theme_index", index)
    }

    actual fun getThemeIndex(): Flow<Int> = flow {
        emit(storage.getInt("theme_index", 0))
    }

    actual suspend fun saveLoggedInStatus(isLoggedIn: Boolean) {
        storage.putBoolean("logged_in", isLoggedIn)
    }

    actual fun getLoggedInStatus(): Flow<Boolean> = flow{
        emit(storage.getBoolean("logged_in", false))
    }

    actual suspend fun saveCartId(cartId: String) {
        storage.putString("cart_id", cartId)
    }

    actual fun getCartId(): Flow<String> = flow{
        emit(storage.getString("cart_id", ""))
    }

    actual suspend fun saveCheckoutUrl(checkoutUrl: String) {
        storage.putString("checkout_url", checkoutUrl)
    }

    actual fun getCheckoutUrl(): Flow<String> {
        return flow {
            emit(storage.getString("checkout_url", ""))
        }
    }

    actual suspend fun saveCartCount(count: Int) {
        storage.putInt("cart_count", count)
    }

    actual fun getCartCount(): Flow<Int> {
        return flow {
            emit(storage.getInt("cart_count", 0))
        }
    }

    actual suspend fun saveCustomerAccessToken(customerAccessToken: String) {
        storage.putString("customerAccessToken", customerAccessToken)
    }

    actual fun getCustomerAccessToken(): Flow<String> {
        return flow {
            emit(storage.getString("customerAccessToken", ""))
        }
    }

    actual suspend fun saveCustomerEmail(customerEmail: String) {
        storage.putString("customerEmail", customerEmail)
    }

    actual fun getCustomerEmail(): Flow<String> {
        return flow {
            emit(storage.getString("customerEmail", ""))
        }
    }

    actual suspend fun saveCustomerPhone(phone: String) {
        storage.putString("customerPhone", phone)
    }

    actual fun getCustomerPhone(): Flow<String> {
        return flow {
            emit(storage.getString("customerPhone", ""))
        }
    }

    actual suspend fun saveCustomerAddressId(addressId: String) {
        storage.putString("customerAddressId", addressId)
    }

    actual fun getCustomerAddressId(): Flow<String> {
        return flow {
            emit(storage.getString("customerAddressId", ""))
        }
    }

    actual suspend fun saveSelectedAddress(addressNode: GetAddressQuery.Node) {
        storage.putString("selectedAddress", addressNode.toString())
    }

    actual fun getSelectedAddress(): Flow<String> {
        return flow {
            emit(storage.getString("selectedAddress", ""))
        }
    }

}