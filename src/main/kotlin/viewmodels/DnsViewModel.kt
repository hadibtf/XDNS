package viewmodels

import androidx.compose.runtime.*
import data.DnsConfig
import data.sanctionsDns
import data.speedDns
import repositories.DnsRepository

class DnsViewModel {

    // UI States
    var outputText by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var tabIndex by mutableStateOf(0)
    var currentDns by mutableStateOf("Checking...")

    val currentList: List<DnsConfig>
        get() = if (tabIndex == 0) sanctionsDns else speedDns

    init {
        // Fetch the DNS once the ViewModel is created
        fetchCurrentDns()
    }

    fun fetchCurrentDns() {
        DnsRepository.fetchCurrentDns {
            currentDns = it
        }
    }

    fun runPingTest() {
        DnsRepository.runPingTest { result ->
            outputText += "\nPing test result:\n$result"
        }
    }

    fun tabIndex(index: Int) {
        tabIndex = index
    }

    fun executeCommand(command: Array<String>) {
        // Keep the original approach, just delegate to the repository
        DnsRepository.executeCommand(
            command = command,
            onStart = { isLoading = true },
            onComplete = {
                isLoading = false
                fetchCurrentDns()
                runPingTest()
            },
            onOutput = { output -> outputText = output }
        )
    }

    fun resetDns() {
        executeCommand(
            arrayOf(
                "powershell.exe", "-ExecutionPolicy", "Bypass", "-Command",
                "Get-NetAdapter | Where-Object { \$_.Status -eq 'Up' } " +
                        "| ForEach-Object { Set-DnsClientServerAddress " +
                        "-InterfaceIndex \$_.InterfaceIndex -ResetServerAddresses }"
            )
        )
    }

    fun clearCache() {
        // You mentioned not to change how commands run, so keep it as is:
        DnsRepository.executeCommand(arrayOf("cmd.exe", "/c", "ipconfig", "/flushdns"), {}, {}, {})
        DnsRepository.executeCommand(
            arrayOf("powershell.exe", "-ExecutionPolicy", "Bypass", "-Command", "Clear-DnsClientCache"),
            {}, {}, {}
        )
        outputText = "DNS cache cleared"
    }
}
