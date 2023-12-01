package com.rocketreserver.http

import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.delay



val apolloClient = ApolloClient.Builder()
    .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
    .addHttpHeader("Authorization", "${TokenRepository.getToken()}")
    .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
    .webSocketReopenWhen { throwable, attempt ->
        delay(attempt * 1000)
        true
    }
    .build()



