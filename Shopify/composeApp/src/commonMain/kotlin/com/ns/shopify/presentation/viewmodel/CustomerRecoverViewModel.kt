package com.ns.shopify.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import com.ns.shopify.domain.usecase.login.CustomerRecoverUseCase
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
class CustomerRecoverViewModel(private val forgotPassword: CustomerRecoverUseCase) : ScreenModel, KoinComponent {


}