package com.ns.shopify.presentation.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition

internal object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(image = Icons.Default.Home)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeViewModel>()
        val state = viewModel.state.collectAsState()

        Navigator(
            screen = HomeScreen(state, viewModel::getCollection),
        ) { navigator ->
            SlideTransition(navigator) { screen ->
                screen.Content()
            }
        }
    }


}