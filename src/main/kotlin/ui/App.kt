package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import viewmodels.DnsViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.composables.*
import ui.theme.XDNSTheme

@Composable
fun App(viewModel: DnsViewModel = remember { DnsViewModel() }) {
    // The main gradient background
    GradientBackground {
        // Main content column with padding
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with enhanced DNS status display
            CurrentDnsDisplay(
                primaryDns = viewModel.primaryDns,
                secondaryDns = viewModel.secondaryDns,
                protectionStatus = viewModel.protectionStatus
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Compact control panel
            CompactControlPanel(
                isLoading = viewModel.isLoading,
                onResetDns = { viewModel.resetDns() },
                onClearCache = { viewModel.clearCache() },
                onRefreshStatus = { viewModel.fetchCurrentDns() }
            )
            
            // Status indicator
            StatusIndicator(
                statusMessage = viewModel.outputText,
                isLoading = viewModel.isLoading
            )
            
            // DNS navigation component
            DnsNavigation(
                categories = viewModel.tabCategories,
                selectedIndex = viewModel.selectedTabIndex,
                onCategorySelected = { viewModel.onTabSelected(it) }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // DNS list
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(viewModel.currentList) { dns ->
                    PremiumDnsCard(
                        dns = dns,
                        isActive = dns == viewModel.activeDns,
                        isLoading = viewModel.isLoading,
                        onSelect = { viewModel.setDns(dns) }
                    )
                }
            }
            
            // Footer
            Text(
                text = "XDNS v1.1.0",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
            )
        }
    }
    
    // Clean up when disposed
    DisposableEffect(Unit) {
        onDispose {
            viewModel.onCleared()
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    XDNSTheme {
        App()
    }
}