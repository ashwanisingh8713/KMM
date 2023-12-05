package com.ns.shopify.presentation.settings

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.ns.shopify.data.storage.CachingManager
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
    var index by mutableStateOf(0)
        private set

    init {
        observerValue()
    }

    private fun observerValue() {
        coroutineScope.launch {
            cachingManager.getThemeIndex().collectLatest {
                index = it
            }
        }
    }

    fun saveThemeIndex(index: Int) {
        coroutineScope.launch(appDispatcher) {
            cachingManager.saveThemeIndex(index)
            observerValue()
        }
    }
}