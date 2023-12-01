package com.ns.shopify.data.network

import com.apollographql.apollo3.ApolloClient
import com.ns.shopify.data.storage.TokenRepository
import kotlinx.coroutines.delay

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
class RemoteService {

    fun makeApolloClient() {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .addHttpHeader("Authorization", "${TokenRepository.getToken()}")
            .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .webSocketReopenWhen { throwable, attempt ->
                delay(attempt * 1000)
                true
            }
            .build()
    }

}