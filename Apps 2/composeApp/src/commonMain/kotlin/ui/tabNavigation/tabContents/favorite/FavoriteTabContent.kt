package ui.tabNavigation.tabContents.favorite

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition
import ui.screens.favourite.FavouriteScreen
import ui.screens.home.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Tab.FavoriteTabContent() {
    val tabTitle = options.title

    LifecycleEffect(
        onStarted = {
//            println("Navigator", "Start tab $tabTitle")
                    },
        onDisposed = {
//            println("Navigator", "Dispose tab $tabTitle")
                     },
    )

    Navigator(
        FavouriteScreen()
    ) { navigator ->
        SlideTransition(navigator) { screen ->
            screen.Content()
        }
    }
}

@Composable
private fun FavoriteInnerNavigation() {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Favorite Tab Content", modifier = Modifier.fillMaxSize())
    }
}
