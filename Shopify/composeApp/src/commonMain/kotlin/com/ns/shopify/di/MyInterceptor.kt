package com.ns.shopify.di

import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow


/**
 * Created by Ashwani Kumar Singh on 28,February,2024.
 */


class AuthorizationInterceptor(val token: String) : ApolloInterceptor {

    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        return chain.proceed(request.newBuilder().addHttpHeader("Authorization", "Bearer $token").build())
    }
}

class OriginInterceptor() : ApolloInterceptor {

    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        return chain.proceed(request.newBuilder().addHttpHeader("Origin", "http://localhost:8082/").build())
    }
}