package com.ns.shopify.presentation.screen.checkout

import com.ns.shopify.CheckoutCreateMutation
import com.ns.shopify.presentation.screen.cart.UserCartUiData
import com.ns.shopify.type.CheckoutLineItemInput
import com.ns.shopify.type.CustomerAccessToken

/**
 * Created by Ashwani Kumar Singh on 08,February,2024.
 */

sealed class CheckoutEvent{

    data class CheckoutLineItemsEvent(val cartLineItems: List<UserCartUiData>): CheckoutEvent()
    data class CheckoutCreateEvent(val checkoutLineItems: List<CheckoutLineItemInput>): CheckoutEvent()
    data class CheckoutCustomerAssociateEvent(val checkoutId: String, val customerAccessToken: String): CheckoutEvent()
    data class CheckoutShippingLineUpdateEvent(val checkoutId: String, val shippingRateHandle: String): CheckoutEvent()
    data class CheckoutShippingAddressUpdateEvent(val checkoutId: String): CheckoutEvent()
    data class CheckoutCompleteWithCreditCardEvent(val checkoutId: String, val totalPrice: CheckoutCreateMutation.TotalPrice?): CheckoutEvent()


}








