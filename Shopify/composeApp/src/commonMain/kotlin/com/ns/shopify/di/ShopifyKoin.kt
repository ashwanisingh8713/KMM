package com.ns.shopify.di

import com.apollographql.apollo3.ApolloClient
import com.ns.shopify.data.repo.AddressModuleRepoImpl
import com.ns.shopify.data.repo.CartRepoImpl
import com.ns.shopify.data.repo.CategoryCollectionRepoImpl
import com.ns.shopify.data.repo.LoginModuleRepoImpl
import com.ns.shopify.data.repo.ProductDetailRepoImpl
import com.ns.shopify.data.repo.ShopDetailsRepoImpl
import com.ns.shopify.domain.repo.IShopDetailsRepo
import com.ns.shopify.domain.repo.address.IAddressModuleRepo
import com.ns.shopify.domain.repo.cart.ICartRepo
import com.ns.shopify.domain.repo.product.ICategoryCollectionRepo
import com.ns.shopify.domain.repo.login.ILoginModuleRepo
import com.ns.shopify.domain.repo.product.IProductDetailRepo
import com.ns.shopify.domain.usecase.address.AddAddressUseCase
import com.ns.shopify.domain.usecase.ShopDetailsUseCase
import com.ns.shopify.domain.usecase.cart.AddMerchandiseUseCase
import com.ns.shopify.domain.usecase.cart.CartCountUsecase
import com.ns.shopify.domain.usecase.cart.CartCreateUseCase
import com.ns.shopify.domain.usecase.cart.CartQueryUseCase
import com.ns.shopify.domain.usecase.cart.CartUpdateUseCase
import com.ns.shopify.domain.usecase.product.CategoryCollectionUsecase
import com.ns.shopify.domain.usecase.login.AccessTokenCreateUseCase
import com.ns.shopify.domain.usecase.login.CustomerCreateUseCase
import com.ns.shopify.domain.usecase.login.CustomerRecoverUseCase
import com.ns.shopify.domain.usecase.product.ProductDetailUsecase
import com.ns.shopify.presentation.screen.address.AddressViewModel
import com.ns.shopify.presentation.screen.cart.CartViewModel
import com.ns.shopify.presentation.screen.forgot_password.ForgotPasswordViewModel
import com.ns.shopify.presentation.screen.home.HomeViewModel
import com.ns.shopify.presentation.screen.product_detail.ProductDetailViewModel
import com.ns.shopify.presentation.screen.sign_in.SignInViewModel
import com.ns.shopify.presentation.screen.sign_up.SignUpViewModel
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
    single { SettingsViewModel(get(), get()) }
    factory { AccessTokenCreateViewModel(get()) }
    factory { CustomerRecoverViewModel(get()) }
    factory { CustomerRecoverViewModel(get()) }
    factory { HomeViewModel(get()) }
    factory { ProductDetailViewModel(get()) }
    factory { SignUpViewModel(get()) }
    factory { SignInViewModel(get()) }
    factory { ForgotPasswordViewModel(get()) }
    single { CartViewModel(get(), get(), get(), get(), get(), get()) }
    factory { AddressViewModel(get()) }
}

val useCasesModule: Module = module {
    factory { ShopDetailsUseCase(get(), get()) }
    factory { AccessTokenCreateUseCase(get(), get()) }
    factory { CustomerCreateUseCase(get(), get()) }
    factory { CustomerRecoverUseCase(get(), get()) }
    factory { CategoryCollectionUsecase(get(), get()) }
    factory { ProductDetailUsecase(get(), get()) }
    factory {AddMerchandiseUseCase(get(), get())}
    factory {CartCountUsecase(get(), get())}
    factory {CartCreateUseCase(get(), get())}
    factory {CartUpdateUseCase(get(), get())}
    factory { CartQueryUseCase(get(), get()) }
    factory { AddAddressUseCase(get(), get()) }
}

val repositoryModule = module {
    factory<IShopDetailsRepo> { ShopDetailsRepoImpl(get()) }
    factory<ILoginModuleRepo> { LoginModuleRepoImpl(get()) }
    factory<ICategoryCollectionRepo> { CategoryCollectionRepoImpl(get()) }
    factory<IProductDetailRepo> { ProductDetailRepoImpl(get()) }
    factory<ICartRepo> { CartRepoImpl(get()) }
    factory<IAddressModuleRepo> { AddressModuleRepoImpl(get()) }
}

val apolloModule = module {
    single {
        ApolloClient.Builder()
//            .serverUrl("https://c498b0-3.myshopify.com/api/2023-10/graphql.json")
//            .addHttpHeader("X-Shopify-Storefront-Access-Token", "6974ac476e8022a5916eca859872fcf3")
            .serverUrl("https://quickstart-fe108883.myshopify.com/api/2023-10/graphql.json")
            .addHttpHeader("X-Shopify-Storefront-Access-Token", "7286faf7ce6313246142ef05ae20f844")
//            .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
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



