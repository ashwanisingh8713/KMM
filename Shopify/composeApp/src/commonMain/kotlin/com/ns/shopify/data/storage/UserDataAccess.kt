package com.ns.shopify.data.storage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.getKoin

/**
 * Created by Ashwani Kumar Singh on 08,February,2024.
 */
object UserDataAccess {

    val Address_Delemeter = "§"

    var selectedFullAddress: String = ""
    private set

    var customerAddressId: String = ""
    private set

    var email: String = ""
        private set

    var phone: String = ""
        private set

    var customerAccessToken: String = ""
        private set

    var checkoutUrl: String = ""
        private set

    var cartCount by mutableStateOf(0)
        private set

    var cartId by mutableStateOf("")
        private set

    var addressId by mutableStateOf("")
        private set



    fun refreshData(coroutineScope: CoroutineScope, cachingManager: CachingManager) {
        coroutineScope.launch {
            cachingManager.getCustomerAddressId().collectLatest {
                customerAddressId = it
            }
        }

        coroutineScope.launch {
            cachingManager.getSelectedAddress().collectLatest {
                selectedFullAddress = it
            }
        }

        coroutineScope.launch {
            cachingManager.getCustomerEmail().collectLatest {
                email = it
            }
        }

        coroutineScope.launch {
            cachingManager.getCustomerPhone().collectLatest {
                phone = it
            }
        }

        coroutineScope.launch {
            cachingManager.getCustomerAccessToken().collectLatest {
                customerAccessToken = it
            }
        }

        coroutineScope.launch {
            cachingManager.getCheckoutUrl().collectLatest {
                checkoutUrl = it
            }
        }

        coroutineScope.launch {
            cachingManager.getCartCount().collectLatest {
                cartCount = it
            }
        }

        coroutineScope.launch {
            cachingManager.getCartId().collectLatest {
                cartId = it
            }
        }

        coroutineScope.launch {
            cachingManager.getCustomerAddressId().collectLatest {
                addressId = it
            }
        }


    }

}