package repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object DnsRepository {

    /**
     * Executes a command and provides the output through a callback.
     */
    suspend fun executeCommand(
        command: Array<String>,
        onOutput: (String) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {
            val process = ProcessBuilder(*command)
                .redirectErrorStream(true)
                .start()

            val output = BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                reader.readText()
            }
            
            val exitCode = process.waitFor()
            val result = if (exitCode == 0) {
                "Success: ${parseOutput(output)}"
            } else {
                "Error ($exitCode): ${parseError(output)}"
            }
            onOutput(result)
        } catch (e: Exception) {
            onOutput("Error: ${parseError(e.message ?: "Unknown error")}")
        }
    }

    /**
     * Executes a command and returns the output directly.
     * This is a non-suspending function version for compatibility with older code.
     */
    fun executeCommand(
        command: Array<String>,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onOutput: (String) -> Unit
    ) {
        Thread {
            try {
                onStart()
                val process = ProcessBuilder(*command)
                    .redirectErrorStream(true)
                    .start()

                val output = BufferedReader(InputStreamReader(process.inputStream)).use {
                    it.readText()
                }

                val exitCode = process.waitFor()
                val result = if (exitCode == 0) {
                    "Success: ${parseOutput(output)}"
                } else {
                    "Error ($exitCode): ${parseError(output)}"
                }
                onOutput(result)
            } catch (e: Exception) {
                onOutput("Error: ${parseError(e.message ?: "Unknown error")}")
            } finally {
                onComplete()
            }
        }.start()
    }

    fun parseOutput(output: String): String {
        return when {
            output.contains("Access is denied") ->
                "Administrator privileges required. Please run as Administrator."

            output.contains("Set-DnsClientServerAddress") ->
                "DNS settings updated successfully"

            else -> output.trim()
        }
    }

    fun parseError(error: String): String {
        return when {
            error.contains("Access is denied") ->
                "Permission denied. Right-click and 'Run as Administrator'"

            error.contains("No matching interfaces") ->
                "No active network adapters found"

            else -> error.trim()
        }
    }

    /**
     * Fetches current DNS settings.
     */
    suspend fun fetchCurrentDns(): String = withContext(Dispatchers.IO) {
        try {
            val process = ProcessBuilder(
                "powershell.exe", "-Command",
                "Get-DnsClientServerAddress -AddressFamily IPv4 " +
                        "| Select-Object -ExpandProperty ServerAddresses"
            ).redirectErrorStream(true).start()

            val output = BufferedReader(InputStreamReader(process.inputStream)).use {
                it.readText()
            }
            process.waitFor()

            output.trim().ifEmpty { "Not set (DHCP)" }
        } catch (e: Exception) {
            "Error fetching DNS: ${e.message}"
        }
    }

    /**
     * Runs a ping test to the specified DNS server
     * 
     * @param target The DNS server IP address to ping
     */
    suspend fun runPingTest(target: String = "8.8.8.8"): String = withContext(Dispatchers.IO) {
        try {
            val process = ProcessBuilder("ping", target, "-n", "3")
                .redirectErrorStream(true)
                .start()

            val output = BufferedReader(InputStreamReader(process.inputStream)).use {
                it.readText()
            }
            process.waitFor()

            output.trim()
        } catch (e: Exception) {
            "Ping test failed: ${e.message}"
        }
    }

    /**
     * Checks if the application is running with administrator privileges.
     */
    fun isAdmin(): Boolean {
        return try {
            // "net session" returns exit value 0 only when run as admin.
            val process = ProcessBuilder("net", "session").start()
            process.waitFor()
            process.exitValue() == 0
        } catch (e: Exception) {
            false
        }
    }
}
