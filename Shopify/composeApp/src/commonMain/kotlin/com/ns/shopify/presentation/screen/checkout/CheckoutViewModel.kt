package com.ns.shopify.presentation.screen.checkout

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.apollographql.apollo3.api.Optional
import com.app.printLog
import com.ns.shopify.CheckoutCreateMutation
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.data.storage.UserDataAccess
import com.ns.shopify.data.utils.checkoutBuyerIdentityInput
import com.ns.shopify.data.utils.checkoutLineItems
import com.ns.shopify.data.utils.checkoutMailingAddressInput
import com.ns.shopify.domain.usecase.checkout.CheckoutCompleteWithCreditCardUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutShippingAddressUpdateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutShippingLineUpdateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutCreateUseCase
import com.ns.shopify.domain.usecase.checkout.CheckoutCustomerAssociateUseCase
import com.ns.shopify.presentation.screen.cart.UserCartUiData
import com.ns.shopify.presentation.screen.checkout.model.CreditCard
import com.ns.shopify.presentation.screen.checkout.model.VaultBody
import com.ns.shopify.type.CheckoutCreateInput
import com.ns.shopify.type.CheckoutLineItemInput
import com.ns.shopify.type.CreditCardPaymentInputV2
import com.ns.shopify.type.CurrencyCode
import com.ns.shopify.type.MoneyInput
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 08,February,2024.
 */
class CheckoutViewModel(
    private val checkoutCreateUC: CheckoutCreateUseCase,
    private val checkoutCustomerAssociateUC: CheckoutCustomerAssociateUseCase,
    private val checkoutShippingLineUpdateUC: CheckoutShippingLineUpdateUseCase,
    private val checkoutShippingAddressUpdateUC: CheckoutShippingAddressUpdateUseCase,
    private val checkoutCompleteWithCreditCardUC: CheckoutCompleteWithCreditCardUseCase,
    private val cachingManager: CachingManager
) : ScreenModel, KoinComponent {

    private val _checkoutLineItemsState = MutableStateFlow<List<CheckoutLineItemInput>>(emptyList())
    val checkoutLineItemsState = _checkoutLineItemsState.asStateFlow()

    private val _checkoutCreateState = MutableStateFlow(CheckoutCreateState())
    val checkoutCreateState = _checkoutCreateState.asStateFlow()

    private val _checkoutCustomerAssociateState = MutableStateFlow(CheckoutCustomerAssociateState())
    val checkoutCustomerAssociateState = _checkoutCustomerAssociateState.asStateFlow()

    private val _checkoutShippingLineUpdateState =
        MutableStateFlow(CheckoutShippingLineUpdateState())
    val checkoutShippingLineUpdateState = _checkoutShippingLineUpdateState.asStateFlow()

    private val _checkoutShippingAddressUpdateState =
        MutableStateFlow(CheckoutShippingAddressUpdateState())
    val checkoutShippingAddressUpdateState = _checkoutShippingAddressUpdateState.asStateFlow()

    private val _checkoutCompleteWithCreditCardState =
        MutableStateFlow(CheckoutCompleteWithCreditCardState())
    val checkoutCompleteWithCreditCardState = _checkoutCompleteWithCreditCardState.asStateFlow()


    fun checkoutEvent(event: CheckoutEvent) {
        when (event) {
            is CheckoutEvent.CheckoutLineItemsEvent -> {
                checkoutLineItemsResult(event.cartLineItems)
            }

            is CheckoutEvent.CheckoutCreateEvent -> {
                checkoutCreateResult(event.checkoutLineItems)
            }

            is CheckoutEvent.CheckoutCustomerAssociateEvent -> {
                checkoutCustomerAssociateResult(event.checkoutId, event.customerAccessToken)
            }

            is CheckoutEvent.CheckoutShippingLineUpdateEvent -> {
                checkoutShippingLineUpdateResult(event.checkoutId, event.shippingRateHandle)
            }

            is CheckoutEvent.CheckoutShippingAddressUpdateEvent -> {
                checkoutShippingAddressUpdateResult(event.checkoutId)
            }

            is CheckoutEvent.CheckoutCompleteWithCreditCardEvent -> {
                checkoutCompleteWithCreditCardResult(event.checkoutId, event.totalPrice)
            }
        }
    }

    // Here I need to return Flow< CheckoutCreateEvent>
    private fun checkoutLineItemsResult(cartLineItems: List<UserCartUiData>) {
        val checkoutLineItems = checkoutLineItems(cartLineItems)
        // Fire the event to create a checkout
        _checkoutLineItemsState.update { checkoutLineItems }
    }

    // Step-1, Create a checkout ID with required details
    private fun checkoutCreateResult(checkoutLineItems: List<CheckoutLineItemInput>) {

        _checkoutCreateState.update { it.copy(isLoading = true) }
        coroutineScope.launch {
            val checkoutCreateInput = CheckoutCreateInput(
                email = Optional.present(UserDataAccess.email),
                lineItems = Optional.present(checkoutLineItems),
                shippingAddress = Optional.present((checkoutMailingAddressInput())),
                note = Optional.present("This is note given by user"),
                customAttributes = Optional.present(listOf()),
                allowPartialAddresses = Optional.present(true),
                buyerIdentity = Optional.present(checkoutBuyerIdentityInput())
            )

            checkoutCreateUC(checkoutCreateInput).onSuccess {
                val dd = it
                val data = dd.data
                val error = it.errors
                if (error.isNullOrEmpty()) {
                    val userErrors = data?.checkoutCreate?.checkoutUserErrors
                    if (userErrors.isNullOrEmpty()) {
                        val checkoutId = data?.checkoutCreate?.checkout?.id!!
                        val totalPrice = data.checkoutCreate.checkout.totalPrice
                        val shippingRateHandle =
                            data.checkoutCreate.checkout.shippingLine?.handle ?: ""
                        _checkoutCreateState.update {
                            it.copy(
                                isLoading = false,
                                isLoaded = true,
                                checkoutId = checkoutId,
                                shippingRateHandle = shippingRateHandle,
                                totalPrice = totalPrice
                            )
                        }

                    } else {
                        printLog("CheckoutCreateEvent Error :: ${userErrors[0].message}")

                        _checkoutCreateState.update {
                            it.copy(
                                isLoading = false,
                                error = userErrors[0].message,
                                isLoaded = false
                            )

                        }
                    }
                } else {
                    _checkoutCreateState.update {
                        it.copy(
                            isLoading = false,
                            error = error[0].message,
                            isLoaded = false
                        )
                    }
                }

            }.onFailure {
                _checkoutCreateState.update {
                    it.copy(
                        isLoading = false,
                        error = it.error,
                        isLoaded = false
                    )
                }
            }

        }
    }

    // Step-2, Associate a customer to the checkout
    private fun checkoutCustomerAssociateResult(checkoutId: String, customerAccessToken: String) {
        coroutineScope.launch {
            _checkoutCustomerAssociateState.update { it.copy(isLoading = true) }
            val pair = Pair(checkoutId, customerAccessToken)
            checkoutCustomerAssociateUC(pair).onSuccess {
                val data = it.data
                val error = data?.checkoutCustomerAssociateV2?.checkoutUserErrors
                if (error.isNullOrEmpty()) {
                    val checkoutId = data?.checkoutCustomerAssociateV2?.checkout?.id
                    val webUrl = data?.checkoutCustomerAssociateV2?.checkout?.webUrl as String
                    _checkoutCustomerAssociateState.update {
                        it.copy(
                            isLoading = false,
                            isLoaded = true,
                            checkoutId = checkoutId!!,
                            webUrl = webUrl
                        )
                    }
                } else {
                    _checkoutCustomerAssociateState.update {
                        it.copy(
                            isLoading = false,
                            error = error[0].message,
                            isLoaded = false
                        )
                    }
                }
            }.onFailure {
                _checkoutCustomerAssociateState.update {
                    it.copy(
                        isLoading = false,
                        error = it.error,
                        isLoaded = false
                    )
                }
            }
        }
    }


    // Step-3, Shipping rates handle
    // create shipping line update
    private fun checkoutShippingLineUpdateResult(checkoutId: String, shippingRateHandle: String) {
        coroutineScope.launch {
            _checkoutShippingLineUpdateState.update { it.copy(isLoading = true) }
            val pair = Pair(checkoutId, shippingRateHandle)
            checkoutShippingLineUpdateUC(pair).onSuccess {
                val data = it.data
                val errors = it.errors
                if (errors.isNullOrEmpty()) {
                    val userErrors = data?.checkoutShippingLineUpdate?.checkoutUserErrors

                    val checkoutId = data?.checkoutShippingLineUpdate?.checkout?.id ?: ""
                    val webUrl = data?.checkoutShippingLineUpdate?.checkout?.webUrl as String
                    val shippingLineHandle =
                        data?.checkoutShippingLineUpdate?.checkout?.shippingLine?.handle as String
                    _checkoutShippingLineUpdateState.update {
                        it.copy(
                            isLoading = false,
                            isLoaded = true,
                            checkoutId = checkoutId,
                            webUrl = webUrl,
                            shippingLineHandle = shippingLineHandle
                        )
                    }
                } else {
                    printLog("CheckoutShippingLineUpdateEvent Error :: ${errors[0].message}")

                    _checkoutShippingLineUpdateState.update {
                        it.copy(
                            isLoading = false,
                            error = errors[0].message,
                            isLoaded = false
                        )
                    }
                }

            }.onFailure {
                _checkoutShippingLineUpdateState.update {
                    it.copy(
                        isLoading = false,
                        error = it.error,
                        isLoaded = false
                    )
                }
            }
        }
    }


    // Step-4, Update the shipping address on the checkout
    private fun checkoutShippingAddressUpdateResult(checkoutId: String) {
        coroutineScope.launch {
            _checkoutShippingAddressUpdateState.update { it.copy(isLoading = true) }
            val pair = Pair(checkoutId, checkoutMailingAddressInput())
            checkoutShippingAddressUpdateUC(pair).onSuccess {
                val data = it.data
                val errors = it.errors
                if (errors.isNullOrEmpty()) {
                    val userErrors = data?.checkoutShippingAddressUpdateV2?.checkoutUserErrors
                    if (userErrors.isNullOrEmpty()) {
                        val checkoutId = data?.checkoutShippingAddressUpdateV2?.checkout?.id ?: ""
                        val shippingAddress =
                            data?.checkoutShippingAddressUpdateV2?.checkout?.shippingAddress
                        _checkoutShippingAddressUpdateState.update {
                            it.copy(
                                isLoading = false,
                                isLoaded = true,
                                checkoutId = checkoutId,
                                success = shippingAddress,

                                )
                        }
                    } else {
                        _checkoutShippingAddressUpdateState.update {
                            it.copy(
                                isLoading = false,
                                error = userErrors[0].message,
                                isLoaded = false
                            )
                        }
                    }
                } else {
                    _checkoutShippingAddressUpdateState.update {
                        it.copy(
                            isLoading = false,
                            error = errors[0].message,
                            isLoaded = false
                        )
                    }
                }
            }.onFailure {
                _checkoutShippingAddressUpdateState.update {
                    it.copy(
                        isLoading = false,
                        error = it.error,
                        isLoaded = false
                    )
                }
            }
        }
    }


    // Step-5, Complete the checkout with a credit card
    private fun checkoutCompleteWithCreditCardResult(
        checkoutId: String,
        totalPrice: CheckoutCreateMutation.TotalPrice?
    ) {
        coroutineScope.launch {
            _checkoutCompleteWithCreditCardState.update { it.copy(isLoading = true) }
            val paymentAmount = MoneyInput(
                amount = totalPrice?.amount ?: "0.0",
                currencyCode = totalPrice?.currencyCode ?: CurrencyCode.USD
            )
            val creditCartPaymentInput = CreditCardPaymentInputV2(
                paymentAmount = paymentAmount,
                idempotencyKey = "1234567890",
                billingAddress = checkoutMailingAddressInput(),
                vaultId = "",
                test = Optional.present(true),
            )


            val pair = Pair(checkoutId, creditCartPaymentInput)
            checkoutCompleteWithCreditCardUC(pair).onSuccess {
                val data = it.data
                val errors = it.errors
                if (errors.isNullOrEmpty()) {
                    val userErrors = data?.checkoutCompleteWithCreditCardV2?.checkoutUserErrors
                    if (userErrors.isNullOrEmpty()) {
                        val checkoutId = data?.checkoutCompleteWithCreditCardV2?.checkout?.id ?: ""
                        printLog("CheckoutCompleteWithCreditCardEvent Success :: $checkoutId")
                        _checkoutCompleteWithCreditCardState.update { it.copy(isLoaded = true, checkoutId = checkoutId) }
                    } else {
                        _checkoutCompleteWithCreditCardState.update {
                            it.copy(
                                isLoading = false,
                                error = userErrors[0].message,
                                isLoaded = false
                            )
                        }
                    }
                } else {
                    _checkoutCompleteWithCreditCardState.update {
                        it.copy(
                            isLoading = false,
                            error = errors[0].message,
                            isLoaded = false
                        )
                    }
                }
            }.onFailure {
                _checkoutCompleteWithCreditCardState.update {
                    it.copy(
                        isLoading = false,
                        error = it.error,
                        isLoaded = false
                    )
                }
            }
        }
    }

    fun createVaultId() {
        coroutineScope.launch {
            val client = HttpClient {
                followRedirects = true
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

            val body = VaultBody(
                CreditCard(
                    number = "4242424242424242",
                    first_name = "Ashwani",
                    last_name = "Kumar",
                    month = "12",
                    year = "26",
                    verification_value = "123"
                )
            )
            try {

                val httpResponse = client.post("https://quickstart-fe108883.myshopify.com/sessions") {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                        append(
                            HttpHeaders.Authorization,
                            "bearer ${UserDataAccess.customerAccessToken}"
                        )
                        append(HttpHeaders.ContentType, "application/json")
                    }

                }

                val stringBody: String = httpResponse.body()

                printLog("createVaultId() Success :: $stringBody")
                printLog("CustomerAccessToken :: ${UserDataAccess.customerAccessToken}")
            }
            catch (e: Exception) {
                printLog("createVaultId() Error :: ${e.printStackTrace()}")
            }

        }

    }


}