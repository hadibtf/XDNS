package ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * A compact status indicator that shows messages
 */
@Composable
fun StatusIndicator(
    statusMessage: String,
    isLoading: Boolean,
    onPingTest: () -> Unit = {}  // Parameter kept for backward compatibility
) {
    AnimatedVisibility(
        visible = statusMessage.isNotBlank(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Determine icon based on message content
            val (icon, tint) = when {
                statusMessage.contains("Success", ignoreCase = true) -> 
                    Pair(Icons.Default.CheckCircle, Color(0xFF2DCE89))
                statusMessage.contains("Error", ignoreCase = true) -> 
                    Pair(Icons.Default.Error, MaterialTheme.colors.error)
                else -> 
                    Pair(Icons.Default.Info, MaterialTheme.colors.primary)
            }
            
            Icon(
                imageVector = icon,
                contentDescription = "Status",
                tint = tint,
                modifier = Modifier.size(16.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = statusMessage.replace("Success: ", "").replace("Error: ", ""),
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (statusMessage.contains("Error", ignoreCase = true)) 
                    MaterialTheme.colors.error else MaterialTheme.colors.onBackground
            )
        }
    }
} 