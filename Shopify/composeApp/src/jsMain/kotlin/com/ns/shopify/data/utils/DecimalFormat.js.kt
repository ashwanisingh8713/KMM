package com.ns.shopify.data.utils

import com.ns.shopify.type.CurrencyCode

/**
 * Created by Ashwani Kumar Singh on 29,January,2024.
 */
actual fun amountFormatter(
    currencyCode: CurrencyCode,
    amount: Double
): String {

    return "$currencyCode $amount"
}