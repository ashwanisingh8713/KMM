package com.daniel_avila.presentation.ui.features.character_detail

import com.daniel_avila.domain.model.Character
import com.daniel_avila.presentation.model.ResourceUiState
import com.daniel_avila.presentation.mvi.UiEffect
import com.daniel_avila.presentation.mvi.UiEvent
import com.daniel_avila.presentation.mvi.UiState

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