package com.ns.shopify.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import com.apollographql.apollo3.network.http.ApolloClientAwarenessInterceptor
import com.apollographql.apollo3.network.http.DefaultHttpEngine
import com.apollographql.apollo3.network.http.HttpEngine
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.ns.shopify.data.repo.AddressModuleRepoImpl
import com.ns.shopify.data.repo.CartRepoImpl
import com.ns.shopify.data.repo.CategoryCollectionRepoImpl
import com.ns.shopify.data.repo.CheckoutRepoImpl
import com.ns.shopify.data.repo.LoginModuleRepoImpl
import com.ns.shopify.data.repo.ProductDetailRepoImpl
import com.ns.shopify.data.repo.ShopDetailsRepoImpl
import com.ns.shopify.domain.repo.IShopDetailsRepo
import com.ns.shopify.domain.repo.address.IAddressModuleRepo
import com.ns.shopify.domain.repo.cart.ICartRepo
import com.ns.shopify.domain.repo.checkout.ICheckoutRepo
import com.ns.shopify.domain.repo.product.ICategoryCollectionRepo
import com.ns.shopify.domain.repo.login.ILoginModuleRepo
import com.ns.shopify.domain.repo.product.IProductDetailRepo
import com.ns.shopify.domain.usecase.address.AddAddressUseCase
import com.ns.shopify.domain.usecase.ShopDetailsUseCase
import com.ns.shopify.domain.usecase.address.AddressListUseCase
import com.ns.shopify.domain.usecase.cart.AddMerchandiseUseCase
import com.ns.shopify.domain.usecase.cart.CartBuyerIdentityUpdateUseCase
import com.ns.shopify.domain.usecase.cart.CartCountUsecase
import com.ns.shopify.domain.usecase.cart.CartCreateUseCase
import com.ns.shopify.domain.usecase.cart.CartQueryUseCase
import com.ns.shopify.domain.usecase.cart.CartUpdateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutCompleteWithCreditCardUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutCreateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutCustomerAssociateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutShippingAddressUpdateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutShippingLineUpdateUseCase
import com.ns.shopify.domain.usecase.product.CategoryCollectionUsecase
import com.ns.shopify.domain.usecase.login.AccessTokenCreateUseCase
import com.ns.shopify.domain.usecase.login.CustomerCreateUseCase
import com.ns.shopify.domain.usecase.login.CustomerRecoverUseCase
import com.ns.shopify.domain.usecase.product.ProductDetailUsecase
import com.ns.shopify.presentation.screen.address.AddAddressViewModel
import com.ns.shopify.presentation.screen.address.AddressListViewModel
import com.ns.shopify.presentation.screen.cart.CartViewModel
import com.ns.shopify.presentation.screen.checkout.CheckoutViewModel
import com.ns.shopify.presentation.screen.forgot_password.ForgotPasswordViewModel
import com.ns.shopify.presentation.screen.home.HomeViewModel
import com.ns.shopify.presentation.screen.product_detail.ProductDetailViewModel
import com.ns.shopify.presentation.screen.sign_in.SignInViewModel
import com.ns.shopify.presentation.screen.sign_up.SignUpViewModel
import com.ns.shopify.presentation.settings.SettingsViewModel
import com.ns.shopify.presentation.viewmodel.AccessTokenCreateViewModel
import com.ns.shopify.presentation.viewmodel.CustomerRecoverViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
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
            platformModule(),
            ktorModule
        )
    }

val viewModelModule = module {
    single { SettingsViewModel(get(), get()) }
    factory { AccessTokenCreateViewModel(get()) }
    factory { CustomerRecoverViewModel(get()) }
    factory { CustomerRecoverViewModel(get()) }
    factory { HomeViewModel(get()) }
    factory { ProductDetailViewModel(get()) }
    factory { SignUpViewModel(get()) }
    factory { SignInViewModel(get(), get()) }
    factory { ForgotPasswordViewModel(get()) }
    single { CartViewModel(get(), get(), get(), get(), get(), get(), get()) }
    factory { AddAddressViewModel(get(), get()) }
    factory { AddressListViewModel(get(), get()) }
    factory{ CheckoutViewModel(get(), get(), get(), get(), get(), get()) }
}

val useCasesModule: Module = module {
    factory { ShopDetailsUseCase(get(), get()) }
    factory { AccessTokenCreateUseCase(get(), get()) }
    factory { CustomerCreateUseCase(get(), get()) }
    factory { CustomerRecoverUseCase(get(), get()) }
    factory { CategoryCollectionUsecase(get(), get()) }
    factory { ProductDetailUsecase(get(), get()) }
    factory { AddMerchandiseUseCase(get(), get()) }
    factory { CartCountUsecase(get(), get()) }
    factory { CartCreateUseCase(get(), get()) }
    factory { CartUpdateUseCase(get(), get()) }
    factory { CartQueryUseCase(get(), get()) }
    factory { CartBuyerIdentityUpdateUseCase(get(), get()) }
    factory { AddAddressUseCase(get(), get()) }
    factory { AddressListUseCase(get(), get()) }

    factory{ CheckoutCreateUseCase(get(), get()) }
    factory{ CheckoutCustomerAssociateUseCase(get(), get()) }
    factory{ CheckoutShippingLineUpdateUseCase(get(), get()) }
    factory{ CheckoutShippingAddressUpdateUseCase(get(), get()) }
    factory{ CheckoutCompleteWithCreditCardUseCase(get(), get()) }

}

val repositoryModule = module {
    factory<IShopDetailsRepo> { ShopDetailsRepoImpl(get()) }
    factory<ILoginModuleRepo> { LoginModuleRepoImpl(get()) }
    factory<ICategoryCollectionRepo> { CategoryCollectionRepoImpl(get()) }
    factory<IProductDetailRepo> { ProductDetailRepoImpl(get()) }
    factory<ICartRepo> { CartRepoImpl(get()) }
    factory<IAddressModuleRepo> { AddressModuleRepoImpl(get()) }
    factory<ICheckoutRepo> { CheckoutRepoImpl(get()) }
}




val apolloModule = module {




    single {
        ApolloClient.Builder()
            .serverUrl("https://quickstart-fe108883.myshopify.com/api/2023-10/graphql.json")
            .addHttpInterceptor(ApolloClientAwarenessInterceptor("com.ns.shopify", "1.0.0"))
            .addHttpHeader("X-Shopify-Storefront-Access-Token", "bcac730a26f46b1a15585da5b45f7d92")
//            .addInterceptor(OriginInterceptor())
            .addHttpHeader("Access-Control-Allow-Origin", "http://192.168.14.2:8080/")
            .addHttpHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS")
            .addHttpHeader("Access-Control-Headers", "*")
//            .addHttpHeader("Access-Control-Allow-Headers", "Content-Type,Authorization")
//            .addHttpHeader("Access-Control-Expose-Headers", "Content-Range,X-Content-Range")
            .addHttpHeader("Access-Control-Allow-Credentials", "true")
//            .addHttpHeader("Access-Control-Max-Age", "999999999999999999999")
            .webSocketReopenWhen { throwable, attempt ->
                delay(attempt * 1000)
                true
            }


            .build()
    }

}

val ktorModule = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }
}


val dispatcherModule = module {
    factory<CoroutineDispatcher> { Dispatchers.Default }
}


fun initKoin() = shopifyInitKoin {}



