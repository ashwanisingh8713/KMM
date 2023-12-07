package com.ns.shopify.presentation.screen.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.app.printLog
import com.ns.shopify.domain.usecase.category.CategoryCollectionUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 07,December,2023.
 */
class HomeViewModel(private val categoryCollectionUsecase: CategoryCollectionUsecase): ScreenModel, KoinComponent {

    private val TAG = HomeViewModel::class.simpleName

    private val _state = MutableStateFlow<HomeState>(HomeState())
    val state = _state.asStateFlow()


    private val collection = mutableListOf<String>()

    fun getCollection() {
        coroutineScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isLoaded = false
                )
            }
            categoryCollectionUsecase.invoke("")
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
                                success = categories ?: emptyList()
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