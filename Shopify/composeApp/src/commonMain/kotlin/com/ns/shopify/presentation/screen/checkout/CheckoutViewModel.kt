package com.ns.shopify.presentation.screen.checkout

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
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
import com.ns.shopify.presentation.screen.checkout.model.NextAction
import com.ns.shopify.presentation.screen.checkout.model.PaymentCreditCard
import com.ns.shopify.presentation.screen.checkout.model.PaymentSessionIdBody
import com.ns.shopify.presentation.screen.checkout.model.Transaction
import com.ns.shopify.presentation.screen.checkout.model.VaultBody
import com.ns.shopify.type.CheckoutCreateInput
import com.ns.shopify.type.CheckoutLineItemInput
import com.ns.shopify.type.CreditCardPaymentInputV2
import com.ns.shopify.type.CurrencyCode
import com.ns.shopify.type.MoneyInput
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

        screenModelScope.launch {
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
        screenModelScope.launch {
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
        screenModelScope.launch {
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
        screenModelScope.launch {
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

        screenModelScope.launch {
            _checkoutCompleteWithCreditCardState.update { it.copy(isLoading = true) }
            val paymentAmount = MoneyInput(
                amount = totalPrice?.amount ?: "0.0",
                currencyCode = totalPrice?.currencyCode ?: CurrencyCode.USD
            )
            val creditCartPaymentInput = CreditCardPaymentInputV2(
                paymentAmount = paymentAmount,
                idempotencyKey = "0980909r4343d1212zdjJHD",
                billingAddress = checkoutMailingAddressInput(),
                vaultId = "east-833efab84a50266b2463278ce6f1d4e5",
                test = Optional.present(true),
            )

            printLog("CheckoutCompleteWithCreditCard Sent Checkout Id is :: $checkoutId")

            val checkoutIdModified = checkoutId
//            val checkoutIdModified = "gid://shopify/Checkout/cda0db988bbfbccdaf6c6e6ed159d21a"

            val pair = Pair(checkoutIdModified, creditCartPaymentInput)
            checkoutCompleteWithCreditCardUC(pair).onSuccess {
                val data = it.data
                val errors = it.errors
                if (errors.isNullOrEmpty()) {
                    val userErrors = data?.checkoutCompleteWithCreditCardV2?.checkoutUserErrors
                    if (userErrors.isNullOrEmpty()) {
                        val recCheckoutId = data?.checkoutCompleteWithCreditCardV2?.checkout?.id
                        if(checkoutId.isNullOrEmpty()) {
                            printLog("CheckoutCompleteWithCreditCard Received Checkout Id is Empty")
                        } else {
                            printLog("CheckoutCompleteWithCreditCard Received Checkout Id is :: $recCheckoutId")
                        }
                        _checkoutCompleteWithCreditCardState.update {
                            it.copy(
                                isLoaded = true,
                                checkoutId = checkoutId
                            )
                        }
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


    /**
     * Create VoultId-Tokenization of Card
     */
    fun createVaultId() {
        screenModelScope.launch {
            val client = getKoin().get<HttpClient>()
            val body = VaultBody(
                CreditCard(
                    number = "4242424242424242",
                    first_name = "Ashwani",
                    last_name = "Kumar",
                    month = "12",
                    year = "2026",
                    verification_value = "123"
                )
            )
            try {

                val httpResponse =
                    client.post("https://quickstart-fe108883.myshopify.com/sessions") {
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
            } catch (e: Exception) {
                printLog("createVaultId() Error :: ${e.printStackTrace()}")
            }

        }

    }


    /**
     * Create Card's Payment sessionId
     */
    fun createSessionId() {
        screenModelScope.launch {
            val creditCard = PaymentCreditCard(
                first_name = "Ashwani",
                last_name = "Singh",
                first_digits = "4242",
                last_digits = "4242",
                brand = "VISA",
                expiry_month = "12",
                expiry_year = "2025"
            )
            val nextAction = NextAction(redirect_url = UserDataAccess.checkoutUrl)
            val transaction = Transaction(
                amount = "111",
                amount_in = "",
                amount_out = "",
                amount_rounding = "",
                authorization = UserDataAccess.customerAccessToken,
                created_at = "2024-02-15T15:47:53-04:00",
                currency = "INR",
                error_code = "123",
                gateway = "shopify_payments",
                id = "1234567890",
                kind = "sale",
                message = "This is message",
                status = "success",
                test = true
            )
            val body = PaymentSessionIdBody(
                id = "1234567890",
                credit_card = creditCard,
                payment_processing_error_message = "Payment Error",
                next_action = nextAction,
                unique_token = "ddfjdfkkdfdfkdkfkdf",
                transaction = transaction
            )

//            val url = "https://quickstart-fe108883.myshopify.com/admin/api/2024-01/checkouts/7yjf4v2we7gamku6a6h7tvm8h3mmvs4x/payments.json"

            val url = "https://quickstart-fe108883.myshopify.com/admin/api/2024-01/checkouts/${UserDataAccess.customerAccessToken}/payments.json"

            val client = getKoin().get<HttpClient>()
            try {
                val httpResponse =
                    client.post(url) {
                        contentType(ContentType.Application.Json)
                        setBody(body)
                        headers {
                            append(HttpHeaders.Accept, "application/json")
                            append(HttpHeaders.ContentType, "application/json")
                            append("X-Shopify-Access-Token", "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")
                        }

                    }

                val stringBody: String = httpResponse.body()

                printLog("createVaultId() Success :: $stringBody")
                printLog("CustomerAccessToken :: ${UserDataAccess.customerAccessToken}")
            } catch (e: Exception) {
                printLog("createVaultId() Error :: ${e.printStackTrace()}")
            }

        }

    }


}