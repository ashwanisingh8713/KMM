package ui.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */

@Composable
fun ProvideAppColors(
    colors: ColorScheme,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    CompositionLocalProvider(LocalAppColors provides colorPalette, content = content)
}

val LocalAppColors = staticCompositionLocalOf {
    LightThemeColors
}