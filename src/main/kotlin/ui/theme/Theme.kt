package ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Premium VPN-style color scheme
object XDNSColors {
    // Primary colors
    val primaryDark = Color(0xFF0A1C2B)      // Very dark blue (background)
    val primary = Color(0xFF0F375F)          // Deep blue (primary surfaces)
    val primaryLight = Color(0xFF1268B3)      // Brighter blue (active items)
    
    // Accent colors
    val accentRed = Color(0xFFC3073F)        // Deep red for important actions
    val accentCyan = Color(0xFF00C2CB)       // Cyan for highlights and focus
    
    // Status colors
    val success = Color(0xFF2DCE89)          // Green for success states
    val warning = Color(0xFFFB6340)          // Orange for warnings
    val error = Color(0xFFF5365C)            // Red for errors
    
    // Text and content colors
    val onDarkSurface = Color(0xFFE9ECEF)    // Almost white for text on dark
    val onLightSurface = Color(0xFF2D3748)   // Dark gray for text on light
    val disabled = Color(0xFF6C757D)         // Gray for disabled states
    
    // Surface and container variants
    val surfaceDark = Color(0xFF11263C)      // Slightly lighter than background
    val surfaceLight = Color(0xFF1B395F)     // Container surfaces
    val cardBackground = Color(0xFF0E2845)   // Card backgrounds
    
    // Gradient endpoints (for background gradients)
    val gradientStart = Color(0xFF0A1C2B)
    val gradientEnd = Color(0xFF163A5F)
}

// Premium typography
val XDNSTypography = Typography(
    h1 = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        letterSpacing = (-1).sp
    ),
    h2 = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    h4 = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),
    h5 = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    h6 = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    button = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    caption = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    overline = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp
    )
)

// Material theme colors
private val XDNSThemeColors = darkColors(
    primary = XDNSColors.primaryLight,
    primaryVariant = XDNSColors.accentCyan,
    secondary = XDNSColors.accentRed,
    background = XDNSColors.primaryDark,
    surface = XDNSColors.surfaceDark,
    error = XDNSColors.error,
    onPrimary = XDNSColors.onDarkSurface,
    onSecondary = XDNSColors.onDarkSurface,
    onBackground = XDNSColors.onDarkSurface,
    onSurface = XDNSColors.onDarkSurface,
    onError = XDNSColors.onDarkSurface
)

// App theme wrapper
@Composable
fun XDNSTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = XDNSThemeColors,
        typography = XDNSTypography,
        content = content
    )
} 