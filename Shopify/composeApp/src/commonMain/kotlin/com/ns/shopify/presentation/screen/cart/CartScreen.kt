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
class CartScreen(private val cartViewModel: CartViewModel):Screen, KoinComponent {
    @Composable
    override fun Content() {
        val settingsViewModel = getScreenModel<SettingsViewModel>()
        val cartQueryState = cartViewModel.cartQueryState.collectAsState()

        val cartId = settingsViewModel.cartId

        LaunchedEffect(true) {
            /*if(cartQueryState.value.isLoaded.not()) {
                cartViewModel.cartQuery(cartId)
            }*/
            cartViewModel.cartQuery(cartId)

        }



        if(cartQueryState.value.isLoaded) {
            CartList(cartQueryState.value.success)
        } else {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text(text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center)
            }
        }
    }


    // Comment,
    // On click of cart item, we will navigate to product detail screen
    // On Click on increment or decrement, we will update the cart
    // Whole cart items will be added into new Cart with updated quantities.
    // On click of increment, we will increment the quantity of product
    // On click of decrement, we will decrement the quantity of product
    // Comment,

    @Composable
    fun CartList(cartItems: List<UserCartUiData> = emptyList()) {
        val onCartItemClicked: (UserCartUiData) -> Unit = {
            printLog("Cart Item Clicked")
        }
        LazyColumn {
            // LazyColumn is a vertically scrolling list
            items(cartItems.size) {
                CartItem(
                    cartUiData = cartItems[it],
                    onCartItemClicked = {},
                    onIncrement = {},
                    onDecrement = {})
            }

        }

    }
    }