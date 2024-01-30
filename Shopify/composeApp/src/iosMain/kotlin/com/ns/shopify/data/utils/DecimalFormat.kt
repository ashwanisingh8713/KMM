package com.ns.shopify.data.utils

/**
 * Created by Ashwani Kumar Singh on 29,January,2024.
 */
import com.ns.shopify.type.CurrencyCode
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
    actual fun amountFormatter(currencyCode: CurrencyCode, amount: Double): String {
        val formatter = NSNumberFormatter()
        formatter.minimumFractionDigits = 2u
        formatter.maximumFractionDigits = 2u
        formatter.numberStyle = 1u //Decimal
        if(currencyCode == CurrencyCode.ALL) {
            return formatter.stringFromNumber(NSNumber(amount))!!
        }
        return "$currencyCode ${formatter.stringFromNumber(NSNumber(amount))!!}"
    }
