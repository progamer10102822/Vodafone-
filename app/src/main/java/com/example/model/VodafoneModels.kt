package com.example.model

enum class PrizeType {
    GB,
    MINUTES,
    DISCOUNT,
    PASS,
    COFFEE,
    POINTS
}

data class WheelPrize(
    val id: String,
    val title: String,
    val subtitle: String,
    val prizeType: PrizeType,
    val colorHex: Long,
    val valueGb: Float = 0f,
    val valuePoints: Int = 0
)

data class HappyOffer(
    val id: String,
    val brandName: String,
    val category: String,
    val title: String,
    val description: String,
    val promoCode: String,
    val validUntil: String,
    val discountPercent: String,
    val isRedExclusive: Boolean = false,
    val isClaimed: Boolean = false
)

data class ExtraPackage(
    val id: String,
    val name: String,
    val gbAmount: Float,
    val validity: String,
    val price: Double,
    val description: String,
    val isPopular: Boolean = false
)

data class UserAccount(
    val phoneNumber: String = "0542 783 91 24",
    val fullName: String = "Murat Demir",
    val tariffName: String = "Vodafone Red Sınırsız 35 GB",
    val daysRemaining: Int = 11,
    val currentBillAmount: Double = 345.50,
    val billDueDate: String = "26 Ekim 2026",
    val isBillPaid: Boolean = false,
    val remainingInternetGb: Float = 21.8f,
    val totalInternetGb: Float = 35.0f,
    val remainingMinutes: Int = 1840,
    val totalMinutes: Int = 2500,
    val remainingSms: Int = 890,
    val totalSms: Int = 1000,
    val happyPoints: Int = 1850,
    val happyTier: String = "Red Club",
    val hasSpunWheelThisWeek: Boolean = false,
    val lastWonPrize: String? = null
)

data class BillHistoryItem(
    val month: String,
    val amount: Double,
    val isPaid: Boolean,
    val paymentDate: String
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: String
)
