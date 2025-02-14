package ui.composables
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrentDnsDisplay(currentDns: String = "1.1.1.1") {
    Box(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("Current DNS:", fontWeight = FontWeight.Bold)
            Text(currentDns, fontSize = 12.sp)
        }
    }
}

@Preview
@Composable
fun CurrentDnsDisplayPreview() {
    MaterialTheme {
        CurrentDnsDisplay()
    }
}