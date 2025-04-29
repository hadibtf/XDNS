import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.res.painterResource
import repositories.DnsRepository
import ui.App
import ui.composables.AdminRequiredDialog
import ui.theme.XDNSTheme
import java.io.File

fun main() = application {
    // Check if running with admin privileges
    val isAdmin = remember { mutableStateOf(DnsRepository.isAdmin()) }
    
    Window(
        title = "XDNS",
        onCloseRequest = ::exitApplication,
        resizable = false,
        state = WindowState(width = 480.dp, height = 720.dp),
        icon = painterResource("XDNS.ico"),
    ) {
        XDNSTheme {
            if (!isAdmin.value) {
                // Show non-dismissible admin required dialog
                AdminRequiredDialog(onCloseApp = ::exitApplication)
            } else {
                App()
            }
        }
    }
}
