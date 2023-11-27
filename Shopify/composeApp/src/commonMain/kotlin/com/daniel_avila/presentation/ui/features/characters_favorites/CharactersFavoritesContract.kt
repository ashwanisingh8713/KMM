package com.daniel_avila.presentation.ui.features.characters_favorites

import com.daniel_avila.domain.model.Character
import com.daniel_avila.presentation.model.ResourceUiState
import com.daniel_avila.presentation.mvi.UiEffect
import com.daniel_avila.presentation.mvi.UiEvent
import com.daniel_avila.presentation.mvi.UiState

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