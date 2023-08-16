import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import theme.AppTheme
import ui.tabNavigation.StartingScreen

@Composable
internal fun App() = AppTheme {
//    Navigator(CharactersScreen())
    Navigator(StartingScreen())
//    StartingScreen()
}

