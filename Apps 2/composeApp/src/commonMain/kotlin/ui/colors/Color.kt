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



// Black Color Variant - https://www.color-meanings.com/shades-of-black-color-names-html-hex-rgb-codes/
val LightThemeColors = ColorScheme(
// Window color and text color(OnPrimary)
    primary = Color(0xff6c281b), // Tab-text selected
    onPrimary = Color(0xFF494F55), // Abbey

    primaryContainer = Color(0xFF666362), // Ash
    onPrimaryContainer = Color(0xFF0C0404), // Asphalt
    inversePrimary = Color(0xFF000000), // Black

    secondary = Color(0xFF3D0C02), // Black Bean
    onSecondary = Color(0xFF413839), // Black Cat
    secondaryContainer = Color(0xFFB9D9EB), // NavigationBar-Selected Highlight Area Color
    onSecondaryContainer = Color(0xFF003366), // NavigationBar-Selected Vector Color

    tertiary = Color(0xFF555555), // Davy’s Gray
    onTertiary = Color(0xff1a1313), // Dim Gray
    tertiaryContainer = Color(0xFFFF00FF), // Magenta
    onTertiaryContainer = Color(0xFFFF00FF), // Magenta

    background = Color(0xFFFBFCF8),  // View Pager - Window background,
    onBackground = Color(0xff6c281b), // TextLabel Color,

    surface = Color(0xFFFFFFFF), // TopAppbar-Background Color, NavigationBar-Background Color, TabBar-Background Color
    onSurface = Color(0xFF012169), // TopAppbar-RightIcon-Title Color, NavigationBar-Selected Text Color

    surfaceVariant = Color(0xFFFBFCF8), // Card background
    onSurfaceVariant = Color(0xFF003366),  // Tab-Text Non-Selected, Vector Icons Non-Selected color, NavigationBar-Non Selected Text Color
    surfaceTint = Color(0xFFFFFFFF),

    inverseSurface = Color(0xFFB0E0E6), // Powder blue
    inverseOnSurface = Color(0xFF7DF9FF), // Electric blue

    error = Color(0xFF5D8AA8), // Air Force Blue (RAF)
    onError = Color(0xFF00308F),    // Air Force blue (USAF)
    errorContainer = Color(0xFFBCD4E6), // Baby Blue
    onErrorContainer = Color(0xFFFFFD37), // Sunshine Yellow

    outline = Color(0xFFFF2400), // Scarlet Red
    outlineVariant = Color(0xFF003366), // TabBar-Bottom-Indicator Divider Color
    scrim = Color(0xFF7C0A02), // Barn Red,



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

