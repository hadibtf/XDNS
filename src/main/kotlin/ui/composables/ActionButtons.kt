package ui.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Restore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtons(
    isLoading: Boolean = false,
    onReset: () -> Unit = {},
    onClearCache: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Reset DNS Button
        Button(
            onClick = onReset,
            enabled = !isLoading,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary,
                disabledBackgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.3f)
            )
        ) {
            Icon(
                Icons.Default.Restore,
                contentDescription = "Reset DNS",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Reset DNS")
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Clear DNS Cache Button
        OutlinedButton(
            onClick = onClearCache,
            enabled = !isLoading,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.primaryVariant,
                disabledContentColor = MaterialTheme.colors.primaryVariant.copy(alpha = 0.3f)
            )
        ) {
            Icon(
                Icons.Default.DeleteSweep,
                contentDescription = "Clear Cache",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Clear Cache")
        }
    }
}

@Preview
@Composable
fun ActionButtonsPreview() {
    MaterialTheme {
        ActionButtons()
    }
}