package com.ns.shopify.presentation.screen.sign_in

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.ns.shopify.domain.usecase.login.AccessTokenCreateUseCase
import com.ns.shopify.type.CustomerAccessTokenCreateInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 11,December,2023.
 */
class SignInViewModel(private val signInUseCase: AccessTokenCreateUseCase) : ScreenModel, KoinComponent {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun signInRequest(email: String, password: String) {
        coroutineScope.launch {
            _state.value = SignInState(isLoading = true)
            signInUseCase(CustomerAccessTokenCreateInput(email, password))
                .onSuccess {it1->
                    val error = it1.customerUserErrors?.get(0)?.message
                    if(error!=null) {
                        _state.update { it2->
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
                }.onFailure {error->
                    _state.update {
                        it.copy(
                            isLoading = true,
                            isLoaded = false,
                            error = error.message!!
                        )
                    }
                }

        }

    }
}