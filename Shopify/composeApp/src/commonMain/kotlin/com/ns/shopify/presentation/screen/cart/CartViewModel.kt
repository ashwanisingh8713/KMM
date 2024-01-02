package com.ns.shopify.presentation.screen.cart

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.apollographql.apollo3.api.Optional
import com.app.printLog
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.domain.usecase.cart.AddMerchandiseUseCase
import com.ns.shopify.domain.usecase.cart.CartCountUsecase
import com.ns.shopify.domain.usecase.cart.CartCreateUseCase
import com.ns.shopify.type.CartInput
import com.ns.shopify.type.CartLineInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 28,December,2023.
 */
class CartViewModel(private val cachingManager: CachingManager, private val cartCreateUseCase: CartCreateUseCase,
    private val addMerchandiseUseCase: AddMerchandiseUseCase,
    private val cartCountUseCase: CartCountUsecase): ScreenModel, KoinComponent {

    private val _cartCreateState = MutableStateFlow(CreateCartState())
    private val cartCreateState = _cartCreateState.asStateFlow()

    private val _addMerchandiseState = MutableStateFlow(AddMerchandiseState())
    private val addMerchandiseState = _addMerchandiseState.asStateFlow()

    private val _cartCountState = MutableStateFlow(CartCountState())
    private val cartCountState = _cartCountState.asStateFlow()

    fun addToCart(merchandiseId: String, quantity : Optional.Present<Int>) {
        printLog("Add to Cart Merchandise Id is $merchandiseId")
        coroutineScope.launch {
            val checkoutUrl = cachingManager.getCheckoutUrl().first()
            printLog("Checkout Url is $checkoutUrl")
            val cartId = cachingManager.getCartId().first()
            if(cartId.isNotEmpty()) {
                addMerchandise(cartId, CartLineInput(merchandiseId = merchandiseId, quantity = quantity))
            } else {
                cartCreate(merchandiseId, quantity)
            }
        }
    }

    /**
     * This method is used to create cart
     */
    private fun cartCreate(merchandiseId: String, quantity : Optional.Present<Int>) {
//        val quantity : Optional.Present<Int> = Optional.Present(1)
//        val cartLineInput = CartLineInput(merchandiseId = merchandiseId, quantity = quantity)
//        val lines : Optional.Present<List<CartLineInput>> = Optional.Present(listOf(cartLineInput))
//        val cartInput = CartInput(lines = lines)
            coroutineScope.launch {
                val cartInput: CartInput = CartInput(lines = Optional.Present(listOf(CartLineInput(merchandiseId = merchandiseId, quantity = quantity))))
                cartCreateUseCase(cartInput)
                    .onSuccess {
                        val error = it.errors
                        if(error != null && error.isNotEmpty()) {
                            _cartCreateState.update { it.copy(isLoading = false, error = error[0].message) }
                        } else {
                            val cart = it.data?.cartCreate?.cart

                            _cartCreateState.update {
                                it.copy(isLoading = false, success = cart, isLoaded = true)
                            }
                            cart?.let {it1->
                                _cartCreateState.update { it.copy(success = cart, isLoaded = true, isLoading = false) }
                                val checkoutUrl = it1.checkoutUrl as String
                                val cartId = cart.id
                                cachingManager.saveCartId(cartId)
                                cachingManager.saveCheckoutUrl(checkoutUrl)
                            }
                        }

                    }
                    .onFailure {it1->
                        _cartCreateState.update { it.copy(isLoading = false, error = it1.message) }
                    }
            }
    }

    /**
     * This method is used to add merchandise in cart
     */
    private fun addMerchandise(cartId: String, cartLineInput: CartLineInput) {
        coroutineScope.launch {
            val pair = Pair(cartId, listOf(cartLineInput))
            addMerchandiseUseCase(pair)
                .onSuccess {
                    val error = it.errors
                    if(error != null && error.isNotEmpty()) {
                        _addMerchandiseState.update { it.copy(isLoading = false, error = error[0].message) }

                    } else {
                        val cart = it.data?.cartLinesAdd?.cart
                        _addMerchandiseState.update {
                            it.copy(isLoading = false, success = cart, isLoaded = true)
                        }
                        cachingManager.saveCheckoutUrl(cart?.checkoutUrl as String)
                        cachingManager.saveCartCount(cart.totalQuantity ?: 0)
                    }

                }
                .onFailure {it1->
                    _cartCreateState.update { it.copy(isLoading = false, error = it1.message) }
                }
        }
    }


    /**
     * This method is used to get the cart count
     */
    fun cartCount(cartId: String) {
        coroutineScope.launch {
            cartCountUseCase(cartId)
                .onSuccess {
                    val error = it.errors
                    if(error != null && error.isNotEmpty()) {
                        _cartCountState.update { it.copy(isLoading = false, error = error[0].message) }
                    } else {
                        val totalQuantity = it.data?.cart?.totalQuantity
                        _cartCountState.update { it.copy(isLoading = false, count = totalQuantity ?: 0) }
                    }

                }
                .onFailure {it1->
                    _cartCreateState.update { it.copy(isLoading = false, error = it1.message) }
                }
        }
    }

}