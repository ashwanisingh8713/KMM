package com.ns.shopify.presentation.screen.checkout

import cafe.adriel.voyager.core.model.ScreenModel
import com.ns.shopify.adapter.CheckoutCustomerAssociateV2Mutation_ResponseAdapter
import com.ns.shopify.domain.usecase.checkout.CheckoutCompleteWithCreditCardUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutShippingAddressUpdateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutShippingLineUpdateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutCreateUseCase
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 08,February,2024.
 */
class CheckoutViewModel(private val checkoutCreate: CheckoutCreateUseCase,
                        private val checkoutCustomerAssociate: CheckoutCustomerAssociateV2Mutation_ResponseAdapter.CheckoutCustomerAssociateV2,
                        private val checkoutShippingLineUpdate: CheckoutShippingLineUpdateUseCase,
                        private val checkoutShippingAddressUpdateV2: CheckoutShippingAddressUpdateUseCase,
                        private val checkoutCompleteWithCreditCardV2: CheckoutCompleteWithCreditCardUseCase):ScreenModel, KoinComponent {



}