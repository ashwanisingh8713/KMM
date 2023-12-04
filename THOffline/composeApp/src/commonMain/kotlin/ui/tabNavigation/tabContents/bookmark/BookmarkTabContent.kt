package ui.tabNavigation.tabContents.bookmark

/**
 * Created by Ashwani Kumar Singh on 02,November,2023.
 */

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition
import ui.screens.home.BookmarkScreen
import ui.screens.home.HomeScreen


@Composable
fun Tab.BookmarkTabContent() {
    val tabTitle = options.title

    Navigator(
        BookmarkScreen()
    ) { navigator ->
        SlideTransition(navigator) { screen ->
            screen.Content()
        }
    }
}