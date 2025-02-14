package ui.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.DnsConfig
import data.sanctionsDns

@Composable
fun DnsEntry(dns: DnsConfig = sanctionsDns[0], isLoading: Boolean = false, onExecute: (Array<String>) -> Unit = {}) {
    Card(modifier = Modifier.fillMaxWidth().height(85.dp).padding(vertical = 4.dp),elevation = 12.dp,) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Default.Star, contentDescription = "DNS", tint = Color(0xffffaf63))
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(3f)) {
                Text(dns.name, fontWeight = FontWeight.Bold)
                Text("Primary: ${dns.primary}", fontSize = 12.sp, color = Color.Gray)
                Text("Secondary: ${dns.secondary}", fontSize = 12.sp, color = Color.Gray)
            }
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
                modifier = Modifier.weight(1f).fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xffffaf63),
                    contentColor = Color(0xaa000000)
                )
            ) {
                Text("Set")
            }
        }
    }
}

@Preview()
@Composable
fun DnsEntryPreview() {
    MaterialTheme {
        DnsEntry()
    }
}