package com.ns.shopify.presentation.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.apollographql.apollo3.api.Optional
import com.app.printLog
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.data.storage.UserDataAccess
import com.ns.shopify.data.utils.amountFormatter
import com.ns.shopify.presentation.screen.checkout.CheckoutWebPageScreen
import com.ns.shopify.type.CartLineUpdateInput
import com.ns.shopify.type.CurrencyCode
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 18,December,2023.
 */
class CartScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        UserDataAccess.refreshData(
            rememberCoroutineScope(), org.koin.compose.getKoin().get<CachingManager>())
        printLog("Ashwani : CartScreen : Content ")
        val cartViewModel = getScreenModel<CartViewModel>()
        val cartQueryState = cartViewModel.cartQueryState.collectAsState()
        val cartBuyerIdentityState = cartViewModel.cartBuyerIdentityUpdateState.collectAsState()


        val navigation = LocalNavigator.current

        LaunchedEffect(key1 = UserDataAccess.cartId) {
            cartViewModel.cartQuery(UserDataAccess.cartId)
            printLog("Ashwani : CartScreen : LaunchedEffect : PageRefreshRequired")
        }

        val onDecrement: (UserCartUiData) -> Unit = {
            val input =
                CartLineUpdateInput(id = it.lineId, quantity = Optional.Present((it.quantity - 1)))
            cartViewModel.cartUpdate(UserDataAccess.cartId, input)
        }

        val onIncrement: (UserCartUiData) -> Unit = {
            val input =
                CartLineUpdateInput(id = it.lineId, quantity = Optional.Present((it.quantity + 1)))
            cartViewModel.cartUpdate(UserDataAccess.cartId, input)
        }

        // On click of place order.
        val onPlaceOrderClicked: () -> Unit = {
            // It updates the buyer identity in Checkout Web-Page URL
            // Once updated, it will trigger "isLoaded" and open the checkout web-page screen
            cartViewModel.cartBuyerIdentityUpdate(UserDataAccess.cartId)
            // It Opens new screen, which has implementation for `CheckoutCompleteWithCreditCardV2`
            // navigation?.push(CheckoutScreen())

        }


        if (cartQueryState.value.isLoaded) {
            printLog("Ashwani : CartScreen : Content : cartQueryState.value.isLoaded ")
            Scaffold(topBar = {},
                bottomBar = {
                    if (cartQueryState.value.totalAmount > 0.0) {
                        BottomBarUIs(onPlaceOrderClicked, cartQueryState.value)
                    }
                },
                content = {
                    if (cartQueryState.value.totalAmount == 0.0) {
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


        }
        else if (cartQueryState.value.isLoading) {
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
        }
        else if (cartQueryState.value.error.isNotEmpty()) {
            printLog("Ashwani : CartScreen : Content : cartQueryState.value.Error ")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                var cartError = cartQueryState.value.error
                if(cartError.contains("cartId of type ID")){
                    cartError = "Your cart is empty"
                } else {
                    cartError = "Error!! $cartError"
                }
                Text(
                    text = cartError,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Cart Buyer Identity Update State
        if (cartBuyerIdentityState.value.isLoaded) {
            printLog("Ashwani : CartScreen : Content : cartBuyerIdentityState.value.isLoaded ")
            LaunchedEffect(true) {
                val checkoutUrl =
                    cartBuyerIdentityState.value.success?.cartBuyerIdentityUpdate?.cart?.checkoutUrl
                checkoutUrl?.let {
                    navigation?.push(CheckoutWebPageScreen(checkoutUrl as String, UserDataAccess.cartId))
                }
            }
        }
        else if (cartBuyerIdentityState.value.error.isNotEmpty()) {
            printLog("Ashwani : CartScreen : Content : cartBuyerIdentityState.value.Error ")
            Dialog(onDismissRequest = {
            }) {
                Box(modifier = Modifier
                    .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .clip(shape = RoundedCornerShape(16.dp)),

                ) {
                    Column(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        var errorMsg = cartBuyerIdentityState.value.error
                        var errorBuildAnnotated = buildAnnotatedString {
                            append(errorMsg)
                        }
                        if(errorMsg.contains("customerAddressId")){
                            errorBuildAnnotated = buildAnnotatedString {
                                withStyle(style = SpanStyle(color=Color.Black, fontWeight = FontWeight.Bold)) {
                                    append("Error!! ")
                                }
                                append("Kindly select the delivery address from")
                                withStyle(style = SpanStyle(color=Color.Black, fontWeight = FontWeight.Bold)) {
                                    append(" User -> Address")
                                }
                                append(" section")
                                append("\n\n")

                                withStyle(style = SpanStyle(color=Color.Black, fontWeight = FontWeight.Bold)) {
                                    append("Note: ")
                                }
                                append("Selected address must have valid mobile number and email id.")
                            }

                        }

                        Text(
                            text = errorBuildAnnotated,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Left,
                            style = typography.bodyMedium,
                        )

                        Button(
                            modifier = Modifier
                                .padding(top = 3.dp, bottom = 8.dp)
                                .height(40.dp)
                                .width(80.dp)
                                .clip(RoundedCornerShape(5.dp)),

                            onClick = {
                                cartViewModel.cartBuyerCancel()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            ),
                        ) {
                            Text(text = "OK", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CartBuyerIndentityUpdate() {

}


@Composable
fun BottomBarUIs(onPlaceOrderClicked: () -> Unit, cartQueryState: CartScreenStateMapper) {
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


