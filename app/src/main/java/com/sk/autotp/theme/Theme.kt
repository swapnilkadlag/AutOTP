package com.sk.autotp.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val darkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val lightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

val isDynamicColorAvailable get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@Composable
fun AutOTPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val colorScheme = when {
        isDynamicColorAvailable && useDynamicTheme -> {
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        darkTheme -> lightColorScheme
        else -> darkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}