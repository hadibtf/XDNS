import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.res.painterResource
import repositories.DnsRepository
import ui.App
import ui.theme.XDNSTheme

fun main() = application {
    // Check admin privileges first
    if (!DnsRepository.isAdmin()) {
        Window(
            title = "XDNS - Administrator Privileges Required",
            onCloseRequest = ::exitApplication,
            resizable = false,
            state = WindowState(width = 480.dp, height = 280.dp),
            icon = painterResource("XDNS.ico")
        ) {
            XDNSTheme {
                Box(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Administrator privileges are required to modify DNS settings.\n\n" +
                               "Please right-click on the application shortcut and select 'Run as administrator'.",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
        return@application
    }

    Window(
        title = "XDNS Premium",
        onCloseRequest = ::exitApplication,
        resizable = false,
        state = WindowState(width = 480.dp, height = 720.dp),
        icon = painterResource("XDNS.ico"),
    ) {
        XDNSTheme {
            App()
        }
    }
}
