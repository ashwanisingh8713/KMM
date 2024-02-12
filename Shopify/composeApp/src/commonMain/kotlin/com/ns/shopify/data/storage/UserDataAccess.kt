package com.ns.shopify.data.storage

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

    var customerAccessToken: String = ""
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
            cachingManager.getCustomerAccessToken().collectLatest {
                customerAccessToken = it
            }
        }

    }

}