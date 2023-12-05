package com.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import platform.UIKit.UIColor

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
fun Color.toUIColor(): UIColor {
    val red = (this.red * 255).toDouble()
    val green = (this.green * 255).toDouble()
    val blue = (this.blue * 255).toDouble()
    val alpha = (this.alpha * 255).toDouble()

    return UIColor(red = red, green = green, blue = blue, alpha = alpha)
}