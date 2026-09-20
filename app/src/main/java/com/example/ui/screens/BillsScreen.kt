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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.model.BillHistoryItem
import com.example.model.UserAccount
import com.example.ui.theme.VodafoneBackground
import com.example.ui.theme.VodafoneBorder
import com.example.ui.theme.VodafoneCrimson
import com.example.ui.theme.VodafoneLightRed
import com.example.ui.theme.VodafoneRed
import com.example.ui.theme.VodafoneSuccessGreen
import com.example.ui.theme.VodafoneTextPrimary
import com.example.ui.theme.VodafoneTextSecondary
import java.util.Locale

@Composable
fun BillsScreen(
    userAccount: UserAccount,
    billHistory: List<BillHistoryItem>,
    onPayBill: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showPaymentDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(VodafoneBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                Text(
                    text = "FATURA VE ÖDEMELERİM",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = VodafoneRed,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Fatura Detayları",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = VodafoneTextPrimary
                )
            }
        }

        // Current Bill Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "GÜNCEL DÖNEM FATURASI",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VodafoneTextSecondary,
                            letterSpacing = 0.5.sp
                        )

                        if (userAccount.isBillPaid) {
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = VodafoneSuccessGreen,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "Ödendi",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VodafoneSuccessGreen
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = "Son Ödeme: ${userAccount.billDueDate}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VodafoneCrimson
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = String.format(Locale.getDefault(), "₺%.2f", userAccount.currentBillAmount),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = VodafoneTextPrimary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(VodafoneBorder)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Itemized breakdown
                    BillBreakdownRow(label = "Tarife Ücreti (${userAccount.tariffName})", value = "₺280.00")
                    BillBreakdownRow(label = "Telsiz Kullanım Ücreti (Yasal Vergi)", value = "₺25.50")
                    BillBreakdownRow(label = "KDV ve ÖİV Dahil Ek Hizmetler", value = String.format(Locale.getDefault(), "₺%.2f", userAccount.currentBillAmount - 305.50))

                    Spacer(modifier = Modifier.height(18.dp))

                    if (!userAccount.isBillPaid) {
                        Button(
                            onClick = { showPaymentDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = VodafoneRed),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Payment,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Faturayı Güvenle Öde",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF1F8E9), shape = RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = VodafoneSuccessGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Bu döneme ait faturanız ödenmiştir.",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = VodafoneSuccessGreen
                            )
                        }
                    }
                }
            }
        }

        // Past Bills History Header
        item {
            Text(
                text = "Geçmiş Fatura Dönemleri",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = VodafoneTextPrimary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        items(billHistory) { bill ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(VodafoneLightRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Receipt,
                                contentDescription = null,
                                tint = VodafoneRed,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = bill.month,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = VodafoneTextPrimary
                            )
                            Text(
                                text = "Ödeme: ${bill.paymentDate}",
                                fontSize = 11.sp,
                                color = VodafoneTextSecondary
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = String.format(Locale.getDefault(), "₺%.2f", bill.amount),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = VodafoneTextPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "İndir",
                                tint = VodafoneRed,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Payment Dialog
    if (showPaymentDialog) {
        AlertDialog(
            onDismissRequest = { showPaymentDialog = false },
            title = {
                Text(
                    text = "Fatura Ödeme",
                    fontWeight = FontWeight.Bold,
                    color = VodafoneTextPrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = "Ödenecek Tutar: ${String.format(Locale.getDefault(), "₺%.2f", userAccount.currentBillAmount)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = VodafoneRed
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Ödeme Yöntemi:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VodafoneTextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF6F7F9), shape = RoundedCornerShape(10.dp))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CreditCard,
                            contentDescription = null,
                            tint = VodafoneRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Kayıtlı Kart / Vodafone Pay (**** 4812)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = VodafoneTextPrimary
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onPayBill()
                        showPaymentDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VodafoneRed)
                ) {
                    Text("Ödemeyi Tamamla", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showPaymentDialog = false }) {
                    Text("İptal", color = VodafoneTextSecondary)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun BillBreakdownRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = VodafoneTextSecondary
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = VodafoneTextPrimary
        )
    }
}
