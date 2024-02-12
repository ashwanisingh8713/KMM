package com.ns.shopify.data.utils

import com.apollographql.apollo3.api.Optional
import com.ns.shopify.GetAddressQuery
import com.ns.shopify.data.storage.UserDataAccess
import com.ns.shopify.data.storage.UserDataAccess.Address_Delemeter
import com.ns.shopify.presentation.screen.cart.UserCartUiData
import com.ns.shopify.type.CartBuyerIdentityInput
import com.ns.shopify.type.CheckoutBuyerIdentityInput
import com.ns.shopify.type.CheckoutLineItemInput
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

fun fullAddressFormatting(addressNode: GetAddressQuery.Node): String {
    return "${addressNode.address1}" + // [0]
            "$Address_Delemeter${addressNode.address2}" + // [1]
            "$Address_Delemeter${addressNode.city}" + // [2]
            "$Address_Delemeter${addressNode.country}" + // [3]
            "$Address_Delemeter${addressNode.zip}" + // [4]
            "$Address_Delemeter${addressNode.province}" + // [5]
            "$Address_Delemeter${addressNode.phone}"
}

fun checkoutMailingAddressInput() : MailingAddressInput {
    val fullAddress = UserDataAccess.selectedFullAddress.split(Address_Delemeter)
    var address1 = fullAddress[0]
    var address2 = fullAddress[1]
    var city = fullAddress[2]
    var country = fullAddress[3]
    var zip = fullAddress[4]
    var province = fullAddress[5]
    var phone = "+91"+fullAddress[6]

    return MailingAddressInput(
        address1 = Optional.present(address1),
        address2 = Optional.present(address2),
        city = Optional.present(city),
        company = Optional.present(""),
        country = Optional.present(country),
        firstName = Optional.present("Ashwani"),
        lastName = Optional.present("Singh"),
        phone = Optional.present(phone),
        province = Optional.present(province),
        zip = Optional.present(zip)
    )
}

fun createCheckoutLineItemInput(quantity: Int, variantId: String): CheckoutLineItemInput {
    return CheckoutLineItemInput(
        quantity = quantity,
        variantId = variantId
    )
}

fun checkoutLineItems( cartLineItems: List<UserCartUiData>):List<CheckoutLineItemInput> {
    return cartLineItems.map { cartItem ->
        createCheckoutLineItemInput(cartItem.quantity, cartItem.productId)
    }
}