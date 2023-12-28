package com.ns.shopify.domain.repo.address

import com.ns.shopify.CustomerAddressCreateMutation
import com.ns.shopify.type.MailingAddressInput

interface IAddressModuleRepo {

    //For Add Address
    suspend fun createAddress(input : MailingAddressInput) : CustomerAddressCreateMutation.CustomerAddressCreate
}