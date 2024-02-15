package com.ns.shopify.di

import com.apollographql.apollo3.ApolloClient
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
import kotlinx.coroutines.CoroutineDispatcher
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
//            .serverUrl("https://c498b0-3.myshopify.com/api/2023-10/graphql.json")
//            .addHttpHeader("X-Shopify-Storefront-Access-Token", "6974ac476e8022a5916eca859872fcf3")
            /*.serverUrl("https://quickstart-fe108883.myshopify.com/api/2023-10/graphql.json")
            .addHttpHeader("X-Shopify-Storefront-Access-Token", "bcac730a26f46b1a15585da5b45f7d92")*/
            .serverUrl("https://260029-2.myshopify.com/api/2023-10/graphql.json")
            .addHttpHeader("X-Shopify-Storefront-Access-Token", "5881ded5c88e92bc2d3a0895aedb8f80")
//            .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .webSocketReopenWhen { throwable, attempt ->
                delay(attempt * 1000)
                true
            }
            .build()
    }

}


val dispatcherModule = module {
    factory<CoroutineDispatcher> { Dispatchers.Default }
}


fun initKoin() = shopifyInitKoin {}



