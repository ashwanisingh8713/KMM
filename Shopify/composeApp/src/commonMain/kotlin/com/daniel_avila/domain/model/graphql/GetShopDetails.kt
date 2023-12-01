package com.daniel_avila.domain.model.graphql

/**
 * Created by Ashwani Kumar Singh on 28,November,2023.
 */
data class GetShopDetails(
    val `data`: Data
)

data class Data(
    val shop: Shop
)

data class Shop(
    val name: String,
    val paymentSettings: PaymentSettings,
    val primaryDomain: PrimaryDomain
)

data class PaymentSettings(
    val acceptedCardBrands: List<Any>,
    val currencyCode: String,
    val enabledPresentmentCurrencies: List<String>
)

data class PrimaryDomain(
    val host: String,
    val url: String
)