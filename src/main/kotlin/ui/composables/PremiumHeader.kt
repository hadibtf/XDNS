package ui.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Dns
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

/**
 * A header component that displays the app logo and DNS status.
 *
 * @param currentDns The current DNS configuration
 * @param isProtected Whether the DNS is currently protected/modified (not using DHCP)
 */
@Composable
fun DnsHeader(
    currentDns: String,
    isProtected: Boolean
) {
    val statusScale by animateFloatAsState(
        targetValue = if (isProtected) 1f else 0.95f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo and app name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            Image(
                painter = painterResource("XDNS.ico"),
                contentDescription = "XDNS Logo",
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            
            // XDNS with cyan X
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colors.primaryVariant)) {
                        append("X")
                    }
                    withStyle(style = SpanStyle(color = MaterialTheme.colors.onBackground)) {
                        append("DNS")
                    }
                },
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold
            )
        }

        // Status card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .scale(statusScale),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 3.dp
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Title row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Dns,
                        contentDescription = "Current DNS",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Current DNS",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onSurface
                    )
                    
                    if (isProtected) {
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Protected",
                                tint = Color(0xFF2DCE89),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Protected",
                                style = MaterialTheme.typography.caption,
                                color = Color(0xFF2DCE89)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(6.dp))
                
                // DNS status content
                if (isProtected) {
                    // Process DNS entries to display primary and secondary explicitly
                    val dnsEntries = currentDns.lines().filter { it.isNotBlank() }.distinct()
                    if (dnsEntries.size >= 2) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Primary:",
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colors.onSurface
                                )
                                Text(
                                    text = dnsEntries[0],
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(10.dp))
                            
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Secondary:",
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colors.onSurface
                                )
                                Text(
                                    text = dnsEntries[1],
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
                                )
                            }
                        }
                    } else {
                        // Fallback if we can't parse the entries properly
                        Text(
                            text = currentDns,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
                        )
                    }
                } else {
                    Text(
                        text = "DNS not protected (using DHCP)",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
} 