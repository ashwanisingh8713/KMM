package com.ns.shopify.presentation.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.apollographql.apollo3.api.Optional
import com.app.printLog
import com.ns.shopify.data.utils.amountFormatter
import com.ns.shopify.presentation.screen.checkout.CheckoutScreen
import com.ns.shopify.presentation.settings.SettingsViewModel
import com.ns.shopify.type.CartLineUpdateInput
import com.ns.shopify.type.CurrencyCode
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 18,December,2023.
 */
class CartScreen(private val cartViewModel: CartViewModel) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val settingsViewModel = getScreenModel<SettingsViewModel>()
        val cartQueryState = cartViewModel.cartQueryState.collectAsState()

        val cartId = settingsViewModel.cartId

        val navigation = LocalNavigator.current

        LaunchedEffect(true) {
            cartViewModel.cartQuery(cartId)
        }

        val onDecrement: (UserCartUiData) -> Unit = {
            val input =
                CartLineUpdateInput(id = it.lineId, quantity = Optional.Present((it.quantity - 1)))
            cartViewModel.cartUpdate(cartId, input)
        }

        val onIncrement: (UserCartUiData) -> Unit = {
            val input =
                CartLineUpdateInput(id = it.lineId, quantity = Optional.Present((it.quantity + 1)))
            cartViewModel.cartUpdate(cartId, input)
        }

        // On click of place order, we will update the buyer identity
        val onPlaceOrderClicked:() -> Unit = {
//            cartViewModel.cartBuyerIdentityUpdate()
            navigation?.push(CheckoutScreen())

        }

        if (cartQueryState.value.isLoaded) {
            Scaffold(topBar = {},
                bottomBar = {
                    BottomBarUIs(onPlaceOrderClicked, cartQueryState.value)
                },
                content = {
                    if(cartQueryState.value.totalAmount == 0.0) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Your cart is empty",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize().padding(it)) {
                            CartList(
                                cartQueryState.value.success,
                                onIncrement = onIncrement,
                                onDecrement = onDecrement
                            )
                        }
                    }
                }
            )


        } else if(cartQueryState.value.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }  else if(cartQueryState.value.error.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = cartQueryState.value.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }


    @Composable
    fun BottomBarUIs(onPlaceOrderClicked:()->Unit, cartQueryState: CartScreenStateMapper) {
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 85.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.End

            ) {
                Text(
                    text = "Product Amount :",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 2.dp),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = amountFormatter(
                        CurrencyCode.ALL,
                        cartQueryState.subTotalAmount
                    ),
                    modifier = Modifier.fillMaxWidth(0.4f)
                        .padding(start = 16.dp, top = 10.dp, bottom = 2.dp),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.End

            ) {
                Text(
                    text = "Taxes Amount :",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 2.dp),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = amountFormatter(
                        CurrencyCode.ALL,
                        cartQueryState.taxAmount
                    ),
                    modifier = Modifier.fillMaxWidth(0.4f)
                        .padding(start = 16.dp, top = 10.dp, bottom = 2.dp),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.End

            ) {
                Text(
                    text = "Total Amount :",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(0.4f)
                        .padding(start = 16.dp, top = 8.dp, bottom = 2.dp),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = amountFormatter(
                        cartQueryState.currencyCode,
                        cartQueryState.totalAmount
                    ),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 10.dp, bottom = 2.dp),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.Center

            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(top = 3.dp, bottom = 8.dp)
                        .height(45.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    onClick = {
                        onPlaceOrderClicked()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = "Place Order", fontSize = 16.sp)
                }
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
    fun CartList(
        cartItems: List<UserCartUiData> = emptyList(),
        onIncrement: (UserCartUiData) -> Unit,
        onDecrement: (UserCartUiData) -> Unit
    ) {
        val onCartItemClicked: (UserCartUiData) -> Unit = {
            printLog("Cart Item Clicked")
        }
        LazyColumn {
            // LazyColumn is a vertically scrolling list
            items(cartItems.size) {
                CartItem(
                    cartUiData = cartItems[it],
                    onCartItemClicked = onCartItemClicked,
                    onIncrement = onIncrement,
                    onDecrement = onDecrement
                )
            }

        }

    }


}