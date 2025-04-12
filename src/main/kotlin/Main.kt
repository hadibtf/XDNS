import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.res.painterResource
import repositories.DnsRepository.isAdmin
import ui.App
import java.lang.Exception

fun main() = application {
    if (!isAdmin()) {
        Window(
            title = "Administrator Privileges Required",
            onCloseRequest = ::exitApplication,
            resizable = false,
            state = WindowState(width = 400.dp, height = 200.dp)
        ) {
            MaterialTheme {
                Text(
                    text = "Error: This application must be run as an administrator.\n" +
                            "Please restart the application with administrative privileges.",
                    modifier = androidx.compose.ui.Modifier.padding(16.dp)
                )
            }
        }
        return@application
    }

    // If already running as administrator, continue normal operation.
    // (If you still need to launch an external elevated process, you can retain your ProcessBuilder command here.)
    val command = listOf(
        "powershell.exe",
        "Start-Process",
        "java",
        "-ArgumentList", "'-jar','XDNS-1.0-SNAPSHOT.jar'",
        "-Verb", "runAs"
    )
    ProcessBuilder(command).start()

    Window(
        title = "XDNS",
        onCloseRequest = ::exitApplication,
        resizable = false,
        state = WindowState(width = 350.dp, height = 575.dp),
        icon = painterResource("icon.ico"),
    ) {
        MaterialTheme {
            App()
        }
    }
}
