package com.ns.shopify.presentation.componets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable


/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */
@Composable
fun AnimVisible(
    isVisible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        exit = fadeOut(),
        enter = fadeIn()
    ) {
        content()
    }
}