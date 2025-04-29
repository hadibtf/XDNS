import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import viewmodels.DnsViewModel
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DnsViewModelTest {
    
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: DnsViewModel
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DnsViewModel()
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
    
    @Test
    fun `protection status should be Not Protected when DNS is not set`() = testDispatcher.runBlockingTest {
        // Given
        val testViewModel = DnsViewModel()
        
        // When
        testViewModel.isProtected = false
        testViewModel.activeDns = null
        
        // Manual call to update protection status
        val updateMethodField = DnsViewModel::class.java.getDeclaredMethod("updateProtectionStatus")
        updateMethodField.isAccessible = true
        updateMethodField.invoke(testViewModel)
        
        // Then
        assertEquals("Not Protected", testViewModel.protectionStatus)
    }
    
    @Test
    fun `protection status should show Undefined DNS when custom DNS is set`() = testDispatcher.runBlockingTest {
        // Given
        val testViewModel = DnsViewModel()
        
        // When
        testViewModel.isProtected = true
        testViewModel.activeDns = null
        
        // Manual call to update protection status
        val updateMethodField = DnsViewModel::class.java.getDeclaredMethod("updateProtectionStatus")
        updateMethodField.isAccessible = true
        updateMethodField.invoke(testViewModel)
        
        // Then
        assertEquals("Undefined DNS", testViewModel.protectionStatus)
    }
    
    @Test
    fun `protection status should show DNS name when app DNS is active`() = testDispatcher.runBlockingTest {
        // Given
        val testViewModel = DnsViewModel()
        
        // When - simulate active DNS with Google
        testViewModel.isProtected = true
        testViewModel.activeDns = data.DnsConfig("Speed", "Google", "8.8.8.8", "8.8.4.4")
        
        // Manual call to update protection status
        val updateMethodField = DnsViewModel::class.java.getDeclaredMethod("updateProtectionStatus")
        updateMethodField.isAccessible = true
        updateMethodField.invoke(testViewModel)
        
        // Then
        assertEquals("Protected: Google", testViewModel.protectionStatus)
    }
    
    @Test
    fun `tab selection should update current list`() {
        // Given default tab (index 0 - Sanctions)
        assertEquals(0, viewModel.selectedTabIndex)
        assertTrue(viewModel.currentList.isNotEmpty())
        assertTrue(viewModel.currentList[0].category == "Sanctions")
        
        // When selecting Speed tab (index 1)
        viewModel.onTabSelected(1)
        
        // Then
        assertEquals(1, viewModel.selectedTabIndex)
        assertTrue(viewModel.currentList.isNotEmpty())
        assertTrue(viewModel.currentList[0].category == "Speed")
    }
} 