package com.theme

/**
 * Created by Ashwani Kumar Singh on 04,December,2023.
 */

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat



@Composable
actual fun AppTheme(
    isDark: Boolean,
    content: @Composable () -> Unit
) {
    val color = if(isDark) DarkColors else LightColors

    val view = LocalView.current
    if(!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = color.primary.toArgb()
            window.navigationBarColor = color.primary.toArgb()
            WindowCompat.getInsetsController(
                window,
                view
            ).isAppearanceLightStatusBars = isDark
        }
    }
    MaterialTheme(
        colorScheme = color,
        content = content
    )
}