package com.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.daniel_avila.presentation.ui.theme.AppTheme
import com.ns.shopify.presentation.screen.HomeScreen


/**
 * Created by Ashwani Kumar Singh on 04,December,2023.
 */

@Composable
internal fun App() = AppTheme {
    Navigator(HomeScreen())
}

