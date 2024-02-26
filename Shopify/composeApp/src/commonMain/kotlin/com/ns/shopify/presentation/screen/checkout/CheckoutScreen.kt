package com.ns.shopify.presentation.screen.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.ns.shopify.data.storage.UserDataAccess
import com.ns.shopify.presentation.componets.DefaultBackArrow
import com.ns.shopify.presentation.componets.Loading
import com.ns.shopify.presentation.screen.cart.CartViewModel

/**
 * Created by Ashwani Kumar Singh on 08,February,2024.
 */
class CheckoutScreen : Screen {
    @Composable
    override fun Content() {
        val cartViewModel = getScreenModel<CartViewModel>()
        val checkoutViewModel = getScreenModel<CheckoutViewModel>()

        val cartQueryState = cartViewModel.cartQueryState.collectAsState()
        val checkoutCreateState = checkoutViewModel.checkoutCreateState.collectAsState()
        val checkoutCustomerAssociateState = checkoutViewModel.checkoutCustomerAssociateState.collectAsState()
        val checkoutShippingLineUpdateState = checkoutViewModel.checkoutShippingLineUpdateState.collectAsState()
        val checkoutShippingAddressUpdateState = checkoutViewModel.checkoutShippingAddressUpdateState.collectAsState()
        val checkoutCompleteWithCreditCardState = checkoutViewModel.checkoutCompleteWithCreditCardState.collectAsState()

        // 1. Sending Checkout Line Items Event
        LaunchedEffect(true) {
            checkoutViewModel.checkoutEvent(CheckoutEvent.CheckoutLineItemsEvent(cartQueryState.value.success))
        }

        // 2. Sending Checkout Create Event
        if (checkoutViewModel.checkoutLineItemsState.value.isNotEmpty()) {
            LaunchedEffect(true) {
                checkoutViewModel.checkoutEvent(CheckoutEvent.CheckoutCreateEvent(checkoutViewModel.checkoutLineItemsState.value))
            }
        }

        // 3. Sending Checkout Customer Associate Event
        if (checkoutCreateState.value.checkoutId.isNotEmpty()) {
            LaunchedEffect(true) {
                checkoutViewModel.checkoutEvent(
                    CheckoutEvent.CheckoutCustomerAssociateEvent(
                        checkoutId = checkoutCreateState.value.checkoutId,
                        customerAccessToken = UserDataAccess.customerAccessToken
                    )
                )
            }
        }

        // 4. Sending Checkout Shipping Line Update Event
        if (checkoutCustomerAssociateState.value.checkoutId.isNotEmpty()) {
            LaunchedEffect(true) {
                checkoutViewModel.checkoutEvent(
                    CheckoutEvent.CheckoutShippingLineUpdateEvent(
                        checkoutId = checkoutCreateState.value.checkoutId,
                        shippingRateHandle = "Standard"
                    )
                )
            }
        }

        // 5. Sending Checkout Shipping Address Update Event
        if (checkoutShippingLineUpdateState.value.checkoutId.isNotEmpty()) {
            LaunchedEffect(true) {
                checkoutViewModel.checkoutEvent(
                    CheckoutEvent.CheckoutShippingAddressUpdateEvent(
                        checkoutShippingLineUpdateState.value.checkoutId
                    )
                )
            }
        }

        // 6. Sending Checkout Complete With Credit Card Event
        if (checkoutShippingAddressUpdateState.value.checkoutId.isNotEmpty()) {
            LaunchedEffect(true) {
                checkoutViewModel.checkoutEvent(
                    CheckoutEvent.CheckoutCompleteWithCreditCardEvent(
                        checkoutShippingAddressUpdateState.value.checkoutId,
                        checkoutCreateState.value.totalPrice
                    )
                )
            }
        }

        // 7. Sending VaultId request
        if (checkoutCompleteWithCreditCardState.value.checkoutId.isNotEmpty()) {
            LaunchedEffect(true) {
                checkoutViewModel.createVaultId()
            }
        }


        val navigation = LocalNavigator.current

        val onBackPress: () -> Unit = {
            navigation?.pop()
        }

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                        text = "Checkout Screen",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )


                }

            },
            bottomBar = {
                Divider(thickness = 1.dp, color = Color.Gray)
            },
            content = {
                Box(modifier = Modifier.fillMaxSize().padding(it)) {

                }
            })
    }

    @Composable
    fun checkoutLoading() {
        Loading()
    }

    @Composable
    fun CheckoutCreate() {

    }

    @Composable
    fun CheckoutCustomerAssociate() {

    }

    @Composable
    fun CheckoutShippingLineUpdate() {

    }

    @Composable
    fun CheckoutShippingAddressUpdate() {

    }


    @Composable
    fun CheckoutCompleteWithCreditCard() {

    }




}