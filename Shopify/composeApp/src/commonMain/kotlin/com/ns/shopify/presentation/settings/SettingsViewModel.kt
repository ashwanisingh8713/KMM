package com.ns.shopify.presentation.settings

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.ns.shopify.data.storage.CachingManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */
class SettingsViewModel(
    private val cachingManager: CachingManager, private val appDispatcher: CoroutineDispatcher
): ScreenModel,KoinComponent {
    var themeIndex by mutableStateOf(0)
        private set

    var loggedInStatus by mutableStateOf(false)
        private set

    var cartCount by mutableStateOf(0)
        private set

    var cartId by mutableStateOf("")
        private set
    var checkoutUrl by mutableStateOf("")
        private set

    var index by mutableStateOf(0)
        private set


    init {
        observerValue()
    }

    private fun observerValue() {
        coroutineScope.launch {
            cachingManager.getLoggedInStatus().collectLatest {
                loggedInStatus = it
            }
        }

        coroutineScope.launch {
            cachingManager.getThemeIndex().collectLatest {
                themeIndex = it
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
            cachingManager.getCheckoutUrl().collectLatest {
                checkoutUrl = it
            }
        }

    }

    fun saveThemeIndex(index: Int) {
        coroutineScope.launch {
            cachingManager.saveThemeIndex(index)
        }
    }

    fun saveCartCount(count: Int) {
        coroutineScope.launch {
            cachingManager.saveCartCount(count)
            observerValue()
        }
    }

    fun saveCartId(cartId: String) {
        coroutineScope.launch {
            cachingManager.saveCartId(cartId)
            observerValue()
        }
    }
    fun saveCheckoutUrl(checkoutUrl: String) {
        coroutineScope.launch {
            cachingManager.saveCheckoutUrl(checkoutUrl)
            observerValue()
        }
    }

    fun saveLoggedInStatus(isLoggedIn: Boolean) {
        coroutineScope.launch(appDispatcher) {
            cachingManager.saveLoggedInStatus(isLoggedIn)
            observerValue()
        }
    }

    fun saveCustomerAccessToken(customerAccessToken: String) {
        coroutineScope.launch(appDispatcher) {
            cachingManager.saveCustomerAccessToken(customerAccessToken)
            observerValue()
        }
    }


}