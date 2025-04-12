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
    private var rawDnsString by mutableStateOf("")
    
    // Whether DNS is manually set (not DHCP)
    var isProtected by mutableStateOf(false)
    
    // Track active DNS configuration
    var activeDns by mutableStateOf<DnsConfig?>(null)
    
    // Tab categories
    val tabCategories = listOf("Sanctions", "Speed")
    
    // Current list of DNS configurations based on selected tab
    val currentList: List<DnsConfig>
        get() = DnsConfigurations.getDnsByCategory(tabCategories[selectedTabIndex])

    init {
        // Fetch the DNS once the ViewModel is created
        fetchCurrentDns()
    }
    
    fun onTabSelected(index: Int) {
        selectedTabIndex = index
    }

    fun fetchCurrentDns() {
        viewModelScope.launch {
            try {
                isLoading = true
                val result = DnsRepository.fetchCurrentDns()
                rawDnsString = result
                
                // Format DNS entries to avoid duplications
                val dnsEntries = result.lines()
                    .filter { it.isNotBlank() }
                    .distinct()
                    .joinToString("\n")
                
                currentDns = dnsEntries
                
                // Check if current DNS matches any of our configurations and mark as active
                updateActiveDns(result)
                
                // Determine if DNS is protected (not DHCP)
                isProtected = !result.contains("Not set (DHCP)", ignoreCase = true) && 
                              !result.contains("Error", ignoreCase = true)
            } catch (e: Exception) {
                currentDns = "Error fetching DNS"
                isProtected = false
            } finally {
                isLoading = false
            }
        }
    }
    
    private fun updateActiveDns(dnsString: String) {
        activeDns = null
        
        // Try to find matching DNS
        val allDnsList = DnsConfigurations.sanctionsDns + 
                         DnsConfigurations.speedDns
                         
        for (dns in allDnsList) {
            // Check if the current DNS contains both primary and secondary servers
            if (dnsString.contains(dns.primary) && dnsString.contains(dns.secondary)) {
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
    
    // Clean up resources
    fun onCleared() {
        viewModelScope.cancel()
    }
}
