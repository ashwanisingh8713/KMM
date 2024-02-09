package com.ns.shopify.data.utils

import com.apollographql.apollo3.api.Optional
import com.ns.shopify.type.CartBuyerIdentityInput
import com.ns.shopify.type.CheckoutBuyerIdentityInput
import com.ns.shopify.type.CountryCode
import com.ns.shopify.type.DeliveryAddressInput
import com.ns.shopify.type.MailingAddressInput

/**
 * Created by Ashwani Kumar Singh on 08,February,2024.
 */

fun buyerCountry() = CountryCode.IN

fun cartBuyerIdentityInput(
    email: String,
    phone: String,
    countryCode: CountryCode,
    customerAccessToken: String,
    deliveryAddressInput: DeliveryAddressInput
): CartBuyerIdentityInput {
    return CartBuyerIdentityInput(
        email = Optional.present(email),
        phone = Optional.present(phone),
        countryCode = Optional.present(countryCode),
        customerAccessToken = Optional.present(customerAccessToken),
        deliveryAddressPreferences = Optional.present(listOf(deliveryAddressInput))
    )
}

fun deliveryAddressInput(addressId: String): DeliveryAddressInput {
    return DeliveryAddressInput(
        customerAddressId = Optional.present(addressId)
    )
}

fun checkoutBuyerIdentityInput() = CheckoutBuyerIdentityInput(
    countryCode = buyerCountry()
)


fun mailingAddressInput(address1: String, address2: String, city: String, company: String,
                        country: String, firstName: String, lastName: String, phone: String,
                        province: String, zip: String) = MailingAddressInput(
    address1 = Optional.present(address1),
    address2 = Optional.present(address2),
    city = Optional.present(city),
    company = Optional.present(company),
    country = Optional.present(country),
    firstName = Optional.present(firstName),
    lastName = Optional.present(lastName),
    phone = Optional.present(phone),
    province = Optional.present(province),
    zip = Optional.present(zip)
)