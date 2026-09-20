package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Loyalty
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.HappyOffer
import com.example.model.UserAccount
import com.example.model.WheelPrize
import com.example.ui.components.HappyWheel
import com.example.ui.components.PrizeWonDialog
import com.example.ui.theme.VodafoneBackground
import com.example.ui.theme.VodafoneBorder
import com.example.ui.theme.VodafoneCrimson
import com.example.ui.theme.VodafoneHappyGold
import com.example.ui.theme.VodafoneHappyOrange
import com.example.ui.theme.VodafoneHappyPurple
import com.example.ui.theme.VodafoneLightRed
import com.example.ui.theme.VodafoneRed
import com.example.ui.theme.VodafoneSuccessGreen
import com.example.ui.theme.VodafoneTextPrimary
import com.example.ui.theme.VodafoneTextSecondary

@Composable
fun HappyScreen(
    userAccount: UserAccount,
    wheelPrizes: List<WheelPrize>,
    wheelRotation: Float,
    isSpinning: Boolean,
    wonPrize: WheelPrize?,
    showPrizeDialog: Boolean,
    isSurpriseBoxOpened: Boolean,
    surpriseBoxReward: String,
    happyOffers: List<HappyOffer>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    onSpinWheel: () -> Unit,
    onClosePrizeDialog: () -> Unit,
    onOpenSurpriseBox: () -> Unit,
    onClaimOffer: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = remember {
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    val categories = listOf("Tümü", "Yeme & İçme", "Alışveriş", "Eğlence", "Seyahat")

    val filteredOffers = if (selectedCategory == "Tümü") {
        happyOffers
    } else {
        happyOffers.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(VodafoneBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Happy Hero Header with Banner
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_happy_banner),
                        contentDescription = "Vodafone Happy",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Black.copy(alpha = 0.2f),
                                        Color.Black.copy(alpha = 0.75f)
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.BottomStart)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = VodafoneHappyGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "VODAFONE HAPPY DÜNYASI",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = VodafoneHappyGold,
                                letterSpacing = 1.sp
                            )
                        }
                        Text(
                            text = "Ayrıcalıklar, Hediyeler & Fırsatlar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 2. Happy Club Membership & Points Card
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(VodafoneHappyGold, VodafoneHappyOrange)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Loyalty,
                                contentDescription = "Club",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = userAccount.happyTier,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = VodafoneRed
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(VodafoneHappyGold.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "VIP",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VodafoneHappyOrange
                                    )
                                }
                            }
                            Text(
                                text = "Özel İndirim & Çark Hakları",
                                fontSize = 11.sp,
                                color = VodafoneTextSecondary
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = VodafoneHappyGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${userAccount.happyPoints}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = VodafoneTextPrimary
                            )
                        }
                        Text(
                            text = "Happy Puan",
                            fontSize = 11.sp,
                            color = VodafoneTextSecondary
                        )
                    }
                }
            }
        }

        // 3. The Interactive Happy Wheel (Hediye Çarkı)
        item {
            HappyWheel(
                prizes = wheelPrizes,
                targetRotation = wheelRotation,
                isSpinning = isSpinning,
                onSpinClick = onSpinWheel
            )
        }

        // 4. Günün Sürprizi (Interactive Scratch / Unbox Card)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenSurpriseBox() },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(VodafoneHappyPurple.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CardGiftcard,
                                    contentDescription = "Sürpriz",
                                    tint = VodafoneHappyPurple,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "GÜNÜN SÜRPRİZİ",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VodafoneHappyPurple,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = if (isSurpriseBoxOpened) "Bugünkü Hediyeni Açtın!" else "Sürpriz Kutuyu Aç & Kazan",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VodafoneTextPrimary
                                )
                            }
                        }

                        if (!isSurpriseBoxOpened) {
                            Box(
                                modifier = Modifier
                                    .background(VodafoneHappyPurple, shape = RoundedCornerShape(10.dp))
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = "AÇ",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    AnimatedVisibility(visible = isSurpriseBoxOpened) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF3E5F5), shape = RoundedCornerShape(12.dp))
                                .border(1.dp, VodafoneHappyPurple.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = VodafoneHappyPurple,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = surpriseBoxReward,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VodafoneHappyPurple
                                )
                            }
                        }
                    }

                    if (!isSurpriseBoxOpened) {
                        Text(
                            text = "Kutuya dokunarak günlük sürpriz GB ve Happy Puanını hemen al!",
                            fontSize = 12.sp,
                            color = VodafoneTextSecondary
                        )
                    }
                }
            }
        }

        // 5. Category Filters for Happy Offers
        item {
            Text(
                text = "Marka Ayrıcalıkları & İndirimler",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = VodafoneTextPrimary,
                modifier = Modifier.padding(top = 4.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 6.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = cat == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { onCategorySelect(cat) },
                        label = { Text(cat, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = VodafoneRed,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = VodafoneTextPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) VodafoneRed else VodafoneBorder
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }
        }

        // 6. Offer Cards
        items(filteredOffers) { offer ->
            HappyOfferCard(
                offer = offer,
                onClaim = {
                    // Copy to clipboard
                    clipboardManager.setPrimaryClip(ClipData.newPlainText("Vodafone Happy Promo", offer.promoCode))
                    onClaimOffer(offer.id)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Prize Dialog if user just won the wheel
    if (showPrizeDialog && wonPrize != null) {
        PrizeWonDialog(
            prize = wonPrize,
            onDismiss = onClosePrizeDialog
        )
    }
}

@Composable
fun HappyOfferCard(
    offer: HappyOffer,
    onClaim: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Brand & Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(VodafoneLightRed),
                        contentAlignment = Alignment.Center
                    ) {
                        val icon = when (offer.category) {
                            "Yeme & İçme" -> Icons.Default.Restaurant
                            "Alışveriş" -> Icons.Default.ShoppingBag
                            "Eğlence" -> Icons.Default.TheaterComedy
                            else -> Icons.Default.LocalOffer
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = offer.category,
                            tint = VodafoneRed,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = offer.brandName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = VodafoneTextPrimary
                        )
                        Text(
                            text = "Son: ${offer.validUntil}",
                            fontSize = 10.sp,
                            color = VodafoneTextSecondary
                        )
                    }
                }

                // Discount badge
                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(listOf(VodafoneRed, VodafoneCrimson)),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = offer.discountPercent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = offer.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = VodafoneTextPrimary
            )

            Text(
                text = offer.description,
                fontSize = 12.sp,
                color = VodafoneTextSecondary,
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
            )

            // Promo Code & Action Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF7F8FA), shape = RoundedCornerShape(12.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(start = 6.dp)) {
                    Text(
                        text = "İNDİRİM KODU",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = VodafoneTextSecondary,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = offer.promoCode,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = VodafoneTextPrimary,
                        letterSpacing = 1.sp
                    )
                }

                Button(
                    onClick = onClaim,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (offer.isClaimed) VodafoneSuccessGreen else VodafoneRed
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (offer.isClaimed) Icons.Default.Check else Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (offer.isClaimed) "Kopyalandı" else "Kodu Al",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
