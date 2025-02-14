package ui.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onReset: () -> Unit = {},
    onClearCache: () -> Unit = {}
) {
    Column(
        modifier = modifier.height(86.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            modifier = modifier.weight(0.45f).fillMaxWidth(),
            onClick = onReset,
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffafd8ff))
        ) {
            Text("Revert to DHCP")
        }
        Spacer(modifier = Modifier.weight(0.1f))
        Button(
            modifier = modifier.weight(0.45f).fillMaxWidth(),
            onClick = onClearCache,
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffccff9f))
        ) {
            Text("Flush DNS Cache")
        }
    }
}

@Composable
@Preview
fun ActionButtonsPreview() {
    MaterialTheme {
        ActionButtons()
    }
}