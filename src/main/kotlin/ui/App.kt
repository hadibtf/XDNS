package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import viewmodels.DnsViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.composables.ActionButtons
import ui.composables.CurrentDnsDisplay
import ui.composables.DnsEntry
import ui.composables.OutputText

@Composable
fun App(viewModel: DnsViewModel = remember { DnsViewModel() }) {
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            // Tabs
            TabRow(selectedTabIndex = viewModel.tabIndex, backgroundColor = Color(0xffffaf63)) {
                listOf("Sanctions", "Download Speed").forEachIndexed { index, title ->
                    Tab(
                        selected = viewModel.tabIndex == index,
                        onClick = { viewModel.tabIndex(index) },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(title, modifier = Modifier.padding(8.dp))
                    }
                }
            }

            // Current DNS Display
            CurrentDnsDisplay(viewModel.currentDns)

            // Revert/Flush Buttons
            ActionButtons(
                isLoading = viewModel.isLoading,
                onReset = { viewModel.resetDns() },
                onClearCache = { viewModel.clearCache() }
            )

            Spacer(modifier = Modifier.height(8.dp))

            //Loading Indicator
            if (viewModel.isLoading) {
                CircularProgressIndicator(backgroundColor = Color(0xffffaf63))
            }
            //Action result
            OutputText(viewModel.outputText)

            // DNS Option Buttons
            viewModel.currentList.forEach { dns ->
                DnsEntry(
                    dns = dns,
                    isLoading = viewModel.isLoading,
                    onExecute = { command -> viewModel.executeCommand(command) }
                )
            }
        }
    }
}

@Composable
@Preview
fun AppPreview() {
    MaterialTheme {
        App()
    }
}