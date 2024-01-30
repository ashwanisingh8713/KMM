package com.ns.shopify.data.utils

import com.ns.shopify.type.CurrencyCode

/**
 * Created by Ashwani Kumar Singh on 29,January,2024.
 */
    actual fun amountFormatter(currencyCode: CurrencyCode, amount: Double): String {
        val df = java.text.DecimalFormat()
        df.isGroupingUsed = false
        df.maximumFractionDigits = 2
        df.minimumFractionDigits = 2
        df.isDecimalSeparatorAlwaysShown = false
        if(currencyCode == CurrencyCode.ALL) {
            return df.format(amount)
        }
        return "$currencyCode ${df.format(amount)}"
    }