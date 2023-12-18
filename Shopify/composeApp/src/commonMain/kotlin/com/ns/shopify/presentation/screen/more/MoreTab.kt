package com.ns.shopify.presentation.screen.more

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.ns.shopify.presentation.screen.home.HomeScreen
import com.ns.shopify.presentation.screen.product_detail.ProductDetailScreen

/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */
internal object MoreTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(image = Icons.Default.MoreVert)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "More",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            screen = MoreScreen(),
        ) { navigator ->
            SlideTransition(navigator) { screen ->
                screen.Content()
            }
        }

    }
}