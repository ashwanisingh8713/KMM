package com.ns.shopify.presentation.screen.product_detail

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 08,December,2023.
 */
class ProductDetailViewModel:ScreenModel, KoinComponent {

    private val TAG = "ProductDetailViewModel"

    private val _state = MutableStateFlow(ProductDetailStates())
    val state = _state.asStateFlow()



}