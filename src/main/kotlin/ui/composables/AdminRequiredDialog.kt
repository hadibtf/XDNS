package ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AdminRequiredDialog(
    onCloseApp: () -> Unit
) {
    Dialog(
        onDismissRequest = { /* Cannot be dismissed */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            modifier = Modifier
                .width(420.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Administrator Rights Required",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Main message
                Text(
                    text = "This application modifies system network settings and requires administrator privileges.",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Instructions
                Text(
                    text = "Please restart the app with administrator rights using one of the following methods:",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Temporary method
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "1. Temporary Method:",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "• Right-click on the app shortcut or executable.",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "• Select \"Run as administrator\".",
                        style = MaterialTheme.typography.body2
                    )
                }
                
                // Permanent method
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "2. Permanent Method:",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "• Right-click on the app shortcut.",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "• Select \"Properties\".",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "• Go to the \"Compatibility\" tab.",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "• Check \"Run this program as an administrator\".",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "• Click \"Apply\", then \"OK\".",
                        style = MaterialTheme.typography.body2
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "After applying one of these methods, relaunch the app.",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Close button
                Button(
                    onClick = onCloseApp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = "Close App",
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
} 