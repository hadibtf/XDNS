package ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Speed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.DnsConfig

@Composable
fun PremiumDnsCard(
    dns: DnsConfig,
    isActive: Boolean,
    isLoading: Boolean,
    onSelect: () -> Unit
) {
    val cardColor by animateColorAsState(
        targetValue = if (isActive) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        animationSpec = tween(durationMillis = 300)
    )
    
    val textColor by animateColorAsState(
        targetValue = if (isActive) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
        animationSpec = tween(durationMillis = 300)
    )
    
    val secondaryTextColor by animateColorAsState(
        targetValue = if (isActive) 
            MaterialTheme.colors.onPrimary.copy(alpha = 0.8f) 
        else 
            MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        animationSpec = tween(durationMillis = 300)
    )
    
    val scale by animateFloatAsState(
        targetValue = if (isActive) 1.02f else 1f,
        animationSpec = tween(durationMillis = 300)
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (isLoading) 0.7f else 1f,
        animationSpec = tween(durationMillis = 200)
    )
    
    // Determine icon based on category
    val icon: ImageVector = when {
        dns.category.contains("Sanctions", ignoreCase = true) -> Icons.Default.NetworkCheck
        dns.category.contains("Speed", ignoreCase = true) -> Icons.Default.Speed
        else -> Icons.Default.Language
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .scale(scale)
            .alpha(alpha),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = cardColor,
        border = if (isActive) BorderStroke(1.dp, MaterialTheme.colors.primaryVariant) else null,
        elevation = if (isActive) 4.dp else 2.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = icon,
                contentDescription = "${dns.category} DNS",
                tint = if (isActive) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary,
                modifier = Modifier.size(24.dp)
            )
            
            // DNS details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = dns.name,
                    style = MaterialTheme.typography.h6,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold
                )
                
                Spacer(modifier = Modifier.height(2.dp))
                
                Row {
                    Text(
                        text = "P: ${dns.primary}",
                        style = MaterialTheme.typography.caption,
                        color = secondaryTextColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "S: ${dns.secondary}",
                        style = MaterialTheme.typography.caption,
                        color = secondaryTextColor
                    )
                }
            }
            
            // Select/Active button
            if (isActive) {
                // Active indicator with matching style
                Button(
                    onClick = {},  // Non-functional button
                    enabled = false,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        contentColor = Color.White, // Force white text
                        disabledBackgroundColor = MaterialTheme.colors.primaryVariant,
                        disabledContentColor = Color.White // Force white text when disabled
                    ),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Active",
                        modifier = Modifier.size(14.dp),
                        tint = Color.White // Force white icon
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Active",
                        style = MaterialTheme.typography.button,
                        color = Color.White // Force white text
                    )
                }
            } else {
                // Select button
                Button(
                    onClick = onSelect,
                    enabled = !isLoading,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary,
                        disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.4f)
                    ),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 4.dp
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Select",
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
} 