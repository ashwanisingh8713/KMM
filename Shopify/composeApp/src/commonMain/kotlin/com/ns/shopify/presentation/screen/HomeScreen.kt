package com.ns.shopify.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey

/**
 * Created by Ashwani Kumar Singh on 27,November,2023.
 */
class HomeScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey
    @Composable
    override fun Content() {
            Text("Hello Shopify App")
    }


}