package com.example.ui.components

import android.graphics.Paint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.WheelPrize
import com.example.ui.theme.VodafoneCrimson
import com.example.ui.theme.VodafoneHappyGold
import com.example.ui.theme.VodafoneHappyOrange
import com.example.ui.theme.VodafoneRed
import com.example.ui.theme.VodafoneTextPrimary
import com.example.ui.theme.VodafoneTextSecondary
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HappyWheel(
    prizes: List<WheelPrize>,
    targetRotation: Float,
    isSpinning: Boolean,
    onSpinClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedRotation by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = tween(
            durationMillis = 3500,
            easing = FastOutSlowInEasing
        ),
        label = "wheel_spin_rotation"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = VodafoneHappyGold,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "BANA NE VAR? HEDİYE ÇARKI",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = VodafoneRed,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = VodafoneHappyGold,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = "Haftalık sürpriz hediyeni kazanmak için çarkı çevir!",
                fontSize = 12.sp,
                color = VodafoneTextSecondary,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Wheel container with needle and center button
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outer glow & border ring
                Box(
                    modifier = Modifier
                        .size(270.dp)
                        .clip(CircleShape)
                        .border(
                            width = 6.dp,
                            brush = Brush.sweepGradient(
                                listOf(
                                    VodafoneHappyGold,
                                    VodafoneRed,
                                    VodafoneHappyOrange,
                                    VodafoneHappyGold
                                )
                            ),
                            shape = CircleShape
                        )
                )

                // Wheel Canvas rotating
                Canvas(
                    modifier = Modifier
                        .size(256.dp)
                        .rotate(animatedRotation)
                ) {
                    val diameter = size.minDimension
                    val radius = diameter / 2f
                    val center = Offset(radius, radius)
                    val sweepAngle = 360f / prizes.size

                    val textPaint = Paint().apply {
                        isAntiAlias = true
                        textSize = 28f
                        textAlign = Paint.Align.CENTER
                        color = android.graphics.Color.WHITE
                        isFakeBoldText = true
                    }

                    prizes.forEachIndexed { index, prize ->
                        val startAngle = index * sweepAngle
                        drawArc(
                            color = Color(prize.colorHex),
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = true
                        )

                        // Draw divider lines
                        val rad = Math.toRadians((startAngle).toDouble())
                        val endX = center.x + radius * cos(rad).toFloat()
                        val endY = center.y + radius * sin(rad).toFloat()
                        drawLine(
                            color = Color.White.copy(alpha = 0.8f),
                            start = center,
                            end = Offset(endX, endY),
                            strokeWidth = 2.dp.toPx()
                        )

                        // Draw label text radially
                        drawIntoCanvas { canvas ->
                            val native = canvas.nativeCanvas
                            native.save()
                            val midAngle = startAngle + (sweepAngle / 2f)
                            native.rotate(midAngle, center.x, center.y)

                            val textRadius = radius * 0.65f
                            native.drawText(
                                prize.title,
                                center.x + textRadius,
                                center.y + (textPaint.textSize / 3f),
                                textPaint
                            )
                            native.restore()
                        }
                    }

                    // Outer dot decorations
                    val numDots = 16
                    for (i in 0 until numDots) {
                        val angle = Math.toRadians((i * (360.0 / numDots)))
                        val dotX = center.x + (radius - 8.dp.toPx()) * cos(angle).toFloat()
                        val dotY = center.y + (radius - 8.dp.toPx()) * sin(angle).toFloat()
                        drawCircle(
                            color = Color.White.copy(alpha = 0.9f),
                            radius = 3.dp.toPx(),
                            center = Offset(dotX, dotY)
                        )
                    }
                }

                // Pointer / Indicator Needle at TOP
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Navigation,
                        contentDescription = "Pointer",
                        tint = VodafoneRed,
                        modifier = Modifier
                            .size(30.dp)
                            .rotate(180f)
                            .shadow(4.dp, shape = CircleShape)
                    )
                }

                // Center Spin Button
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .shadow(8.dp, shape = CircleShape)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    VodafoneRed,
                                    VodafoneCrimson
                                )
                            )
                        )
                        .border(3.dp, Color.White, CircleShape)
                        .clickable(enabled = !isSpinning) { onSpinClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Çevir",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = if (isSpinning) "..." else "ÇEVİR",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action button
            Button(
                onClick = onSpinClick,
                enabled = !isSpinning,
                colors = ButtonDefaults.buttonColors(containerColor = VodafoneRed),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Celebration,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isSpinning) "Çark Dönüyor..." else "HEDİYE ÇARKINI ÇEVİR",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun PrizeWonDialog(
    prize: WheelPrize,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = VodafoneRed),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Hemen Kullanmaya Başla",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(VodafoneHappyGold.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Celebration,
                        contentDescription = "Celebration",
                        tint = VodafoneRed,
                        modifier = Modifier.size(36.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "TEBRİKLER!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = VodafoneRed
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Vodafone Happy Hediye Çarkı'ndan",
                    fontSize = 13.sp,
                    color = VodafoneTextSecondary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${prize.title} - ${prize.subtitle}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = VodafoneTextPrimary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFFF6F7F9), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Hediyeniz hattınıza anında başarıyla tanımlandı!",
                        fontSize = 12.sp,
                        color = VodafoneTextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White
    )
}
