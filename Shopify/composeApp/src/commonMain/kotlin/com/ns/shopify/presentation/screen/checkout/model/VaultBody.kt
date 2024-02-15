package com.ns.shopify.presentation.screen.checkout.model

import kotlinx.serialization.Serializable

/**
 * Created by Ashwani Kumar Singh on 13,February,2024.
 */
@Serializable
data class VaultBody(
    val credit_card: CreditCard
)

@Serializable
data class CreditCard(
    val first_name: String,
    val last_name: String,
    val month: String,
    val number: String,
    val verification_value: String,
    val year: String
)