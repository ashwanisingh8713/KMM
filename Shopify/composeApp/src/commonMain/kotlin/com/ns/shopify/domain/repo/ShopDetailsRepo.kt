package com.ns.shopify.domain.repo

import com.ns.shopify.domain.model.SampleModel

/**
 * Created by Ashwani Kumar Singh on 28,November,2023.
 */
interface ShopDetailsRepo {
    suspend fun getShopDetails(): SampleModel
}