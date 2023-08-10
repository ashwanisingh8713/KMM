package daniel.avila.rnm.kmm

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import theme.AppTheme
import daniel.avila.rnm.kmm.presentation.ui.features.characters.CharactersScreen
import daniel.avila.rnm.kmm.presentation.ui.features.characters.Content

@Composable
internal fun App() = AppTheme {
//    Navigator(CharactersScreen())
    Content()
}

