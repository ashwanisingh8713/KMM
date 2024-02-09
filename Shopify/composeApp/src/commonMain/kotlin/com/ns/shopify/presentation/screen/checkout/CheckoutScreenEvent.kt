package com.ns.shopify.presentation.screen.checkout

/**
 * Created by Ashwani Kumar Singh on 08,February,2024.
 */

sealed class CheckoutEvent{
    data object CheckoutCreateEvent: CheckoutEvent()
    data object CheckoutCustomerAssociateEvent: CheckoutEvent()
    data object CheckoutShippingLineUpdateEvent: CheckoutEvent()
    data object CheckoutShippingAddressUpdateEvent: CheckoutEvent()
    data object CheckoutCompleteWithCreditCardEvent: CheckoutEvent()


}





