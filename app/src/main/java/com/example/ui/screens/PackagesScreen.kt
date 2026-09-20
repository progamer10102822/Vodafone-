package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ExtraPackage
import com.example.model.UserAccount
import com.example.ui.theme.VodafoneBackground
import com.example.ui.theme.VodafoneCrimson
import com.example.ui.theme.VodafoneHappyOrange
import com.example.ui.theme.VodafoneLightRed
import com.example.ui.theme.VodafoneRed
import com.example.ui.theme.VodafoneTextPrimary
import com.example.ui.theme.VodafoneTextSecondary
import java.util.Locale

@Composable
fun PackagesScreen(
    userAccount: UserAccount,
    packages: List<ExtraPackage>,
    onPurchasePackage: (ExtraPackage) -> Unit,
    modifier: Modifier = Modifier
) {
    var packageToBuy by remember { mutableStateOf<ExtraPackage?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(VodafoneBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                Text(
                    text = "TARİFE VE EK PAKETLER",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = VodafoneRed,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Hattınıza Uygun Ek Paketler",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = VodafoneTextPrimary
                )
                Text(
                    text = "İhtiyacınıza uygun ek GB ve Pass paketlerini faturanıza yansıtarak tek tıkla satın alın.",
                    fontSize = 12.sp,
                    color = VodafoneTextSecondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        items(packages) { extraPackage ->
            PackageCard(
                extraPackage = extraPackage,
                onBuy = { packageToBuy = extraPackage }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Purchase confirmation dialog
    packageToBuy?.let { pkg ->
        AlertDialog(
            onDismissRequest = { packageToBuy = null },
            title = {
                Text(
                    text = "Paket Satın Alma Onayı",
                    fontWeight = FontWeight.Bold,
                    color = VodafoneTextPrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = "${pkg.name} (${String.format(Locale.getDefault(), "₺%.2f", pkg.price)}) paketini hattınıza tanımlamak istiyor musunuz?",
                        fontSize = 14.sp,
                        color = VodafoneTextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tutar bir sonraki faturanıza yansıtılacaktır.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VodafoneCrimson
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onPurchasePackage(pkg)
                        packageToBuy = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VodafoneRed)
                ) {
                    Text("Onayla ve Satın Al", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { packageToBuy = null }) {
                    Text("Vazgeç", color = VodafoneTextSecondary)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun PackageCard(
    extraPackage: ExtraPackage,
    onBuy: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val (icon, tint) = when {
                        extraPackage.name.contains("Video", ignoreCase = true) -> Icons.Default.PlayCircle to VodafoneHappyOrange
                        extraPackage.name.contains("Sosyal", ignoreCase = true) -> Icons.Default.AllInclusive to Color(0xFF00875A)
                        extraPackage.name.contains("Yurt Dışı", ignoreCase = true) -> Icons.Default.Flight to Color(0xFF0065FF)
                        else -> Icons.Default.DataUsage to VodafoneRed
                    }

                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(tint.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = tint,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = extraPackage.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = VodafoneTextPrimary
                        )
                        Text(
                            text = "Geçerlilik: ${extraPackage.validity}",
                            fontSize = 11.sp,
                            color = VodafoneTextSecondary
                        )
                    }
                }

                if (extraPackage.isPopular) {
                    Box(
                        modifier = Modifier
                            .background(VodafoneLightRed, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "Popüler",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = VodafoneRed
                        )
                    }
                }
            }

            Text(
                text = extraPackage.description,
                fontSize = 12.sp,
                color = VodafoneTextSecondary,
                modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = String.format(Locale.getDefault(), "₺%.2f", extraPackage.price),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = VodafoneRed
                )

                Button(
                    onClick = onBuy,
                    colors = ButtonDefaults.buttonColors(containerColor = VodafoneRed),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Buy",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Satın Al",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
