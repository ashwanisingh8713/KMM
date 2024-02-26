package com.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Created by Ashwani Kumar Singh on 04,December,2023.
 */

@Composable
actual fun AppTheme(
    isDark: Boolean,
    content: @Composable () -> Unit
) {
    val color = if(isDark) DarkColors else LightColors
    MaterialTheme(
        colorScheme = color,
        content = content
    )
}
