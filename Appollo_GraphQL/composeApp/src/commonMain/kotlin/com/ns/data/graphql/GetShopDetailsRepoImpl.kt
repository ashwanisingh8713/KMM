package com.ns.data.graphql

import com.daniel_avila.domain.model.graphql.GetShopDetails
import com.ns.domain.graphql.GetShopDetailsRepo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post

/**
 * Created by Ashwani Kumar Singh on 28,November,2023.
 */
class GetShopDetailsRepoImpl(private val endPoint: String,
                             private val httpClient: HttpClient
)/*: GetShopDetailsRepo {
    override suspend fun getShopDetails(): GetShopDetails {

        val mediaType = MediaType.parse("application/graphql")

        val request = httpClient.post(endPoint)


    }
}*/