package ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * A user-friendly navigation component for DNS server categories
 */
@Composable
fun DnsNavigation(
    categories: List<String>,
    selectedIndex: Int,
    onCategorySelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            categories.forEachIndexed { index, category ->
                // Determine icon based on category
                val icon = when (category) {
                    "Sanctions" -> Icons.Default.NetworkCheck
                    "Speed" -> Icons.Default.Speed
                    "Custom" -> Icons.Default.Settings
                    else -> Icons.Default.Dns
                }
                
                val isSelected = index == selectedIndex
                
                // Category button
                NavigationButton(
                    text = category,
                    icon = icon,
                    isSelected = isSelected,
                    onClick = { onCategorySelected(index) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun NavigationButton(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) 
        MaterialTheme.colors.primary.copy(alpha = 0.15f) 
    else 
        Color.Transparent
    
    val textColor = if (isSelected) 
        MaterialTheme.colors.primary 
    else 
        MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        
    val shape = RoundedCornerShape(8.dp)
    
    Box(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .clip(shape)
            .background(backgroundColor)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.5f) else Color.Transparent,
                shape = shape
            )
            .then(Modifier.clickable(onClick = onClick))
            .padding(vertical = 6.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = textColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                ),
                color = textColor
            )
        }
    }
} 