package ui.tabNavigation.tabContents.more

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
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
fun Tab.MoreTabContent() {
    val tabTitle = options.title

    LifecycleEffect(
        onStarted = {
//            println("Navigator", "Start tab $tabTitle")
                    },
        onDisposed = {
//            println("Navigator", "Dispose tab $tabTitle")
                     },
    )

    MoreInnerNavigation()


}

@Composable
private fun MoreInnerNavigation() {
    Column (
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "More Tab Content", modifier = Modifier.weight(0.51f))
    }
}


