package com.ns.shopify.presentation.screen.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator

/**
 * Created by Ashwani Kumar Singh on 06,December,2023.
 */

internal class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navController = LocalNavigator.current
        if (navController != null) {
            HomeScreen(navController = navController)
        }
    }

    @Composable
    fun HomeScreen(navController: Navigator) {


    }


}