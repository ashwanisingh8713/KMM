package com.ns.shopify.presentation.screen.cart

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.apollographql.apollo3.api.Optional
import com.app.printLog
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.domain.usecase.cart.AddMerchandiseUseCase
import com.ns.shopify.domain.usecase.cart.CartCountUsecase
import com.ns.shopify.domain.usecase.cart.CartCreateUseCase
import com.ns.shopify.domain.usecase.cart.CartQueryUseCase
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
class CartViewModel(/*private val cachingManager: CachingManager,*/
                    private val cartCreateUseCase: CartCreateUseCase,
                    private val addMerchandiseUseCase: AddMerchandiseUseCase,
                    private val cartCountUseCase: CartCountUsecase,
                    private val cartQueryUseCase: CartQueryUseCase): ScreenModel, KoinComponent {

    private val _cartCreateState = MutableStateFlow(CreateCartState())
    val cartCreateState = _cartCreateState.asStateFlow()

    private val _addMerchandiseState = MutableStateFlow(AddMerchandiseState())
    val addMerchandiseState = _addMerchandiseState.asStateFlow()

    private val _cartCountState = MutableStateFlow(CartCountState())
    val cartCountState = _cartCountState.asStateFlow()

    private val _cartQueryState = MutableStateFlow(CartScreenStateMapper())
    val cartQueryState = _cartQueryState.asStateFlow()

    fun addToCart(merchandiseId: String, quantity : Optional.Present<Int>, cartId: String) {
        printLog("Add to Cart Merchandise Id is $merchandiseId")
        coroutineScope.launch {
            printLog("Cart Id is $cartId")
            if(cartId.isEmpty() || cartId == "null") {
                cartCreate(merchandiseId, quantity)
            } else {
                addMerchandise(cartId, CartLineInput(merchandiseId = merchandiseId, quantity = quantity))
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
                val cartInput = CartInput(lines = Optional.Present(listOf(CartLineInput(merchandiseId = merchandiseId, quantity = quantity))))
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
//                                val checkoutUrl = it1.checkoutUrl as String
//                                val cartId = cart.id
//                                cachingManager.saveCartId(cartId)
//                                cachingManager.saveCheckoutUrl(checkoutUrl)
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

    fun cartQuery(cartId: String) {
        coroutineScope.launch {
            cartQueryUseCase(cartId)
                .onSuccess { response->
                    val error = response.errors
                    if(error != null && error.isNotEmpty()) {
                        _cartQueryState.update { it.copy(isLoading = false, error = error[0].message) }
                    } else {
//                        val totalQuantity = response.data?.cart?.totalQuantity
//                        val checkoutUrl = response.data?.cart?.checkoutUrl
//                        cachingManager.saveCheckoutUrl(checkoutUrl as String)
//                        cachingManager.saveCartCount(totalQuantity ?: 0 )
                        val cartData = response.data!!.cart
                        cartData?.let {
                            val lines = cartData.lines
                            val edges = lines.edges
                            val list = mutableListOf<UserCartUiData>()
                            edges.forEach {
                                val node = it.node
                                val merchandise = node.merchandise
                                val onProductVariant = merchandise.onProductVariant
                                val title = onProductVariant?.product?.title ?: ""
                                val quantity = node.quantity
                                val amount = onProductVariant?.price?.amount ?: ""
                                val imageUrl = onProductVariant?.image?.url ?: ""
                                val productId = onProductVariant?.id ?: ""
                                val userCartUiData = UserCartUiData(productId = productId, price = amount as String,
                                    quantity = quantity, title = title, imageUrl = imageUrl as String)
                                list.add(userCartUiData)
                            }
                            _cartQueryState.update { it.copy(isLoading = false, success = list, isLoaded = true) }
                        }
                    }

                }
                .onFailure {it1->
                    _cartQueryState.update { it.copy(isLoading = false, error = it1.message ?: "Error Occurred!") }
                }
        }
    }



}