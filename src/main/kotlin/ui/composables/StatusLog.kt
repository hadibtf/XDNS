package ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * A component to display operation logs and status messages.
 */
@Composable
fun StatusLog(
    text: String,
    isVisible: Boolean = true
) {
    AnimatedVisibility(
        visible = isVisible && text.isNotBlank(),
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.8f),
            elevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Log header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = "System Log",
                        tint = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "System Log",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                
                Divider(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
                    thickness = 1.dp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Log content
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colors.background.copy(alpha = 0.5f)
                ) {
                    Box(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        // Determine log text color based on content
                        val textColor = when {
                            text.contains("Success", ignoreCase = true) -> Color(0xFF2DCE89)
                            text.contains("Error", ignoreCase = true) -> MaterialTheme.colors.error
                            else -> MaterialTheme.colors.onSurface
                        }
                        
                        // Display log text
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body2,
                            color = textColor,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
                
                // Show warning icon for errors
                if (text.contains("Error", ignoreCase = true)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = "Error",
                            tint = MaterialTheme.colors.error,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "An error occurred during the operation",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.error
                        )
                    }
                }
                
                // Show success icon for success
                if (text.contains("Success", ignoreCase = true)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Success",
                            tint = Color(0xFF2DCE89),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Operation completed successfully",
                            style = MaterialTheme.typography.caption,
                            color = Color(0xFF2DCE89)
                        )
                    }
                }
            }
        }
    }
} 