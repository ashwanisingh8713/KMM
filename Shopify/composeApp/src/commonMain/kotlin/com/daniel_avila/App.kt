package com.daniel_avila

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.daniel_avila.presentation.ui.theme.AppTheme
import com.daniel_avila.presentation.ui.features.characters.CharactersScreen

@Composable
internal fun App() = AppTheme {
    Navigator(CharactersScreen())
}

