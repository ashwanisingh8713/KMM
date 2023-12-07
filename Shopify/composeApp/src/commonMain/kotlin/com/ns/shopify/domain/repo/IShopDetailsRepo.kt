package com.ns.shopify.domain.repo

import com.ns.shopify.GetShopDetailsQuery

/**
 * Created by Ashwani Kumar Singh on 28,November,2023.
 */
interface IShopDetailsRepo {
    suspend fun getShopDetails(): GetShopDetailsQuery
}