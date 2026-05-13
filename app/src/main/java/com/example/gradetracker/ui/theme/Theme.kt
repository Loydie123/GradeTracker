package com.example.gradetracker.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,
    secondary = SecondaryBlue,
    onSecondary = White,
    secondaryContainer = Blue95,
    onSecondaryContainer = Blue20,
    tertiary = AccentGreen,
    onTertiary = White,
    tertiaryContainer = Color(0xFFB8F5B8),
    onTertiaryContainer = Color(0xFF002204),
    error = AccentRed,
    onError = White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = BackgroundLight,
    onBackground = Black,
    surface = SurfaceLight,
    onSurface = Black,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = DarkGray,
    outline = Gray
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlueLight,
    onPrimary = Blue10,
    primaryContainer = Blue30,
    onPrimaryContainer = Blue90,
    secondary = SecondaryBlueLight,
    onSecondary = Blue10,
    secondaryContainer = Blue30,
    onSecondaryContainer = Blue90,
    tertiary = Color(0xFF81C784),
    onTertiary = Color(0xFF003909),
    tertiaryContainer = Color(0xFF005313),
    onTertiaryContainer = Color(0xFFB8F5B8),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE3E3E3),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE3E3E3),
    surfaceVariant = Color(0xFF2D2D2D),
    onSurfaceVariant = Color(0xFFCACACA),
    outline = Color(0xFF8E8E8E)
)

@Composable
fun GradeTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}