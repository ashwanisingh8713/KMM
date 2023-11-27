package com.ns.shopify.kmm.presentation.ui.features.characters_favorites

import com.ns.shopify.kmm.domain.model.Character
import com.ns.shopify.kmm.presentation.model.ResourceUiState
import com.ns.shopify.kmm.presentation.mvi.UiEffect
import com.ns.shopify.kmm.presentation.mvi.UiEvent
import com.ns.shopify.kmm.presentation.mvi.UiState

interface CharactersFavoritesContract {
    sealed interface Event : UiEvent {
        object OnBackPressed : Event
        object OnTryCheckAgainClick : Event
        data class OnCharacterClick(val idCharacter: Int) : Event
    }

    data class State(
        val charactersFavorites: ResourceUiState<List<Character>>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToDetailCharacter(val idCharacter: Int) : Effect
        object BackNavigation : Effect

    }
}