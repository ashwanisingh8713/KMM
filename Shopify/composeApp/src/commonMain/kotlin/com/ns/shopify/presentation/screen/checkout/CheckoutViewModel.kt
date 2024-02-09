package com.ns.shopify.presentation.screen.checkout

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.apollographql.apollo3.api.Optional
import com.ns.shopify.adapter.CheckoutCustomerAssociateV2Mutation_ResponseAdapter
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.data.utils.buyerCountry
import com.ns.shopify.data.utils.checkoutBuyerIdentityInput
import com.ns.shopify.domain.usecase.checkout.CheckoutCompleteWithCreditCardUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutShippingAddressUpdateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutShippingLineUpdateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutCreateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutCustomerAssociateUseCase
import com.ns.shopify.type.CartBuyerIdentityInput
import com.ns.shopify.type.CheckoutCreateInput
import com.ns.shopify.type.CountryCode
import com.ns.shopify.type.CreditCardPaymentInputV2
import com.ns.shopify.type.DeliveryAddressInput
import com.ns.shopify.type.MailingAddressInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 08,February,2024.
 */
class CheckoutViewModel(
    private val checkoutCreateUC: CheckoutCreateUseCase,
    private val checkoutCustomerAssociateUC: CheckoutCustomerAssociateUseCase,
    private val checkoutShippingLineUpdateUC: CheckoutShippingLineUpdateUseCase,
    private val checkoutShippingAddressUpdateUC: CheckoutShippingAddressUpdateUseCase,
    private val checkoutCompleteWithCreditCardUC: CheckoutCompleteWithCreditCardUseCase,
    private val cachingManager: CachingManager
) : ScreenModel, KoinComponent {

    private val _checkoutCreateState = MutableStateFlow(CheckoutCreateState())
    val checkoutCreateState = _checkoutCreateState.asStateFlow()

    private val _checkoutCustomerAssociateState = MutableStateFlow(CheckoutCustomerAssociateState())
    val checkoutCustomerAssociateState = _checkoutCustomerAssociateState.asStateFlow()

    private val _checkoutShippingLineUpdateState =
        MutableStateFlow(CheckoutShippingLineUpdateState())
    val checkoutShippingLineUpdateState = _checkoutShippingLineUpdateState.asStateFlow()

    private val _checkoutShippingAddressUpdateState =
        MutableStateFlow(CheckoutShippingAddressUpdateState())
    val checkoutShippingAddressUpdateState = _checkoutShippingAddressUpdateState.asStateFlow()

    private val _checkoutCompleteWithCreditCardState =
        MutableStateFlow(CheckoutCompleteWithCreditCardState())
    val checkoutCompleteWithCreditCardState = _checkoutCompleteWithCreditCardState.asStateFlow()

    fun checkoutEvent(event: CheckoutEvent) {
        when (event) {
            is CheckoutEvent.CheckoutCreateEvent -> checkoutCreate()
            is CheckoutEvent.CheckoutCustomerAssociateEvent -> checkoutCustomerAssociate()
            is CheckoutEvent.CheckoutShippingLineUpdateEvent -> checkoutShippingLineUpdate()
            is CheckoutEvent.CheckoutShippingAddressUpdateEvent -> checkoutShippingAddressUpdate()
            is CheckoutEvent.CheckoutCompleteWithCreditCardEvent -> checkoutCompleteWithCreditCard()
        }
    }

    // Step-1, Create a checkout ID with required details
    private fun checkoutCreate() {
        coroutineScope.launch {
            _checkoutCreateState.update { it.copy(isLoading = true) }


            combine(
                cachingManager.getCustomerEmail(), cachingManager.getCustomerPhone(),
                cachingManager.getCustomerAddressId(), cachingManager.getCartId(),
                cachingManager.getCustomerAccessToken()
            ) { email, phone, addressId, cartId, customerAccessToken ->

                val deliveryAddressInput = DeliveryAddressInput(
                    customerAddressId = Optional.present(addressId)
                )

                val buyerIdentity = CartBuyerIdentityInput(
                    email = Optional.present(email),
                    phone = Optional.present(phone),
                    countryCode = Optional.present(CountryCode.IN),
                    customerAccessToken = Optional.present(customerAccessToken),
                    deliveryAddressPreferences = Optional.present(listOf(deliveryAddressInput))
                )

                val checkoutCreateInput = CheckoutCreateInput(
                    note = Optional.present("This is note given by user"),
                    customAttributes = Optional.present(listOf()),
                    email = Optional.present(email),
                    lineItems = Optional.present(listOf()),
                    shippingAddress = Optional.present(MailingAddressInput()),
                    buyerIdentity = Optional.present(checkoutBuyerIdentityInput()),
                    allowPartialAddresses = Optional.present(true)
                )

                checkoutCreateUC(checkoutCreateInput).onSuccess {

                }.onFailure {

                }
            }
        }
    }

    // Step-2, Associate a customer to the checkout
    private fun checkoutCustomerAssociate() {
        coroutineScope.launch {
            _checkoutCustomerAssociateState.update { it.copy(isLoading = true) }
            val checkoutId = ""
            val customerAccessToken = ""
            val pair = Pair(checkoutId, customerAccessToken)
            checkoutCustomerAssociateUC(pair).onSuccess {

            }.onFailure {

            }
        }
    }


    // Step-3, Shipping rates handle
    // create shipping line and update the checkout
    private fun checkoutShippingLineUpdate() {
        coroutineScope.launch {
            _checkoutShippingLineUpdateState.update { it.copy(isLoading = true) }
            val checkoutId = ""
            val shippingRateHandle = ""
            val pair = Pair(checkoutId, shippingRateHandle)
            checkoutShippingLineUpdateUC(pair).onSuccess {

            }.onFailure {

            }
        }
    }


    // Step-4, Update the shipping address on the checkout
    private fun checkoutShippingAddressUpdate() {
        coroutineScope.launch {
            _checkoutShippingAddressUpdateState.update { it.copy(isLoading = true) }
            val checkoutId = ""
            val shippingAddress = MailingAddressInput()
            val pair = Pair(checkoutId, shippingAddress)
            checkoutShippingAddressUpdateUC(pair).onSuccess {

            }.onFailure {

            }
        }
    }


    // Step-5, Complete the checkout with a credit card
    private fun checkoutCompleteWithCreditCard() {
        /*coroutineScope.launch {
            _checkoutCompleteWithCreditCardState.update { it.copy(isLoading = true) }
            val checkoutId = ""
            val creditCartPaymentInput = CreditCardPaymentInputV2()
            val pair = Pair(checkoutId, creditCartPaymentInput)
            checkoutCompleteWithCreditCardUC(pair).onSuccess {

            }.onFailure {

            }
        }*/
    }



}