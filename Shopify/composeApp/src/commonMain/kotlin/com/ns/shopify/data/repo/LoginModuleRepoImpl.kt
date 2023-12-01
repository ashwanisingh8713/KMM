package com.ns.shopify.data.repo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.ns.shopify.LaunchListQuery
import com.ns.shopify.domain.model.SampleModel
import com.ns.shopify.domain.repo.login.ILoginModuleRepo

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
class LoginModuleRepoImpl(private val apolloClient: ApolloClient): ILoginModuleRepo {
    override suspend fun signUP(any: Any): SampleModel {
        val response = apolloClient.query(LaunchListQuery(Optional.present(""))).execute()
        val model = response.data?.launches?.launches?.filterNotNull().orEmpty()
        return SampleModel()
    }

    override suspend fun login(any: Any): SampleModel {
        val response = apolloClient.query(LaunchListQuery(Optional.present(""))).execute()
        val model = response.data?.launches?.launches?.filterNotNull().orEmpty()
        return SampleModel()
    }

    override suspend fun forgotPassword(email: String): SampleModel {
        val response = apolloClient.query(LaunchListQuery(Optional.present(""))).execute()
        val model = response.data?.launches?.launches?.filterNotNull().orEmpty()
        return SampleModel()
    }
}