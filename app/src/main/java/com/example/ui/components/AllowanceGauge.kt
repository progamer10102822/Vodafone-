package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserAccount
import com.example.ui.theme.VodafoneBorder
import com.example.ui.theme.VodafoneCrimson
import com.example.ui.theme.VodafoneHappyOrange
import com.example.ui.theme.VodafoneLightRed
import com.example.ui.theme.VodafoneRed
import com.example.ui.theme.VodafoneSuccessGreen
import com.example.ui.theme.VodafoneTextPrimary
import com.example.ui.theme.VodafoneTextSecondary
import java.util.Locale

@Composable
fun AllowanceCard(
    userAccount: UserAccount,
    modifier: Modifier = Modifier
) {
    val internetProgress = (userAccount.remainingInternetGb / userAccount.totalInternetGb).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = internetProgress,
        animationSpec = tween(durationMillis = 900),
        label = "internet_progress"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            // Header: Tariff Name & Renewal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "KULLANIMLARIM",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = VodafoneRed,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = userAccount.tariffName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = VodafoneTextPrimary
                    )
                }
                Box(
                    modifier = Modifier
                        .background(VodafoneLightRed, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "Yenilenme: ${userAccount.daysRemaining} gün",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VodafoneCrimson
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Main Circular Gauge for Internet
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.size(130.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(130.dp)) {
                        val strokeWidth = 12.dp.toPx()
                        // Track circle
                        drawArc(
                            color = Color(0xFFF0F2F5),
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        // Progress arc with gradient
                        drawArc(
                            brush = Brush.sweepGradient(
                                listOf(
                                    VodafoneHappyOrange,
                                    VodafoneRed,
                                    VodafoneCrimson
                                )
                            ),
                            startAngle = -90f,
                            sweepAngle = animatedProgress * 360f,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.DataUsage,
                            contentDescription = "Internet",
                            tint = VodafoneRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = String.format(Locale.getDefault(), "%.1f", userAccount.remainingInternetGb),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = VodafoneTextPrimary
                        )
                        Text(
                            text = "GB Kaldı",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = VodafoneTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Detailed right info
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Total & Used Info
                    Column {
                        Text(
                            text = "Toplam: ${userAccount.totalInternetGb.toInt()} GB",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = VodafoneTextPrimary
                        )
                        Text(
                            text = "Kullanılan: ${String.format(Locale.getDefault(), "%.1f", userAccount.totalInternetGb - userAccount.remainingInternetGb)} GB",
                            fontSize = 12.sp,
                            color = VodafoneTextSecondary
                        )
                    }

                    // Social Pass Feature
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AllInclusive,
                            contentDescription = "Sınırsız",
                            tint = VodafoneSuccessGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Sınırsız Sosyal Pass Aktif",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VodafoneSuccessGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(VodafoneBorder)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Minutes & SMS Bars
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Minutes
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Dakika",
                                tint = Color(0xFF00875A),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Dakika",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VodafoneTextSecondary
                            )
                        }
                        Text(
                            text = "${userAccount.remainingMinutes} DK",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = VodafoneTextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { (userAccount.remainingMinutes.toFloat() / userAccount.totalMinutes).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape),
                        color = Color(0xFF00875A),
                        trackColor = Color(0xFFE0E0E0),
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Toplam ${userAccount.totalMinutes} DK",
                        fontSize = 10.sp,
                        color = VodafoneTextSecondary
                    )
                }

                // SMS
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Message,
                                contentDescription = "SMS",
                                tint = Color(0xFF0065FF),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "SMS",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VodafoneTextSecondary
                            )
                        }
                        Text(
                            text = "${userAccount.remainingSms} SMS",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = VodafoneTextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { (userAccount.remainingSms.toFloat() / userAccount.totalSms).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape),
                        color = Color(0xFF0065FF),
                        trackColor = Color(0xFFE0E0E0),
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Toplam ${userAccount.totalSms} SMS",
                        fontSize = 10.sp,
                        color = VodafoneTextSecondary
                    )
                }
            }
        }
    }
}
