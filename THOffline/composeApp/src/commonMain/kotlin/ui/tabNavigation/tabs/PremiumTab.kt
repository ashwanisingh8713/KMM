package ui.tabNavigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PresentToAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ui.tabNavigation.tabContents.premium.PremiumTabContent

internal object PremiumTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.PresentToAll)

            return remember {
                TabOptions(
                    index = 1u,
                    title = "Premium",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        PremiumTabContent()
    }
}
