package ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import ui.theme.XDNSColors

/**
 * A gradient background component that provides a premium feel to the app.
 * 
 * @param startColor The starting color of the gradient
 * @param endColor The ending color of the gradient
 * @param content The content to be displayed on top of the gradient
 */
@Composable
fun GradientBackground(
    startColor: Color = XDNSColors.gradientStart,
    endColor: Color = XDNSColors.gradientEnd,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(startColor, endColor)
                )
            ),
        content = content
    )
} 