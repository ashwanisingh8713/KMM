package com.ns.shopify.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.ns.shopify.presentation.componets.Loading

/**
 * Created by Ashwani Kumar Singh on 06,December,2023.
 */

internal class HomeScreen(
    private val state: State<HomeState>,
    private val getCollection: () -> Unit
) : Screen {

    @Composable
    override fun Content() {
        val navController = LocalNavigator.current
        if (navController != null) {
            HomeScreen()
        }

        LaunchedEffect(true) {
            getCollection()
        }
    }

    @Composable
    fun HomeScreen() {
        if(state.value.error.isNotBlank()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.value.error, fontSize = 25.sp)
            }
        }
        if(state.value.isLoading) {
            Loading()
        }
        if(state.value.isLoaded) {
            //Text(text = state.value.success[0], fontSize = 25.sp)
        }

    }


}