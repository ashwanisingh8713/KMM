package com.ns.shopify.presentation.screen.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.app.printLog
import com.ns.shopify.presentation.settings.SettingsViewModel
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 18,December,2023.
 */
class CartScreen:Screen, KoinComponent {
    @Composable
    override fun Content() {
        val cartViewModel = getScreenModel<CartViewModel>()
        val settingsViewModel = getScreenModel<SettingsViewModel>()
        val cartQueryState = cartViewModel.cartQueryState.collectAsState()

        val cartId = settingsViewModel.cartId

        LaunchedEffect(Unit) {
            coroutineScope {
                cartViewModel.cartQuery(cartId)
            }
        }



        if(cartQueryState.value.isLoaded) {
            val lines = cartQueryState.value.success!!.lines
            lines.edges.forEach {
                printLog("CartScreen lineItem: ${it.node.merchandise.onProductVariant?.title}")
            }

            CartList()
        } else {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text(text = "Cart is Empty",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center)
            }
        }
    }

    @Composable
    fun CartList() {
        LazyColumn {
            // LazyColumn is a vertically scrolling list
            items(10) {
                CartItem(
                    cartUiData = UserCartUiData(),
                    onCartItemClicked = {},
                    onIncrement = {},
                    onDecrement = {})
            }

        }

    }
    }