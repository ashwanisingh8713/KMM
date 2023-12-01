package com.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.daniel_avila.presentation.ui.theme.AppTheme
import com.rocketreserver.ui.LaunchList


// This is for daniel.avila.rnm Sample
// GitHub link : https://github.com/daniaviladomingo/kmm
/*@Composable
internal fun App() = AppTheme {
    Navigator(CharactersScreen())
}*/


@Composable
internal fun App() = AppTheme {
    Navigator(LaunchList)
}


/*@Composable
internal fun App() = AppTheme {
    Navigator(HomeScreen())
}*/

