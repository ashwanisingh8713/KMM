package ui.tabNavigation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ui.tabNavigation.tabs.BookmarkTab
import ui.tabNavigation.tabs.PremiumTab
import ui.tabNavigation.tabs.HomeTab
import ui.tabNavigation.tabs.MoreTab
import ui.tabNavigation.tabs.ReelsTab

/**
 * Created by Ashwani Kumar Singh on 10,August,2023.
 */

internal class StartingScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalVoyagerApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(
            HomeTab,
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(HomeTab, PremiumTab, BookmarkTab, ReelsTab, MoreTab)
                )
            }
        ) { tabNavigator ->
            Scaffold(
//                topBar = {
//                    TopAppBar(
//                        title = {
//                            Surface(shadowElevation = 3.dp) {
//                                Text(text = tabNavigator.current.options.title)
//                            }
//                        }
//                    )
//                },
                content = {
                    BoxWithConstraints(
                        Modifier.padding(it),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    BottomAppBar {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(PremiumTab)
                        TabNavigationItem(BookmarkTab)
                        TabNavigationItem(ReelsTab)
                        TabNavigationItem(MoreTab)
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
}