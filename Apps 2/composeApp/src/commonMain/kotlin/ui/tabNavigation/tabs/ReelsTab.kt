package ui.tabNavigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ui.tabNavigation.tabContents.reels.ReelsTabContent

/**
 * Created by Ashwani Kumar Singh on 02,November,2023.
 */
internal object ReelsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Bookmark)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Reels",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        ReelsTabContent()
    }
}