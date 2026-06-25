package com.aselab.basaraha.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PurplePrimary = Color(0xFF7C4DFF)
private val PurpleSecondary = Color(0xFFB388FF)
private val TealAccent = Color(0xFF26A69A)
private val BackgroundLight = Color(0xFFF5F3FF)
private val BackgroundDark = Color(0xFF1A1625)
private val SurfaceLight = Color(0xFFFFFFFF)
private val SurfaceDark = Color(0xFF2D2640)

private val LightColors = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = Color.White,
    secondary = TealAccent,
    onSecondary = Color.White,
    tertiary = PurpleSecondary,
    background = BackgroundLight,
    onBackground = Color(0xFF1C1B1F),
    surface = SurfaceLight,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE8E0FF),
    onSurfaceVariant = Color(0xFF49454F)
)

private val DarkColors = darkColorScheme(
    primary = PurpleSecondary,
    onPrimary = Color(0xFF1A1625),
    secondary = TealAccent,
    onSecondary = Color(0xFF1A1625),
    tertiary = PurplePrimary,
    background = BackgroundDark,
    onBackground = Color(0xFFE6E1E5),
    surface = SurfaceDark,
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF3D3555),
    onSurfaceVariant = Color(0xFFCAC4D0)
)

@Composable
fun AselaBasarahaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = ArabicTypography,
        content = content
    )
}
