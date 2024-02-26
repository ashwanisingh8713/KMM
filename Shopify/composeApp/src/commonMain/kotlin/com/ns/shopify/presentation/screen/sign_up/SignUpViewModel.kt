package com.ns.shopify.presentation.screen.sign_up

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.apollographql.apollo3.api.Optional
import com.ns.shopify.domain.usecase.login.CustomerCreateUseCase
import com.ns.shopify.type.CustomerCreateInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 11,December,2023.
 */
class SignUpViewModel(private val signUpViewModel: CustomerCreateUseCase) : ScreenModel,
    KoinComponent {

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    fun signUpRequest(email: String, password: String, firstName: String, lastName: String, phone: String) {
        _state.value = SignUpState(isLoading = true)
        screenModelScope.launch {
            signUpViewModel(
                CustomerCreateInput(
                    email = email,
                    password = password,
                    firstName = Optional.present(firstName),
                    lastName = Optional.present(lastName),
                    phone = Optional.present(phone),
                    acceptsMarketing = Optional.present(true)
                )
            ).onSuccess {it1->
                    val errorData = it1.customerUserErrors
                    if(errorData.isNotEmpty()) {
                        _state.update { it2->
                            val error = errorData[0].message
                            it2.copy(
                                isLoading = false,
                                isLoaded = false,
                                error = error
                            )
                        }
                        return@onSuccess
                    } else {
                        _state.update { it2 ->
                            it2.copy(
                                isLoading = false,
                                isLoaded = true,
                                success = it1,
                                error = ""
                            )
                        }
                    }
                }.onFailure {error->
                    _state.update {
                        it.copy(
                            isLoading = true,
                            isLoaded = false,
                            error = error.message
                        )
                    }
                }
        }
    }


fun clearErrorState() {
        _state.update { it.copy(error = "") }
    }

}