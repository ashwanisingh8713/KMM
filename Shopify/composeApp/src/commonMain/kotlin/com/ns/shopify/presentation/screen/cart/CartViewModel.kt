package com.ns.shopify.presentation.screen.cart

import cafe.adriel.voyager.core.model.ScreenModel
import com.ns.shopify.domain.usecase.cart.AddMerchandiseUseCase
import com.ns.shopify.domain.usecase.cart.CartCountUsecase
import com.ns.shopify.domain.usecase.cart.CartCreateUseCase
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 28,December,2023.
 */
class CartViewModel(private val cartCreate: CartCreateUseCase,
    private val addMerchandise: AddMerchandiseUseCase,
    private val cartCount: CartCountUsecase): ScreenModel, KoinComponent {

    fun cartCreate() {

    }

    fun addMerchandise() {

    }

    fun cartCount() {

    }

}