package com.ns.shopify.kmm.presentation.ui.features.character_detail

import com.ns.shopify.kmm.domain.model.Character
import com.ns.shopify.kmm.presentation.model.ResourceUiState
import com.ns.shopify.kmm.presentation.mvi.UiEffect
import com.ns.shopify.kmm.presentation.mvi.UiEvent
import com.ns.shopify.kmm.presentation.mvi.UiState

interface CharacterDetailContract {
    sealed interface Event : UiEvent {
        object OnFavoriteClick : Event
        object OnTryCheckAgainClick : Event
        object OnBackPressed : Event
    }

    data class State(
        val character: ResourceUiState<Character>,
        val isFavorite: ResourceUiState<Boolean>,
    ) : UiState

    sealed interface Effect : UiEffect {
        object CharacterAdded : Effect
        object CharacterRemoved : Effect
        object BackNavigation : Effect
    }
}