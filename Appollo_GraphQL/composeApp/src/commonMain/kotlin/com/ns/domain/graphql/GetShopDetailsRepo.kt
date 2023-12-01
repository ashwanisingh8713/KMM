package com.ns.domain.graphql

import com.daniel_avila.domain.model.graphql.GetShopDetails

/**
 * Created by Ashwani Kumar Singh on 28,November,2023.
 */
interface GetShopDetailsRepo {
    suspend fun getShopDetails(): GetShopDetails
}