package ui.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.DnsConfig
import data.DnsConfigurations

@Composable
fun DnsEntry(
    dns: DnsConfig = DnsConfigurations.sanctionsDns[0],
    isLoading: Boolean = false,
    onExecute: (Array<String>) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp)
        ) {
            // DNS Icon based on category
            val icon = when (dns.category) {
                "Sanctions" -> Icons.Default.NetworkCheck
                else -> Icons.Default.Dns
            }
            
            Icon(
                imageVector = icon,
                contentDescription = "DNS icon",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.size(24.dp).padding(start = 4.dp)
            )
            
            // DNS Information
            Column(
                modifier = Modifier.weight(3f).padding(horizontal = 12.dp)
            ) {
                Text(
                    text = dns.name,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = "Primary: ${dns.primary}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "Secondary: ${dns.secondary}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            }
            
            // Set DNS Button
            Button(
                onClick = {
                    onExecute(
                        arrayOf(
                            "powershell.exe", "-ExecutionPolicy", "Bypass", "-Command",
                            "Get-NetAdapter | Where-Object { \$_.Status -eq 'Up' } | " +
                                    "ForEach-Object { Set-DnsClientServerAddress " +
                                    "-InterfaceIndex \$_.InterfaceIndex " +
                                    "-ServerAddresses '${dns.primary}', '${dns.secondary}' }"
                        )
                    )
                },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                    disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.3f)
                )
            ) {
                Text("Set")
            }
        }
    }
}

@Preview
@Composable
fun DnsEntryPreview() {
    MaterialTheme {
        DnsEntry()
    }
}