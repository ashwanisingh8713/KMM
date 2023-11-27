package com.ns.shopify.kmm.presentation.ui.features.characters

import com.ns.shopify.kmm.domain.model.Character
import com.ns.shopify.kmm.presentation.model.ResourceUiState
import com.ns.shopify.kmm.presentation.mvi.UiEffect
import com.ns.shopify.kmm.presentation.mvi.UiEvent
import com.ns.shopify.kmm.presentation.mvi.UiState

interface CharactersContract {
    sealed interface Event : UiEvent {
        object OnTryCheckAgainClick : Event
        object OnFavoritesClick : Event
        data class OnCharacterClick(val idCharacter: Int) : Event
    }

    data class State(
        val characters: ResourceUiState<List<Character>>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToDetailCharacter(val idCharacter: Int) : Effect
        object NavigateToFavorites : Effect
    }
}


