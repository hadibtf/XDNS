package repositories

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object DnsRepository {

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

                val output = buildString {
                    BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            appendLine(line)
                        }
                    }
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

    fun fetchCurrentDns(onResult: (String) -> Unit) {
        Thread {
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

                onResult(output.trim().ifEmpty { "Not set (DHCP)" })
            } catch (e: Exception) {
                onResult("Error fetching DNS: ${e.message}")
            }
        }.start()
    }

    fun runPingTest(onResult: (String) -> Unit) {
        Thread {
            try {
                val process = ProcessBuilder("ping", "8.8.8.8", "-n", "3")
                    .redirectErrorStream(true)
                    .start()

                val output = BufferedReader(InputStreamReader(process.inputStream)).use {
                    it.readText()
                }
                process.waitFor()

                onResult(output.trim())
            } catch (e: Exception) {
                onResult("Ping test failed: ${e.message}")
            }
        }.start()
    }

    fun isAdmin(): Boolean {
        return try {
            // "net session" returns exit value 0 only when run as admin.
            val process = ProcessBuilder("net", "session").start()
            process.waitFor()
            process.exitValue() == 0
        } catch (e: java.lang.Exception) {
            false
        }
    }
}
