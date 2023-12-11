package com.ns.shopify.presentation.screen.product_detail

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.app.printLog
import com.ns.shopify.domain.usecase.product.ProductDetailUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 08,December,2023.
 */
class ProductDetailViewModel(private val productDetailUsecase: ProductDetailUsecase):ScreenModel, KoinComponent {

    private val TAG = "ProductDetailViewModel"

    private val _state = MutableStateFlow(ProductDetailStates())
    val state = _state.asStateFlow()

    fun getProductDetail(id: String) {
        coroutineScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isLoaded = false
                )
            }
            productDetailUsecase.invoke("")
                .onSuccess {response->
                    val error = response.errors?.get(0)?.message
                    if(error!=null) { // Error Block
                        _state.update {its->
                            its.copy(
                                isLoading = false,
                                isLoaded = false,
                                error = error
                            )
                        }
                    } else {  // Success Block
                        val categories = response.data?.collections?.nodes

                        val title = response.data?.collections?.nodes?.get(0)?.products?.nodes?.get(0)?.title
                        printLog("model_error: $error")
                        printLog("model_1: $title")

                        _state.update {its->
                            its.copy(
                                isLoading = false,
                                isLoaded = true,
//                                success = categories ?: emptyList()
                            )
                        }
                    }
                }
                .onFailure {itError->
                    _state.update {its->
                        its.copy(
                            isLoading = false,
                            isLoaded = false,
                            error = itError.message.toString()
                        )
                    }
                }
        }
    }
    }



