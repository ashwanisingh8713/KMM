package com.ns.shopify.presentation.screen.address

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.apollographql.apollo3.api.Optional
import com.ns.shopify.data.storage.CachingManager
import com.ns.shopify.domain.usecase.address.AddAddressUseCase
import com.ns.shopify.type.MailingAddressInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class AddAddressViewModel(
    private val addressUseCase: AddAddressUseCase,
    private val cachingManager: CachingManager
) : ScreenModel, KoinComponent {

    private val _state = MutableStateFlow(AddAddressState())
    val state = _state.asStateFlow()

    fun addAddressRequest(
        address1: String,
        address2: String,
        city: String,
        country: String,
        company: String,
        firstName: String,
        lastName: String,
        phone: String,
        province: String,
        zip: String
    ) {
        _state.value = AddAddressState(isLoading = true)
        coroutineScope.launch {
            cachingManager.getCustomerAccessToken().collectLatest { userAccessToken ->
                addressUseCase(
                    Pair(
                        userAccessToken,
                        MailingAddressInput(
                            address1 = Optional.present(address1),
                            address2 = Optional.present(address2),
                            city = Optional.present(city),
                            company = Optional.present(company),
                            country = Optional.present(country),
                            firstName = Optional.present(firstName),
                            lastName = Optional.present(lastName),
                            phone = Optional.present(phone),
                            province = Optional.present(province),
                            zip = Optional.present(zip)
                        )
                    )
                ).onSuccess { it1 ->
                    val errorData = it1.customerUserErrors
                    if (errorData.isNotEmpty()) {
                        _state.update { it2 ->
                            val error = errorData[0].message
                            it2.copy(
                                isLoading = false,
                                isLoaded = false,
                                error = error
                            )
                        }
                    } else {
                        _state.update { it2 ->
                            it2.copy(
                                isLoading = false,
                                isLoaded = true,
                                success = it1
                            )
                        }
                    }
                }.onFailure { error ->
                    _state.update { it.copy(isLoading = false, isLoaded = false, error = error.message ?: "Error Occurred!") }
                }
            }
        }


    }

    fun clearErrorState() {
        _state.update { it.copy(error = "") }
    }

}