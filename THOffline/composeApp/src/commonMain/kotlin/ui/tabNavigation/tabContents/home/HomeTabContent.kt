package ui.tabNavigation.tabContents.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition
import ui.screens.home.HomeScreen


@Composable
fun Tab.HomeTabContent() {
    val tabTitle = options.title

    LifecycleEffect(
        onStarted = {
//            Log.d("Navigator", "Start tab $tabTitle")
        },
        onDisposed = {
//            Log.d("Navigator", "Dispose tab $tabTitle")
        },
    )

    Navigator(
        HomeScreen(wrapContent = false)
    ) { navigator ->
        SlideTransition(navigator) { screen ->
            screen.Content()
        }
    }
}





