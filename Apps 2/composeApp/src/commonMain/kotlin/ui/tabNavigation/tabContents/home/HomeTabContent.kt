package ui.tabNavigation.tabContents.home

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition
import ext.getScreenModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.model.SectionTabItem
import ui.screens.util.ComposeTag
import ui.screens.util.NoNetworkUI
import ui.vm.SectionListViewModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalResourceApi::class,
)
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
        HomeNavigationScreen(wrapContent = false)
    ) { navigator ->
        SlideTransition(navigator) { screen ->
            screen.Content()
        }
    }
}





