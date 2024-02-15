package com.ns.shopify.presentation.screen.checkout.model

import kotlinx.serialization.Serializable

/**
 * Created by Ashwani Kumar Singh on 15,February,2024.
 */

@Serializable
data class PaymentSessionIdBody (val id: String,
                                 val credit_card: PaymentCreditCard,
                                 val payment_processing_error_message: String,
                                 val next_action: NextAction,
                                 val unique_token: String,
                                val transaction: Transaction,
    )

@Serializable
data class PaymentCreditCard(val first_name: String, val last_name: String, val first_digits: String,
    val last_digits: String, val brand: String, val expiry_month: String,  val expiry_year: String)

@Serializable
data class NextAction(val redirect_url: String)

@Serializable
data class Transaction (val amount: String, val amount_in: String, val amount_out: String,
                        val amount_rounding: String, val authorization: String, val created_at: String,
                        val currency: String, val error_code: String, val gateway: String, val id: String,
                        val kind: String, val message: String, val status: String, val test: Boolean)