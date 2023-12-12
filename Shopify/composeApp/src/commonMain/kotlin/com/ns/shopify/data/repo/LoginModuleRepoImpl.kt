package com.ns.shopify.data.repo

import com.apollographql.apollo3.ApolloClient
import com.ns.shopify.CreateCustomerAccountMutation
import com.ns.shopify.LoginMutation
import com.ns.shopify.RecoverCustomerAccountMutation
import com.ns.shopify.domain.repo.login.ILoginModuleRepo
import com.ns.shopify.type.CustomerAccessTokenCreateInput
import com.ns.shopify.type.CustomerCreateInput

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
class LoginModuleRepoImpl(private val apolloClient: ApolloClient): ILoginModuleRepo {
    override suspend fun signUP(input: CustomerCreateInput):  CreateCustomerAccountMutation.CustomerCreate {
        /*val input = CustomerCreateInput(
            acceptsMarketing = null,
            email = "",
            firstName = "",
            lastName = "",
            password = "",
            phone = null,
            tags = null,
            verifiedEmail = null
        )*/
        val response = apolloClient.mutation(CreateCustomerAccountMutation(input)).execute()
        val customerCreate = response.data?.customerCreate
//        val userError = customerCreate?.customerUserErrors
//        val customer = customerCreate?.customer

        return customerCreate!!
    }

    override suspend fun login(input: CustomerAccessTokenCreateInput): LoginMutation.CustomerAccessTokenCreate {
        val response = apolloClient.mutation(LoginMutation(input)).execute()
        return response.data?.customerAccessTokenCreate!!
    }

    override suspend fun forgotPassword(email: String): RecoverCustomerAccountMutation.CustomerRecover {
        val response = apolloClient.mutation(RecoverCustomerAccountMutation(email)).execute()
        return response.data?.customerRecover!!
    }
}