package com.ns.shopify.presentation.screen.cart

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.apollographql.apollo3.api.Optional
import com.app.printLog
import com.ns.shopify.CartBuyerIdentityUpdateMutation
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.data.storage.UserDataAccess
import com.ns.shopify.data.utils.buyerCountry
import com.ns.shopify.data.utils.cartBuyerIdentityInput
import com.ns.shopify.data.utils.deliveryAddressInput
import com.ns.shopify.domain.usecase.cart.AddMerchandiseUseCase
import com.ns.shopify.domain.usecase.cart.CartBuyerIdentityUpdateUseCase
import com.ns.shopify.domain.usecase.cart.CartCountUsecase
import com.ns.shopify.domain.usecase.cart.CartCreateUseCase
import com.ns.shopify.domain.usecase.cart.CartQueryUseCase
import com.ns.shopify.domain.usecase.cart.CartUpdateUseCase
import com.ns.shopify.type.CartInput
import com.ns.shopify.type.CartLineInput
import com.ns.shopify.type.CartLineUpdateInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 28,December,2023.
 */
class CartViewModel(
    private val cartCreateUseCase: CartCreateUseCase,
    private val addMerchandiseUseCase: AddMerchandiseUseCase,
    private val cartCountUseCase: CartCountUsecase,
    private val cartQueryUseCase: CartQueryUseCase,
    private val cartUpdateUseCase: CartUpdateUseCase,
    private val cartBuyerIdentityUpdateUseCase: CartBuyerIdentityUpdateUseCase,
    private val cachingManager: CachingManager
) : ScreenModel, KoinComponent {

    private val _cartCreateState = MutableStateFlow(CreateCartState())
    val cartCreateState = _cartCreateState.asStateFlow()

    private val _addMerchandiseState = MutableStateFlow(AddMerchandiseState())
    val addMerchandiseState = _addMerchandiseState.asStateFlow()

    private val _cartCountState = MutableStateFlow(CartCountState())
    val cartCountState = _cartCountState.asStateFlow()

    private val _cartQueryState = MutableStateFlow(CartScreenStateMapper())
    val cartQueryState = _cartQueryState.asStateFlow()

    private val _cartBuyerIdentityUpdateState = MutableStateFlow(CartBuyerIdentityUpdateState())
    val cartBuyerIdentityUpdateState = _cartBuyerIdentityUpdateState.asStateFlow()


    fun clearCartId() {
        screenModelScope.launch {
            cachingManager.saveCartId("")
        }

    }
    fun refreshCartQueryState() {
        _cartQueryState.update {
            it.copy(
                success = emptyList(),
                isLoaded = false,
            )
        }
    }

    /**
     * This method is used to add merchandise in cart
     */
    fun addToCart(merchandiseId: String, quantity: Optional.Present<Int>, cartId: String) {
        printLog("Add to Cart Merchandise Id is $merchandiseId")
        screenModelScope.launch {
            printLog("Cart Id is $cartId")
            if (cartId.isEmpty() || cartId == "null") {
                cartCreate(merchandiseId, quantity)
            } else {
                addMerchandise(
                    cartId,
                    CartLineInput(merchandiseId = merchandiseId, quantity = quantity)
                )
            }
        }
    }

    /**
     * This method is used to create cart
     */
    private fun cartCreate(merchandiseId: String, quantity: Optional.Present<Int>) {
//        val quantity : Optional.Present<Int> = Optional.Present(1)
//        val cartLineInput = CartLineInput(merchandiseId = merchandiseId, quantity = quantity)
//        val lines : Optional.Present<List<CartLineInput>> = Optional.Present(listOf(cartLineInput))
//        val cartInput = CartInput(lines = lines)
        screenModelScope.launch {
            val cartInput = CartInput(
                lines = Optional.Present(
                    listOf(
                        CartLineInput(
                            merchandiseId = merchandiseId,
                            quantity = quantity
                        )
                    )
                )
            )
            cartCreateUseCase(cartInput)
                .onSuccess {
                    val error = it.errors
                    if (error != null && error.isNotEmpty()) {
                        _cartCreateState.update {
                            it.copy(
                                isLoading = false,
                                error = error[0].message
                            )
                        }
                    } else {
                        val cart = it.data?.cartCreate?.cart

                        _cartCreateState.update {
                            it.copy(isLoading = false, success = cart, isLoaded = true)
                        }
                        cart?.let { it1 ->

                            _cartCreateState.update {
                                it.copy(
                                    success = cart,
                                    isLoaded = true,
                                    isLoading = false
                                )
                            }

                            cart.let {
                                val checkoutUrl = cart.checkoutUrl
                                val totalQuantity = cart.totalQuantity
                                val latestCartId = cart.id
                                cachingManager.saveCartCount(totalQuantity)
                                cachingManager.saveCheckoutUrl(checkoutUrl as String)
                                cachingManager.saveCartId(latestCartId)
                            }

//                                val checkoutUrl = it1.checkoutUrl as String
//                                val cartId = cart.id
//                                cachingManager.saveCartId(cartId)
//                                cachingManager.saveCheckoutUrl(checkoutUrl)
                        }
                    }

                }
                .onFailure { it1 ->
                    _cartCreateState.update { it.copy(isLoading = false, error = it1.message) }
                }
        }
    }

    /**
     * This method is used to add merchandise in cart
     */
    private fun addMerchandise(cartId: String, cartLineInput: CartLineInput) {
        screenModelScope.launch {
            val pair = Pair(cartId, listOf(cartLineInput))
            addMerchandiseUseCase(pair)
                .onSuccess { it1 ->
                    val error = it1.errors
                    if (error != null && error.isNotEmpty()) {
                        _addMerchandiseState.update {
                            it.copy(
                                isLoading = false,
                                error = error[0].message
                            )
                        }
                    } else {
                        val cart = it1.data?.cartLinesAdd?.cart
                        _addMerchandiseState.update {
                            it.copy(isLoading = false, success = cart, isLoaded = true)
                        }
                        cart?.let {
                            val checkoutUrl = cart.checkoutUrl
                            val totalQuantity = cart.totalQuantity
                            val latestCartId = cart.id
                            cachingManager.saveCartCount(totalQuantity)
                            cachingManager.saveCheckoutUrl(checkoutUrl as String)
                            cachingManager.saveCartId(latestCartId)
                        }

                    }
                }
                .onFailure { it1 ->
                    _addMerchandiseState.update { it.copy(isLoading = false, error = it1.message) }
                }
        }
    }


    /**
     * This method is used to get the cart count
     */
    fun cartCount(cartId: String) {
        screenModelScope.launch {
            cartCountUseCase(cartId)
                .onSuccess {
                    val error = it.errors
                    if (error != null && error.isNotEmpty()) {
                        _cartCountState.update {
                            it.copy(
                                isLoading = false,
                                error = error[0].message
                            )
                        }
                    } else {
                        val totalQuantity = it.data?.cart?.totalQuantity
                        _cartCountState.update {
                            it.copy(
                                isLoading = false,
                                count = totalQuantity ?: 0
                            )
                        }
                    }

                }
                .onFailure { it1 ->
                    _cartCreateState.update { it.copy(isLoading = false, error = it1.message) }
                }
        }
    }

    /**
     * To get the cart product list with Amount types.
     */
    fun cartQuery(cartId: String) {
        screenModelScope.launch {
            cartQueryUseCase(cartId)
                .onSuccess { response ->
                    printLog("Ashwani : CartScreen : Success : $response")
                    val error = response.errors
                    val cart = response.data?.cart
                    if (!error.isNullOrEmpty()) {
                        _cartQueryState.update {
                            it.copy(
                                isLoading = false,
                                error = error[0].message
                            )
                        }
                    } else if (cart != null) {
//                        val totalQuantity = response.data?.cart?.totalQuantity
//                        val checkoutUrl = response.data?.cart?.checkoutUrl
//                        cachingManager.saveCheckoutUrl(checkoutUrl as String)
//                        cachingManager.saveCartCount(totalQuantity ?: 0 )

                        val rawTotalAmount = cart.cost.totalAmount.amount as String
                        val totalAmount = rawTotalAmount.toDoubleOrNull() ?: 0.0

                        val rawSubTotalAmount = cart.cost.subtotalAmount.amount as String
                        val subTotalAmount = rawSubTotalAmount.toDoubleOrNull() ?: 0.0

                        var taxAmount = 0.0
                        if (cart.cost.totalTaxAmount != null) {
                            val rawTaxAmount = cart.cost.totalTaxAmount.amount as String
                            taxAmount = rawTaxAmount.toDoubleOrNull() ?: 0.0
                        }

                        val currencyCode = cart.cost.totalAmount.currencyCode
                        cart.let {
                            val lines = cart.lines
                            val edges = lines.edges
                            val list = mutableListOf<UserCartUiData>()
                            edges.forEach {
                                val node = it.node
                                val merchandise = node.merchandise
                                val onProductVariant = merchandise.onProductVariant
                                val title = onProductVariant?.product?.title ?: ""
                                val quantity = node.quantity
                                val lineId = node.id
                                val rawAmount = onProductVariant?.price?.amount as String
                                val amount = rawAmount.toDoubleOrNull() ?: 0.0
                                val imageUrl = onProductVariant.image?.url ?: ""
                                val productId = onProductVariant.id
                                val userCartUiData = UserCartUiData(
                                    productId = productId,
                                    price = amount,
                                    quantity = quantity,
                                    title = title,
                                    imageUrl = imageUrl as String,
                                    lineId = lineId,
                                    currencyCode = currencyCode
                                )
                                list.add(userCartUiData)
                            }
                            _cartQueryState.update {
                                it.copy(
                                    isLoading = false,
                                    success = list,
                                    isLoaded = true,
                                    totalAmount = totalAmount,
                                    subTotalAmount = subTotalAmount,
                                    taxAmount = taxAmount,
                                    currencyCode = currencyCode
                                )
                            }
                        }
                    } else {
                        _cartQueryState.update {
                            it.copy(
                                isLoading = false,
                                error = "Cart is Empty"
                            )
                        }
                    }

                }
                .onFailure { it1 ->
                    printLog("Ashwani : CartScreen : onFailure : $it1")
                    _cartQueryState.update {
                        it.copy(
                            isLoading = false,
                            error = it1.message ?: "Error Occurred!"
                        )
                    }
                }
        }
    }

    /**
     * It updates the product quantity in Cart
     */
    fun cartUpdate(cartId: String, cartLineInput: CartLineUpdateInput) {
        screenModelScope.launch {
            val pair = Pair(cartId, listOf(cartLineInput))
            cartUpdateUseCase(pair)
                .onSuccess { it1 ->
                    val error = it1.errors
                    val cart = it1.data?.cartLinesUpdate?.cart
                    if (!error.isNullOrEmpty()) {
                        _cartQueryState.update {
                            it.copy(
                                isLoading = false,
                                error = error[0].message
                            )
                        }
                    } else if (cart != null) {
                        val rawTotalAmount = cart.cost.totalAmount.amount as String
                        val totalAmount = rawTotalAmount.toDoubleOrNull() ?: 0.0
                        val rawSubTotalAmount = cart.cost.subtotalAmount.amount as String
                        val subTotalAmount = rawSubTotalAmount.toDoubleOrNull() ?: 0.0
                        var taxAmount = 0.0
                        if (cart.cost.totalTaxAmount != null) {
                            val rawTaxAmount = cart.cost.totalTaxAmount.amount as String
                            taxAmount = rawTaxAmount.toDoubleOrNull() ?: 0.0
                        }
                        val currencyCode = cart.cost.totalAmount.currencyCode
                        cart.let {
                            val lines = cart.lines
                            val edges = lines.edges
                            val list = mutableListOf<UserCartUiData>()
                            edges.forEach {
                                val node = it.node
                                val merchandise = node.merchandise
                                val onProductVariant = merchandise.onProductVariant
                                val title = onProductVariant?.product?.title ?: ""
                                val quantity = node.quantity
                                val lineId = node.id
                                val rawAmount = onProductVariant?.price?.amount as String
                                val amount = rawAmount.toDoubleOrNull() ?: 0.0
                                val imageUrl = onProductVariant.image?.url ?: ""
                                val productId = onProductVariant.id
                                val userCartUiData = UserCartUiData(
                                    productId = productId,
                                    price = amount,
                                    quantity = quantity,
                                    title = title,
                                    imageUrl = imageUrl as String,
                                    lineId = lineId,
                                    currencyCode = currencyCode
                                )
                                list.add(userCartUiData)
                            }
                            _cartQueryState.update {
                                it.copy(
                                    isLoading = false, success = list,
                                    isLoaded = true,
                                    totalAmount = totalAmount,
                                    subTotalAmount = subTotalAmount,
                                    taxAmount = taxAmount, currencyCode = currencyCode
                                )
                            }

                            cart.let {
                                val checkoutUrl = cart.checkoutUrl
                                val totalQuantity = cart.totalQuantity
                                cachingManager.saveCartCount(totalQuantity)
                                cachingManager.saveCheckoutUrl(checkoutUrl as String)
                            }
                        }
                    }
                }
                .onFailure { it1 ->
                    _cartQueryState.update {
                        it.copy(
                            isLoading = false,
                            error = it1.message ?: "Error Occurred!"
                        )
                    }
                }
        }

    }

    /**
     * To update the Buyer Identity in Checkout WebUrl
     */
    fun cartBuyerIdentityUpdate(cartId: String) {
        screenModelScope.launch {
            _cartBuyerIdentityUpdateState.update {
                it.copy(
                    isLoading = true
                )
            }
                val cartBuyerIdentity = cartBuyerIdentityInput(
                    email = UserDataAccess.email,
                    phone = "+91${UserDataAccess.phone}",
                    countryCode = buyerCountry(),
                    customerAccessToken = UserDataAccess.customerAccessToken,
                    deliveryAddressInput = deliveryAddressInput(UserDataAccess.addressId),
                )

                printLog("$cartBuyerIdentity")

                val param = Pair(cartBuyerIdentity, cartId)

                cartBuyerIdentityUpdateUseCase(param).onSuccess {
                    val error = it.errors

                    if (error != null && error.isNotEmpty()) {
                        _cartBuyerIdentityUpdateState.update {
                            it.copy(
                                isLoading = false,
                                error = error[0].message,
                                isLoaded = false
                            )
                        }
                    } else {
                        val data = it.data
                        val userErrors = data?.cartBuyerIdentityUpdate?.userErrors
                        if (userErrors != null && userErrors.isNotEmpty()) {
                            printLog("Error is '${userErrors[0].message}'")
                            _cartBuyerIdentityUpdateState.update {
                                it.copy(
                                    isLoading = false,
                                    error = userErrors[0].message,
                                    isLoaded = false
                                )
                            }
                        } else {
                            val checkoutUrl = data?.cartBuyerIdentityUpdate?.cart?.checkoutUrl
                            printLog("Checkout Url is $checkoutUrl")
                            _cartBuyerIdentityUpdateState.update {
                                it.copy(
                                    isLoading = false,
                                    success = data,
                                    isLoaded = true
                                )
                            }
                        }
                    }
                }.onFailure { it1 ->
                        _cartBuyerIdentityUpdateState.update {
                            it.copy(
                                isLoading = false,
                                error = it1.message ?: "Error Occurred!"
                            )
                        }
                    }

        }
    }

    fun cartBuyerCancel() {
        screenModelScope.launch {
            _cartBuyerIdentityUpdateState.update {
                it.copy(
                    isLoading = false,
                    success = null,
                    isLoaded = false,
                    error = ""
                )
            }
        }
    }

}