package ui.tabNavigation.tabs

import androidx.compose.animation.ExperimentalAnimationApi
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
import ui.tabNavigation.tabs.BasicNavigationScreen

//https://proandroiddev.com/the-state-of-navigation-in-jetpack-compose-cc13eb6ac3d9
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun Tab.TabContent() {
    val tabTitle = options.title

    LifecycleEffect(
        onStarted = {
//            println("Navigator", "Start tab $tabTitle")
                    },
        onDisposed = {
//            println("Navigator", "Dispose tab $tabTitle")
                     },
    )

//    val navigator = LocalTabNavigator.current
//    navigator.push(item = InnerScreen(index = 0))

    InnerTabNavigation()

    Navigator(screen = BasicNavigationScreen(index = 0)) { navigator ->
//        navigator.parent = LocalNavigator.current.currentOrThrow
//        navigator.push(item = InnerScreen(index = 0))

//        Column {
//            InnerTabNavigation()
//        }

//        Navigator(screen = InnerScreen(index = 0)) { innerNavigator ->
//            FadeTransition(innerNavigator) { screen ->
//                Column {
//                    InnerTabNavigation()
//                    screen.Content()
//                }
//            }
//        }

//        navigator.push(item = InnerScreen(index = 0))




        ////////////
//        SlideTransition(navigator) { screen ->
//            Column {
//                InnerTabNavigation()
//                screen.Content()
////                Log.d("Navigator", "Last Event: ${navigator.lastEvent}")
//            }
//        }

    }
}

@Composable
private fun InnerTabNavigation() {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        TabNavigationButton(HomeTab)

        Spacer(modifier = Modifier.weight(.05f))

        TabNavigationButton(FavoritesTab)

        Spacer(modifier = Modifier.weight(.05f))

        TabNavigationButton(ProfileTab)
    }
}

@Composable
private fun RowScope.TabNavigationButton(
    tab: Tab
) {
    val tabNavigator = LocalTabNavigator.current

    Button(
        enabled = tabNavigator.current.key != tab.key,
        onClick = { tabNavigator.current = tab },
        modifier = Modifier.weight(1f)
    ) {
        Text(text = tab.options.title, modifier = Modifier.fillMaxSize())
    }
}
