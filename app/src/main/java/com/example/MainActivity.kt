package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.VodafoneTopBar
import com.example.ui.screens.BillsScreen
import com.example.ui.screens.HappyScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PackagesScreen
import com.example.ui.screens.TobiBottomSheet
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.VodafoneHappyOrange
import com.example.ui.theme.VodafoneRed
import com.example.ui.theme.VodafoneTextPrimary
import com.example.ui.theme.VodafoneTextSecondary
import com.example.viewmodel.VodafoneViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val viewModel: VodafoneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                VodafoneApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun VodafoneApp(viewModel: VodafoneViewModel) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val userAccount by viewModel.userAccount.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val selectedHappyCategory by viewModel.selectedHappyCategory.collectAsStateWithLifecycle()

    val wheelRotation by viewModel.wheelRotation.collectAsStateWithLifecycle()
    val isSpinning by viewModel.isSpinning.collectAsStateWithLifecycle()
    val wonPrize by viewModel.wonPrize.collectAsStateWithLifecycle()
    val showPrizeDialog by viewModel.showPrizeDialog.collectAsStateWithLifecycle()

    val isSurpriseBoxOpened by viewModel.isSurpriseBoxOpened.collectAsStateWithLifecycle()
    val surpriseBoxReward by viewModel.surpriseBoxReward.collectAsStateWithLifecycle()

    val happyOffers by viewModel.happyOffers.collectAsStateWithLifecycle()
    val billHistory by viewModel.billHistory.collectAsStateWithLifecycle()

    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val showTobiDialog by viewModel.showTobiDialog.collectAsStateWithLifecycle()

    // Listen for toast messages
    LaunchedEffect(Unit) {
        viewModel.toastMessage.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            VodafoneTopBar(
                userAccount = userAccount,
                onOpenTobi = { viewModel.openTobi() },
                onHappyClick = { viewModel.setTab(1) }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_nav_bar")
            ) {
                // Tab 0: Ana Sayfa
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { viewModel.setTab(0) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 0) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "Ana Sayfa"
                        )
                    },
                    label = {
                        Text(
                            text = "Ana Sayfa",
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = VodafoneRed,
                        selectedTextColor = VodafoneRed,
                        indicatorColor = VodafoneRed.copy(alpha = 0.12f),
                        unselectedIconColor = VodafoneTextSecondary,
                        unselectedTextColor = VodafoneTextSecondary
                    ),
                    modifier = Modifier.testTag("tab_home")
                )

                // Tab 1: Vodafone Happy
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { viewModel.setTab(1) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 1) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome,
                            contentDescription = "Vodafone Happy"
                        )
                    },
                    label = {
                        Text(
                            text = "Happy",
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = VodafoneHappyOrange,
                        selectedTextColor = VodafoneRed,
                        indicatorColor = VodafoneHappyOrange.copy(alpha = 0.15f),
                        unselectedIconColor = VodafoneHappyOrange,
                        unselectedTextColor = VodafoneTextSecondary
                    ),
                    modifier = Modifier.testTag("tab_happy")
                )

                // Tab 2: Paketler
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { viewModel.setTab(2) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 2) Icons.Filled.AddShoppingCart else Icons.Outlined.AddShoppingCart,
                            contentDescription = "Paketler"
                        )
                    },
                    label = {
                        Text(
                            text = "Paketler",
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = VodafoneRed,
                        selectedTextColor = VodafoneRed,
                        indicatorColor = VodafoneRed.copy(alpha = 0.12f),
                        unselectedIconColor = VodafoneTextSecondary,
                        unselectedTextColor = VodafoneTextSecondary
                    ),
                    modifier = Modifier.testTag("tab_packages")
                )

                // Tab 3: Faturam
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { viewModel.setTab(3) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 3) Icons.Filled.ReceiptLong else Icons.Outlined.ReceiptLong,
                            contentDescription = "Faturam"
                        )
                    },
                    label = {
                        Text(
                            text = "Faturam",
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = VodafoneRed,
                        selectedTextColor = VodafoneRed,
                        indicatorColor = VodafoneRed.copy(alpha = 0.12f),
                        unselectedIconColor = VodafoneTextSecondary,
                        unselectedTextColor = VodafoneTextSecondary
                    ),
                    modifier = Modifier.testTag("tab_bills")
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(
                    userAccount = userAccount,
                    onNavigateToHappy = { viewModel.setTab(1) },
                    onNavigateToPackages = { viewModel.setTab(2) },
                    onNavigateToBills = { viewModel.setTab(3) },
                    onPayBill = { viewModel.payBill() }
                )
                1 -> HappyScreen(
                    userAccount = userAccount,
                    wheelPrizes = viewModel.wheelPrizes,
                    wheelRotation = wheelRotation,
                    isSpinning = isSpinning,
                    wonPrize = wonPrize,
                    showPrizeDialog = showPrizeDialog,
                    isSurpriseBoxOpened = isSurpriseBoxOpened,
                    surpriseBoxReward = surpriseBoxReward,
                    happyOffers = happyOffers,
                    selectedCategory = selectedHappyCategory,
                    onCategorySelect = { viewModel.setHappyCategory(it) },
                    onSpinWheel = { viewModel.spinWheel() },
                    onClosePrizeDialog = { viewModel.closePrizeDialog() },
                    onOpenSurpriseBox = { viewModel.openSurpriseBox() },
                    onClaimOffer = { viewModel.claimOffer(it) }
                )
                2 -> PackagesScreen(
                    userAccount = userAccount,
                    packages = viewModel.extraPackages,
                    onPurchasePackage = { viewModel.purchasePackage(it) }
                )
                3 -> BillsScreen(
                    userAccount = userAccount,
                    billHistory = billHistory,
                    onPayBill = { viewModel.payBill() }
                )
            }
        }
    }

    // TOBi Digital Assistant BottomSheet
    if (showTobiDialog) {
        TobiBottomSheet(
            messages = chatMessages,
            onSendMessage = { viewModel.sendTobiMessage(it) },
            onDismiss = { viewModel.closeTobi() }
        )
    }
}

