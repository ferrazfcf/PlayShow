package com.ferraz.playshow.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LightThemeColors = lightColorScheme(
    primary = SoftRed,
    onPrimary = Color.White,
    secondary = DarkerRed,
    onSecondary = Color.White,
    onSecondaryContainer = LightGray,
    tertiary = MediumGray,
    onTertiary = DarkerGray,
    background = LightGray,
    onBackground = DarkerGray,
    surface = Color.White,
    onSurface = DarkerGray
)

val DarkThemeColors = darkColorScheme(
    primary = SoftRed,
    onPrimary = Color.White,
    secondary = DarkerRed,
    onSecondary = Color.White,
    onSecondaryContainer = DarkerGray,
    tertiary = MediumGray,
    onTertiary = LightGray,
    background = DarkerGray,
    onBackground = LightGray,
    surface = DarkGray,
    onSurface = LightGray
)

@Composable
fun PlayShowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkThemeColors
        else -> LightThemeColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
