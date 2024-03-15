package com.ns.shopify.presentation.screen.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.ns.shopify.presentation.componets.DefaultBackArrow
import com.ns.shopify.presentation.screen.cart.CartViewModel

actual class CheckoutWebPageScreen actual constructor(val checkoutWebUrl: String): Screen {

    @Composable
    override fun Content() {

        val navigation = LocalNavigator.current

        val cartViewModel = getScreenModel<CartViewModel>()
        val cartBuyerIdentityState = cartViewModel.cartBuyerIdentityUpdateState.collectAsState()

        val onBackPress: () -> Unit = {
            cartViewModel.cartBuyerCancel()
            navigation?.pop()
        }

        val paymentStatusListener: (statusCode: Int, msg: String)->Unit = {statusCode, msg ->

        }

        val onRefreshWebPage: () -> Unit = {
            cartViewModel.cartBuyerIdentityUpdate()
        }


        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row {
                        // Back Arrow
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .padding(top = 10.dp, start = 10.dp)
                        ) {
                            DefaultBackArrow {
                                onBackPress()
                            }
                        }

                        // Page Title
                        Text(
                            text = "Checkout ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    // Refresh Webpage Button
                    Button(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        onClick = {
                            onRefreshWebPage()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(
                            text = "Refresh",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W800,
                            textAlign = TextAlign.Center
                        )
                    }

                }

            },
            bottomBar = {

                Divider(thickness = 1.dp, color = Color.Gray)
            },
            content = {
                Box(modifier = Modifier.fillMaxSize().padding(it)) {
                    CheckoutWebPageCompose(checkoutWebUrl, paymentStatusListener, onRefreshWebPage)
                }
            })



    }
}

@Composable
fun CheckoutWebPageCompose(checkoutWebUrl: String, paymentStatusListener:(statusCode: Int, msg: String)->Unit) {

}