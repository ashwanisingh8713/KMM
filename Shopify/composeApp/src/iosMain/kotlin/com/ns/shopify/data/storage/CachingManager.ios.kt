package com.ns.shopify.data.storage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.app.printLog
import com.ns.shopify.GetAddressQuery
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
        printLog("Save Cart - Id :: $cartId")
    }

    actual fun getCartId(): Flow<String> = flow {
        val cartId = userDefault.stringForKey("cart_id")
        printLog("Get Cart - Id :: $cartId")
        emit(cartId.toString())
    }

    actual suspend fun saveCheckoutUrl(checkoutUrl: String) {
        userDefault.setObject(checkoutUrl, "checkout_url")
        printLog("Save Cart - Checkout Url :: $checkoutUrl")
    }

    actual fun getCheckoutUrl(): Flow<String> = flow {
        val s = userDefault.stringForKey("checkout_url")
        printLog("Get Cart - Checkout Url :: $s")
        emit(s.toString())
    }

    actual suspend fun saveCartCount(count: Int) {
        userDefault.setInteger(count.toLong(), "cart_count")
        printLog("Save Cart - Count :: $count")
    }

    actual fun getCartCount(): Flow<Int>  = flow {
        val count = userDefault.integerForKey("cart_count")
        emit(count.toInt())
        printLog("Get Cart - Count :: $count")
    }

    actual suspend fun saveCustomerAccessToken(customerAccessToken: String) {
        userDefault.setObject(customerAccessToken, "customerAccessToken")
    }

    actual fun getCustomerAccessToken(): Flow<String> = flow {
        val s = userDefault.stringForKey("customerAccessToken")
        emit(s.toString())
    }

    actual suspend fun saveCustomerEmail(customerEmail: String) {
        userDefault.setObject(customerEmail, "customerEmail")
    }

    actual fun getCustomerEmail(): Flow<String> = flow {
        val s = userDefault.stringForKey("customerEmail")
        emit(s.toString())
    }

    actual suspend fun saveCustomerPhone(phone: String) {
        userDefault.setObject(phone, "customerPhone")
    }

    actual fun getCustomerPhone(): Flow<String> = flow{
        val s = userDefault.stringForKey("customerPhone")
        emit(s.toString())
    }

    actual suspend fun saveCustomerAddressId(addressId: String) {
        userDefault.setObject(addressId, "customerAddressId")
    }

    actual fun getCustomerAddressId(): Flow<String> = flow {
        val s = userDefault.stringForKey("customerAddressId")
        emit(s.toString())
    }

    actual suspend fun saveSelectedAddress(addressNode: GetAddressQuery.Node) {
        val fullAddress =
            "${addressNode.address1}Address_Delemeter${addressNode.address2}Address_Delemeter${addressNode.city}Address_Delemeter${addressNode.province}Address_Delemeter${addressNode.country}Address_Delemeter${addressNode.zip}Address_Delemeter${addressNode.province}Address_Delemeter${addressNode.phone}"
        userDefault.setObject(fullAddress, "selectedAddress")
    }

    actual fun getSelectedAddress(): Flow<String> = flow {
        val s = userDefault.stringForKey("selectedAddress")
        emit(s.toString())
    }


}