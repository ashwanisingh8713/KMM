package ui.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

/*
https://www.boltuix.com/

Build a custom color scheme to map dynamic color, use as fallback colors,
or implement a branded theme. The color system automatically handles critical adjustments
that provide accessible color contrast.

https://material-foundation.github.io/material-theme-builder/#/custom
*/




val LightThemeColors = ColorScheme(
// Window color and text color(OnPrimary)
    primary = Color(0xFF55AB60),
    onPrimary = Color(0xFF55AB60),

    primaryContainer = Color(0xFFF3FFF5),
    onPrimaryContainer = Color(0xFFFFB3AE),
    inversePrimary = Color(0xFF410004),

    secondary = Color(0xFF775654),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDAD7),
    onSecondaryContainer = Color(0xFF2C1513),

    tertiary = Color(0xFF725B2E),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDEA6),
    onTertiaryContainer = Color(0xFF271900),

    background = Color(0xFFBA1A1A),
    onBackground = Color(0xFFFFDAD6),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF410002),

    surfaceVariant = Color(0xFFFFFBFF),
    onSurfaceVariant = Color(0xFF000000),
    surfaceTint = Color(0xFF000000),

    inverseSurface = Color(0xFF000000),
    inverseOnSurface = Color(0xFF000000),

    error = Color(0xFFFFFBFF),
    onError = Color(0xFF201A1A),
    errorContainer = Color(0xFFF5F5F5),
    onErrorContainer = Color(0xFF534342),

    outline = Color(0xFFFBEEEC),
    outlineVariant = Color(0xFFC0001D),
    scrim = Color(0xFF362F2E),

)

val DarkThemeColors = ColorScheme(
// Window color and text color(OnPrimary)
    primary = Color(0xFF55AB60),
    onPrimary = Color(0xFF55AB60),

    primaryContainer = Color(0xFFF3FFF5),
    onPrimaryContainer = Color(0xFFFFB3AE),
    inversePrimary = Color(0xFF410004),

    secondary = Color(0xFF775654),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDAD7),
    onSecondaryContainer = Color(0xFF2C1513),

    tertiary = Color(0xFF725B2E),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDEA6),
    onTertiaryContainer = Color(0xFF271900),

    background = Color(0xFFBA1A1A),
    onBackground = Color(0xFFFFDAD6),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF410002),

    surfaceVariant = Color(0xFFFFFBFF),
    onSurfaceVariant = Color(0xFF000000),
    surfaceTint = Color(0xFF000000),

    inverseSurface = Color(0xFF000000),
    inverseOnSurface = Color(0xFF000000),

    error = Color(0xFFFFFBFF),
    onError = Color(0xFF201A1A),
    errorContainer = Color(0xFFF5F5F5),
    onErrorContainer = Color(0xFF534342),

    outline = Color(0xFFFBEEEC),
    outlineVariant = Color(0xFFC0001D),
    scrim = Color(0xFF362F2E),

    )

