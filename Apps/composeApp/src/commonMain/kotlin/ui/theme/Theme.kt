package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ui.drawable.Shapes
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.currentComposer
import ui.colors.DarkThemeColors
import ui.colors.LightThemeColors
import ui.colors.LocalAppColors
import ui.colors.ProvideAppColors
import ui.dimens.Dimensions
import ui.dimens.LocalAppDimens
import ui.dimens.ProvideDimens
import ui.dimens.smallDimensions
import ui.dimens.sw360Dimensions


@Composable
fun App(user: String) {
    CompositionLocalProvider() {
        currentComposer
    }
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    screenWidthDp:Int = 360,
    content: @Composable() () -> Unit,
) {
    val colors = if (darkTheme) DarkThemeColors else LightThemeColors
    val dimensions = if (screenWidthDp <= 360) smallDimensions else sw360Dimensions

    ProvideDimens(dimensions = dimensions) {
        ProvideAppColors(colors = colors) {
            MaterialTheme(
                colorScheme = colors,
                shapes = Shapes,
//                typography = typography,
                content = content,
            )
        }
    }
}

object Theme {
    val colors: ColorScheme
        @Composable
        get() = LocalAppColors.current

    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current
}
