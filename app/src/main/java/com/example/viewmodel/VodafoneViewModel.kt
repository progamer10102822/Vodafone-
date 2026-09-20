package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.BillHistoryItem
import com.example.model.ChatMessage
import com.example.model.ExtraPackage
import com.example.model.HappyOffer
import com.example.model.PrizeType
import com.example.model.UserAccount
import com.example.model.WheelPrize
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class VodafoneViewModel : ViewModel() {

    private val _userAccount = MutableStateFlow(UserAccount())
    val userAccount: StateFlow<UserAccount> = _userAccount.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _selectedHappyCategory = MutableStateFlow("Tümü")
    val selectedHappyCategory: StateFlow<String> = _selectedHappyCategory.asStateFlow()

    // Wheel of Fortune / Çarkıfelek State
    val wheelPrizes = listOf(
        WheelPrize("1", "5 GB", "Haftalık İnternet", PrizeType.GB, 0xFFE60000, valueGb = 5f),
        WheelPrize("2", "Kahve", "1 Alana 1 Bedava", PrizeType.COFFEE, 0xFFFF8F00),
        WheelPrize("3", "2 GB", "Günlük İnternet", PrizeType.GB, 0xFF00875A, valueGb = 2f),
        WheelPrize("4", "500 Puan", "Vodafone Happy", PrizeType.POINTS, 0xFF7B1FA2, valuePoints = 500),
        WheelPrize("5", "10 GB", "Sürpriz GB Hediyesi", PrizeType.GB, 0xFFD81B60, valueGb = 10f),
        WheelPrize("6", "Sosyal Pass", "24 Saat Sınırsız", PrizeType.PASS, 0xFF0065FF),
        WheelPrize("7", "1000 DK", "Haftalık Her Yöne", PrizeType.MINUTES, 0xFF0097A7),
        WheelPrize("8", "%30 İndirim", "Yemeksepeti", PrizeType.DISCOUNT, 0xFFFF6D00)
    )

    private val _isSpinning = MutableStateFlow(false)
    val isSpinning: StateFlow<Boolean> = _isSpinning.asStateFlow()

    private val _wheelRotation = MutableStateFlow(0f)
    val wheelRotation: StateFlow<Float> = _wheelRotation.asStateFlow()

    private val _wonPrize = MutableStateFlow<WheelPrize?>(null)
    val wonPrize: StateFlow<WheelPrize?> = _wonPrize.asStateFlow()

    private val _showPrizeDialog = MutableStateFlow(false)
    val showPrizeDialog: StateFlow<Boolean> = _showPrizeDialog.asStateFlow()

    // Surprise Scratch Box State
    private val _isSurpriseBoxOpened = MutableStateFlow(false)
    val isSurpriseBoxOpened: StateFlow<Boolean> = _isSurpriseBoxOpened.asStateFlow()

    private val _surpriseBoxReward = MutableStateFlow("Günlük 1 GB İnternet + 200 Happy Puan!")
    val surpriseBoxReward: StateFlow<String> = _surpriseBoxReward.asStateFlow()

    // Toast / Message event
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    // Happy Offers List
    private val _happyOffers = MutableStateFlow(
        listOf(
            HappyOffer(
                id = "h1",
                brandName = "Kahve Dünyası",
                category = "Yeme & İçme",
                title = "1 Alana 1 Bedava Kahve",
                description = "Tüm sıcak ve soğuk kahvelerde geçerli 1 alana 1 bedava ayrıcalığı.",
                promoCode = "VODA-KAHVE-24",
                validUntil = "30 Ekim",
                discountPercent = "1+1",
                isRedExclusive = true
            ),
            HappyOffer(
                id = "h2",
                brandName = "Yemeksepeti",
                category = "Yeme & İçme",
                title = "₺120 İndirim Kuponu",
                description = "₺350 ve üzeri restoran siparişlerinizde anında ₺120 indirim.",
                promoCode = "VODAYEMEK120",
                validUntil = "28 Ekim",
                discountPercent = "₺120",
                isRedExclusive = false
            ),
            HappyOffer(
                id = "h3",
                brandName = "Trendyol",
                category = "Alışveriş",
                title = "%25 İndirim Çeki",
                description = "Moda ve teknoloji kategorilerinde geçerli %25 net indirim.",
                promoCode = "VODATRENDY25",
                validUntil = "5 Kasım",
                discountPercent = "%25",
                isRedExclusive = true
            ),
            HappyOffer(
                id = "h4",
                brandName = "Paribu Cineverse",
                category = "Eğlence",
                title = "1 Bilet Alana 1 Bilet Hediye",
                description = "Pazartesi ve Perşembe günleri sinema biletlerinde 1 alana 1 bedava.",
                promoCode = "CINE-VODA-FREE",
                validUntil = "15 Kasım",
                discountPercent = "1+1",
                isRedExclusive = false
            ),
            HappyOffer(
                id = "h5",
                brandName = "Petrol Ofisi",
                category = "Seyahat",
                title = "₺150 Yakıt Hediyesi",
                description = "Vodafone Happy kullanıcılarına özel ₺1000 yakıt alımına ₺150 hediye.",
                promoCode = "PO-VODAFONE-150",
                validUntil = "31 Ekim",
                discountPercent = "₺150",
                isRedExclusive = true
            ),
            HappyOffer(
                id = "h6",
                brandName = "Boyner",
                category = "Alışveriş",
                title = "₺200 İndirim Çeki",
                description = "Yeni sezon alışverişlerinizde geçerli ₺200 anında indirim fırsatı.",
                promoCode = "BOYNER-VODA200",
                validUntil = "10 Kasım",
                discountPercent = "₺200",
                isRedExclusive = false
            ),
            HappyOffer(
                id = "h7",
                brandName = "Martı & TAG",
                category = "Seyahat",
                title = "%30 Sürüş İndirimi",
                description = "Tüm Martı scooter ve TAG yolculuklarında anında %30 indirim.",
                promoCode = "MARTI-VODA30",
                validUntil = "25 Kasım",
                discountPercent = "%30",
                isRedExclusive = false
            )
        )
    )
    val happyOffers: StateFlow<List<HappyOffer>> = _happyOffers.asStateFlow()

    // Extra packages
    val extraPackages = listOf(
        ExtraPackage("p1", "Haftalık 5 GB Ek Paket", 5f, "Haftalık", 89.0, "Tüm yurt içinde geçerli 7 günlük 5 GB internet.", isPopular = true),
        ExtraPackage("p2", "Aylık 10 GB Süper Ek Paket", 10f, "Aylık", 149.0, "Dönem sonuna kadar geçerli 10 GB ek internet paketi.", isPopular = true),
        ExtraPackage("p3", "Günlük 2 GB Acil İnternet", 2f, "Günlük", 39.0, "24 saat geçerli hızlı ek internet."),
        ExtraPackage("p4", "Video Pass 15 GB", 15f, "Aylık", 119.0, "YouTube, Netflix ve TikTok için kotadan düşmeyen 15 GB."),
        ExtraPackage("p5", "Sosyal Pass Sınırsız", 0f, "Aylık", 99.0, "Instagram, X, Facebook ve WhatsApp kullanımlarında sınırsız kota."),
        ExtraPackage("p6", "Yurt Dışı Kırmızı Dünya 3 GB", 3f, "Günlük", 169.0, "Popüler 80 ülkede geçerli uluslararası paket.")
    )

    // Bill history
    private val _billHistory = MutableStateFlow(
        listOf(
            BillHistoryItem("Eylül 2026", 345.50, true, "28 Eylül 2026"),
            BillHistoryItem("Ağustos 2026", 325.00, true, "28 Ağustos 2026"),
            BillHistoryItem("Temmuz 2026", 310.00, true, "28 Temmuz 2026"),
            BillHistoryItem("Haziran 2026", 310.00, true, "28 Haziran 2026")
        )
    )
    val billHistory: StateFlow<List<BillHistoryItem>> = _billHistory.asStateFlow()

    // TOBi Chat State
    private val _chatMessages = MutableStateFlow(
        listOf(
            ChatMessage("1", "Merhaba Murat! Ben dijital asistanın TOBi. Vodafone Yanımda ve Vodafone Happy ile ilgili sana nasıl yardımcı olabilirim?", false, "Şimdi")
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _showTobiDialog = MutableStateFlow(false)
    val showTobiDialog: StateFlow<Boolean> = _showTobiDialog.asStateFlow()

    fun setTab(index: Int) {
        _selectedTab.value = index
    }

    fun setHappyCategory(category: String) {
        _selectedHappyCategory.value = category
    }

    fun openTobi() {
        _showTobiDialog.value = true
    }

    fun closeTobi() {
        _showTobiDialog.value = false
    }

    fun sendTobiMessage(userText: String) {
        if (userText.isBlank()) return
        val userMsg = ChatMessage(System.currentTimeMillis().toString(), userText, true, "Şimdi")
        _chatMessages.update { it + userMsg }

        viewModelScope.launch {
            delay(700)
            val reply = when {
                userText.contains("internet", ignoreCase = true) || userText.contains("kalan", ignoreCase = true) -> {
                    "Kalan internet kullanımın: ${_userAccount.value.remainingInternetGb} GB / ${_userAccount.value.totalInternetGb} GB. Fatura kesimine ${_userAccount.value.daysRemaining} gün kaldı."
                }
                userText.contains("happy", ignoreCase = true) || userText.contains("çark", ignoreCase = true) -> {
                    "Vodafone Happy Hediye Çarkı her hafta yenilenir! Haftalık hediyeni Happy sekmesindeki çarkı çevirerek hemen alabilirsin."
                }
                userText.contains("fatura", ignoreCase = true) -> {
                    if (_userAccount.value.isBillPaid) {
                        "Bu ayki faturan ödenmiştir. İlginiz için teşekkür ederiz!"
                    } else {
                        "Güncel dönem faturanız ₺${_userAccount.value.currentBillAmount}. Son ödeme tarihi ${_userAccount.value.billDueDate}."
                    }
                }
                userText.contains("paket", ignoreCase = true) -> {
                    "Paketler sekmesinden avantajlı ek GB ve Pass paketlerini doğrudan faturana yansıtarak satın alabilirsin."
                }
                else -> {
                    "Sorunu anladım! Tarifen, kalan hakların ve Vodafone Happy hediyelerin hakkında dilediğin zaman bana yazabilirsin."
                }
            }
            val botMsg = ChatMessage((System.currentTimeMillis() + 1).toString(), reply, false, "Şimdi")
            _chatMessages.update { it + botMsg }
        }
    }

    fun spinWheel() {
        if (_isSpinning.value) return

        viewModelScope.launch {
            _isSpinning.value = true
            val prizeCount = wheelPrizes.size
            val sectorDegrees = 360f / prizeCount

            // Pick a random prize
            val targetIndex = Random.nextInt(prizeCount)
            val selectedPrize = wheelPrizes[targetIndex]

            // Calculate rotation: multiple full spins (4-6 full spins) + target sector alignment
            val fullSpins = Random.nextInt(5, 8) * 360f
            // Pointer is at top (270 deg or 0 deg depending on orientation).
            // When rotating clockwise, target sector offset:
            val sectorCenter = (targetIndex * sectorDegrees) + (sectorDegrees / 2f)
            val finalAngle = _wheelRotation.value + fullSpins + (360f - (sectorCenter % 360f))

            _wheelRotation.value = finalAngle
            delay(3500) // Duration of spin animation

            _isSpinning.value = false
            _wonPrize.value = selectedPrize
            _showPrizeDialog.value = true

            // Apply prize to user account
            _userAccount.update { current ->
                val newGb = if (selectedPrize.prizeType == PrizeType.GB) {
                    current.remainingInternetGb + selectedPrize.valueGb
                } else current.remainingInternetGb

                val newPoints = if (selectedPrize.prizeType == PrizeType.POINTS) {
                    current.happyPoints + selectedPrize.valuePoints
                } else current.happyPoints + 100 // Participation bonus points

                current.copy(
                    remainingInternetGb = newGb,
                    happyPoints = newPoints,
                    hasSpunWheelThisWeek = true,
                    lastWonPrize = "${selectedPrize.title} ${selectedPrize.subtitle}"
                )
            }
        }
    }

    fun closePrizeDialog() {
        _showPrizeDialog.value = false
    }

    fun openSurpriseBox() {
        if (_isSurpriseBoxOpened.value) return
        _isSurpriseBoxOpened.value = true
        _userAccount.update { current ->
            current.copy(
                remainingInternetGb = current.remainingInternetGb + 1.0f,
                happyPoints = current.happyPoints + 200
            )
        }
        viewModelScope.launch {
            _toastMessage.emit("Tebrikler! Günlük 1 GB ve 200 Happy Puan hattınıza tanımlandı!")
        }
    }

    fun claimOffer(offerId: String) {
        _happyOffers.update { list ->
            list.map { offer ->
                if (offer.id == offerId) offer.copy(isClaimed = true) else offer
            }
        }
        viewModelScope.launch {
            _toastMessage.emit("Kupon kodu kopyalandı ve hesabınıza kaydedildi!")
        }
    }

    fun purchasePackage(extraPackage: ExtraPackage) {
        _userAccount.update { current ->
            current.copy(
                remainingInternetGb = current.remainingInternetGb + extraPackage.gbAmount,
                totalInternetGb = current.totalInternetGb + extraPackage.gbAmount,
                currentBillAmount = current.currentBillAmount + extraPackage.price
            )
        }
        viewModelScope.launch {
            _toastMessage.emit("${extraPackage.name} başarıyla hattınıza yüklendi!")
        }
    }

    fun payBill() {
        _userAccount.update { current ->
            current.copy(
                isBillPaid = true
            )
        }
        viewModelScope.launch {
            _toastMessage.emit("Faturanız başarıyla ödendi. Teşekkürler!")
        }
    }
}
