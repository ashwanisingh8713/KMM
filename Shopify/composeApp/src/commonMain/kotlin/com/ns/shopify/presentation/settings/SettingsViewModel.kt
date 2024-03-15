package com.ns.shopify.presentation.settings

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.data.storage.UserDataAccess
import kotlinx.coroutines.CoroutineDispatcher
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


    var index by mutableStateOf(0)
        private set


    init {
        observerValue()
    }

    private fun observerValue() {
        screenModelScope.launch {
            cachingManager.getLoggedInStatus().collectLatest {
                loggedInStatus = it
            }
        }

        screenModelScope.launch {
            cachingManager.getThemeIndex().collectLatest {
                themeIndex = it
            }
        }

        screenModelScope.launch {
            cachingManager.getCartCount().collectLatest {
                cartCount = it
            }
        }


    }


    fun saveLoggedInStatus(isLoggedIn: Boolean) {
        screenModelScope.launch(appDispatcher) {
            cachingManager.saveLoggedInStatus(isLoggedIn)
            observerValue()
        }
    }

    fun saveCustomerAccessToken(customerAccessToken: String) {
        screenModelScope.launch(appDispatcher) {
            cachingManager.saveCustomerAccessToken(customerAccessToken)
            observerValue()
        }
    }


}