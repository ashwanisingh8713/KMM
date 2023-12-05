package com.ns.shopify.di

import com.apollographql.apollo3.ApolloClient
import com.ns.shopify.data.repo.LoginModuleRepoImpl
import com.ns.shopify.data.storage.TokenRepository
import com.ns.shopify.domain.repo.login.ILoginModuleRepo
import com.ns.shopify.domain.usecase.login.AccessTokenCreateUseCase
import com.ns.shopify.domain.usecase.login.CustomerCreateUseCase
import com.ns.shopify.domain.usecase.login.CustomerRecoverUseCase
import com.ns.shopify.presentation.settings.SettingsViewModel
import com.ns.shopify.presentation.viewmodel.AccessTokenCreateViewModel
import com.ns.shopify.presentation.viewmodel.CustomerRecoverViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * Created by Ashwani Kumar Singh on 02,December,2023.
 */
expect fun platformModule(): Module
fun shopifyInitKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCasesModule,
            repositoryModule,
            apolloModule,
            dispatcherModule,
            platformModule()
        )
    }

val viewModelModule = module {
    factory { SettingsViewModel(get(), get()) }
    factory { AccessTokenCreateViewModel(get()) }
    factory { CustomerRecoverViewModel(get()) }
    factory { CustomerRecoverViewModel(get()) }
}

val useCasesModule: Module = module {
    factory { AccessTokenCreateUseCase(get(), get()) }
    factory { CustomerCreateUseCase(get(), get()) }
    factory { CustomerRecoverUseCase(get(), get()) }
}

val repositoryModule = module {
    factory<ILoginModuleRepo> { LoginModuleRepoImpl(get()) }
}

val apolloModule = module {
    single {
        ApolloClient.Builder()
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



val dispatcherModule = module {
    factory { Dispatchers.Default }
}



fun initKoin() = shopifyInitKoin {}



