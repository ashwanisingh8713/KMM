package com.ns.shopify.kmm

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.ns.shopify.kmm.presentation.ui.theme.AppTheme
import com.ns.shopify.kmm.presentation.ui.features.characters.CharactersScreen

@Composable
internal fun App() = AppTheme {
    Navigator(CharactersScreen())
}

