package com.msenetho.winnie_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = HoneyLight,
    onPrimary = DarkBrown,

    secondary = PoohRedLight,
    onSecondary = DarkBrown,

    tertiary = ForestGreenLight,
    onTertiary = DarkBrown,

    background = DarkBrown,
    onBackground = SoftCream,

    surface = Color(0xFF403027),
    onSurface = SoftCream,

    surfaceVariant = Color(0xFF594238),
    onSurfaceVariant = Color(0xFFE5D5C0),

    outline = Color(0xFF9C8370)
)

private val LightColorScheme = lightColorScheme(
    primary = HoneyGold,
    onPrimary = DarkBrown,

    primaryContainer = HoneyLight,
    onPrimaryContainer = DarkBrown,

    secondary = PoohRed,
    onSecondary = White,

    secondaryContainer = PoohRedLight,
    onSecondaryContainer = DarkBrown,

    tertiary = ForestGreen,
    onTertiary = White,

    tertiaryContainer = ForestGreenLight,
    onTertiaryContainer = DarkBrown,

    background = Parchment,
    onBackground = PoohBrown,

    surface = SoftCream,
    onSurface = PoohBrown,

    surfaceVariant = Cream,
    onSurfaceVariant = MediumBrown,

    outline = MediumBrown
)

@Composable
fun WinnieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}