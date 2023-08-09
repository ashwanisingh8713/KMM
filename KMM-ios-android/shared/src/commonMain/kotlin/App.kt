import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ui.theme.AppTheme
import ui.tabNavigation.tabs.FavoritesTab
import ui.tabNavigation.tabs.HomeTab
import ui.tabNavigation.tabs.ProfileTab

@Composable
fun App(screenWidthDp: Int) =
    AppTheme(screenWidthDp = screenWidthDp) {
        StartContent()
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartContent() {
    TabNavigator(HomeTab) {
            tabNavigator ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = tabNavigator.current.options.title) }
                )
            },
            content = {
                CurrentTab()
            },
            bottomBar = {
                BottomAppBar {
                    TabNavigationItem(HomeTab)
                    TabNavigationItem(FavoritesTab)
                    TabNavigationItem(ProfileTab)
                }
            }
        )
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    NavigationBarItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
    )
}





