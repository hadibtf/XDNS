package viewmodels

import androidx.compose.runtime.*
import data.DnsConfig
import data.DnsConfigurations
import kotlinx.coroutines.*
import repositories.DnsRepository

class DnsViewModel {
    // Coroutine scope for background operations
    private val viewModelScope = CoroutineScope(Dispatchers.Default)
    
    // UI States
    var outputText by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var selectedTabIndex by mutableStateOf(0)
    
    // Current DNS state (formatted to avoid duplication)
    var currentDns by mutableStateOf("Checking...")
    var primaryDns by mutableStateOf("")
    var secondaryDns by mutableStateOf("")
    private var rawDnsString by mutableStateOf("")
    
    // Protection status
    var protectionStatus by mutableStateOf("Not Protected")
    var isProtected by mutableStateOf(false)
    
    // Track active DNS configuration
    var activeDns by mutableStateOf<DnsConfig?>(null)
    
    // Tab categories
    val tabCategories = listOf("Sanctions", "Speed")
    
    // Current list of DNS configurations based on selected tab
    val currentList: List<DnsConfig>
        get() = DnsConfigurations.getDnsByCategory(tabCategories[selectedTabIndex])

    fun onTabSelected(index: Int) {
        selectedTabIndex = index
    }

    fun fetchCurrentDns() {
        viewModelScope.launch {
            try {
                isLoading = true
                val result = DnsRepository.fetchCurrentDns()
                rawDnsString = result

                // Update DNS entries and status
                updateDnsDisplay(result)

                // Check if current DNS matches any of our configurations and mark as active
                updateActiveDns(result)

                // Update protection status
                updateProtectionStatus()
            } catch (e: Exception) {
                currentDns = "Error fetching DNS"
                primaryDns = "Error"
                secondaryDns = "Error"
                protectionStatus = "Not Protected"
                isProtected = false
            } finally {
                isLoading = false
            }
        }
    }

    private fun updateDnsDisplay(dnsString: String) {
        // Format DNS entries to avoid duplications
        val dnsEntries = dnsString.lines()
            .filter { it.isNotBlank() }
            .distinct()
            .joinToString("\n")
        
        currentDns = dnsEntries
        
        // Extract primary and secondary DNS for display
        val dnsLines = dnsString.lines().filter { it.isNotBlank() }.distinct()
        primaryDns = if (dnsLines.isNotEmpty()) dnsLines[0] else "Not set (DHCP)"
        secondaryDns = if (dnsLines.size > 1) dnsLines[1] else ""
        
        // Determine if DNS is protected (not DHCP)
        isProtected = !dnsString.contains("Not set (DHCP)", ignoreCase = true) && 
                      !dnsString.contains("Error", ignoreCase = true)
    }
    
    private fun updateProtectionStatus() {
        protectionStatus = when {
            activeDns != null -> "Protected: ${activeDns!!.name}"
            isProtected -> "Undefined DNS"
            else -> "Not Protected"
        }
    }
    
    private fun updateActiveDns(dnsString: String) {
        activeDns = null
        
        // Try to find matching DNS
        val allDnsList = DnsConfigurations.sanctionsDns + DnsConfigurations.speedDns
                         
        for (dns in allDnsList) {
            // Check if the current DNS contains both primary and secondary servers
            if (dnsString.contains(dns.primary) && 
               (dns.secondary.isEmpty() || dnsString.contains(dns.secondary))) {
                activeDns = dns
                break
            }
        }
    }

    fun executeCommand(command: Array<String>) {
        viewModelScope.launch {
            isLoading = true
            
            try {
                DnsRepository.executeCommand(command) { output ->
                    outputText = output
                }
                fetchCurrentDns()
            } catch (e: Exception) {
                outputText = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
    
    fun setDns(dns: DnsConfig) {
        executeCommand(
            arrayOf(
                "powershell.exe", "-ExecutionPolicy", "Bypass", "-Command",
                "Get-NetAdapter | Where-Object { \$_.Status -eq 'Up' } | " +
                        "ForEach-Object { Set-DnsClientServerAddress " +
                        "-InterfaceIndex \$_.InterfaceIndex " +
                        "-ServerAddresses '${dns.primary}', '${dns.secondary}' }"
            )
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
        viewModelScope.launch {
            isLoading = true
            try {
                DnsRepository.executeCommand(
                    arrayOf("cmd.exe", "/c", "ipconfig", "/flushdns"),
                    onOutput = {}
                )
                
                DnsRepository.executeCommand(
                    arrayOf("powershell.exe", "-ExecutionPolicy", "Bypass", "-Command", "Clear-DnsClientCache"),
                    onOutput = {}
                )
                
                outputText = "Success: DNS cache cleared"
            } catch (e: Exception) {
                outputText = "Error: Failed to clear DNS cache - ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
