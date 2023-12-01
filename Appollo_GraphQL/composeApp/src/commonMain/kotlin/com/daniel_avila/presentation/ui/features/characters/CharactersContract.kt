package com.daniel_avila.presentation.ui.features.characters

import com.daniel_avila.domain.model.Character
import com.daniel_avila.presentation.model.ResourceUiState
import com.daniel_avila.presentation.mvi.UiEffect
import com.daniel_avila.presentation.mvi.UiEvent
import com.daniel_avila.presentation.mvi.UiState

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


