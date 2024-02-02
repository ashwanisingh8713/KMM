package com.ns.shopify.presentation.screen.address

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.domain.usecase.address.AddressListUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 01,February,2024.
 */
class AddressListViewModel(
    private val addressListUseCase: AddressListUseCase,
    private val cachingManager: CachingManager
) : ScreenModel, KoinComponent {

    private val _state = MutableStateFlow(AddressListState())
    val addressState = _state.asStateFlow()

    fun getAddressList() {
        _state.value = AddressListState(isLoading = true)
        coroutineScope.launch {
            cachingManager.getCustomerAccessToken().collectLatest { customerAccessToken ->
                addressListUseCase(customerAccessToken).onSuccess { it ->
                    val data = it.data!!
                    val addresses = data.customer?.addresses?.edges?.map { it.node }
                    addresses?.let {
                        _state.update {
                            it.copy(
                                addresses = addresses,
                                isLoaded = true,
                                isLoading = false,
                                error = ""
                            )
                        }
                    }?:run {
                        _state.update { it1-> it1.copy(isLoading = false, error = "Address is empty", isLoaded = false) }
                    }

                }.onFailure {
                    //_state.value = AddressListState(error = it.message ?: "Something went wrong")
                    _state.update { it1 ->
                        it1.copy(
                            error = it.message ?: "Something went wrong",
                            isLoading = false,
                            isLoaded = false
                        )
                    }
                }

            }
        }
    }
}