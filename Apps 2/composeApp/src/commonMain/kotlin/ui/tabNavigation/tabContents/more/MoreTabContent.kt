package ui.tabNavigation.tabContents.more

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.navigator.tab.Tab

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun Tab.MoreTabContent() {
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


