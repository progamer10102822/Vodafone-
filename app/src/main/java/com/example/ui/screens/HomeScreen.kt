package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.UserAccount
import com.example.ui.components.AllowanceCard
import com.example.ui.theme.VodafoneBackground
import com.example.ui.theme.VodafoneCrimson
import com.example.ui.theme.VodafoneHappyGold
import com.example.ui.theme.VodafoneHappyOrange
import com.example.ui.theme.VodafoneLightRed
import com.example.ui.theme.VodafoneRed
import com.example.ui.theme.VodafoneSuccessGreen
import com.example.ui.theme.VodafoneTextPrimary
import com.example.ui.theme.VodafoneTextSecondary
import java.util.Locale

@Composable
fun HomeScreen(
    userAccount: UserAccount,
    onNavigateToHappy: () -> Unit,
    onNavigateToPackages: () -> Unit,
    onNavigateToBills: () -> Unit,
    onPayBill: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(VodafoneBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Welcome Card
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "İyi Günler,",
                        fontSize = 13.sp,
                        color = VodafoneTextSecondary
                    )
                    Text(
                        text = userAccount.fullName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = VodafoneTextPrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(VodafoneRed)
                        .clickable { onNavigateToHappy() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = VodafoneHappyGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Vodafone Happy",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 1. Allowance Card (Internet, Minutes, SMS)
        item {
            AllowanceCard(userAccount = userAccount)
        }

        // 2. Vodafone Happy Teaser Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToHappy() },
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_happy_banner),
                        contentDescription = "Vodafone Happy Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        contentScale = ContentScale.Crop
                    )
                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        VodafoneCrimson.copy(alpha = 0.9f),
                                        VodafoneRed.copy(alpha = 0.6f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = VodafoneHappyGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "VODAFONE HAPPY",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = VodafoneHappyGold,
                                    letterSpacing = 1.sp
                                )
                            }
                            Text(
                                text = "Bana Ne Var? Hediye Çarkı",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                text = "Haftalık ücretsiz internet & indirimleri çevir kazan!",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }

                        Button(
                            onClick = onNavigateToHappy,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Çevir",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = VodafoneRed
                            )
                        }
                    }
                }
            }
        }

        // 3. Current Bill Quick Summary Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "GÜNCEL FATURAM",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VodafoneTextSecondary,
                            letterSpacing = 0.5.sp
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = String.format(Locale.getDefault(), "₺%.2f", userAccount.currentBillAmount),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = VodafoneTextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            if (userAccount.isBillPaid) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Paid",
                                        tint = VodafoneSuccessGreen,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "Ödendi",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VodafoneSuccessGreen
                                    )
                                }
                            } else {
                                Text(
                                    text = "Son: ${userAccount.billDueDate}",
                                    fontSize = 11.sp,
                                    color = VodafoneTextSecondary,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                            }
                        }
                    }

                    if (!userAccount.isBillPaid) {
                        Button(
                            onClick = onPayBill,
                            colors = ButtonDefaults.buttonColors(containerColor = VodafoneRed),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Faturayı Öde",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    } else {
                        Button(
                            onClick = onNavigateToBills,
                            colors = ButtonDefaults.buttonColors(containerColor = VodafoneLightRed),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Detay",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = VodafoneCrimson
                            )
                        }
                    }
                }
            }
        }

        // 4. Quick Actions Grid
        item {
            Text(
                text = "Hızlı İşlemler",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = VodafoneTextPrimary,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionItem(
                    icon = Icons.Default.AddShoppingCart,
                    title = "Ek Paket Al",
                    color = VodafoneRed,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToPackages
                )
                QuickActionItem(
                    icon = Icons.Default.AutoAwesome,
                    title = "Vodafone Happy",
                    color = VodafoneHappyOrange,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToHappy
                )
                QuickActionItem(
                    icon = Icons.Default.ReceiptLong,
                    title = "Fatura Detayı",
                    color = Color(0xFF0065FF),
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToBills
                )
                QuickActionItem(
                    icon = Icons.Default.Speed,
                    title = "5G & Hız",
                    color = VodafoneSuccessGreen,
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }
        }

        // 5. Promotional Info Cards
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToPackages() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8F0FE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Flight,
                            contentDescription = "Yurt Dışı",
                            tint = Color(0xFF1A73E8),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Yurt Dışı Kullanım Rehberi",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = VodafoneTextPrimary
                        )
                        Text(
                            text = "80 ülkede geçerli 'Her Şey Dahil Pasaport' fırsatlarını incele.",
                            fontSize = 11.sp,
                            color = VodafoneTextSecondary
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = null,
                        tint = VodafoneTextSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun QuickActionItem(
    icon: ImageVector,
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = VodafoneTextPrimary,
                maxLines = 1
            )
        }
    }
}
