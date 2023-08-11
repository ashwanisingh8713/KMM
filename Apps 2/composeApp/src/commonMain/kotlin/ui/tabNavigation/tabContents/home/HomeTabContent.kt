package ui.tabNavigation.tabContents.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition
import ui.tabNavigation.tabs.BasicNavigationScreen
import ui.tabNavigation.tabs.FavoritesTab
import ui.tabNavigation.tabs.HomeTab
import ui.tabNavigation.tabs.ProfileTab

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Tab.HomeTabContent() {
    val tabTitle = options.title

//    LifecycleEffect(
//        onStarted = { Log.d("Navigator", "Start tab $tabTitle") },
//        onDisposed = { Log.d("Navigator", "Dispose tab $tabTitle") },
//    )

    Navigator(HomeNavigationScreen(index = 0)) { navigator ->
        SlideTransition(navigator) { screen ->
            Column {
//                InnerTabNavigation()
                screen.Content()
//                Log.d("Navigator", "Last Event: ${navigator.lastEvent}")
            }
        }
    }
}

