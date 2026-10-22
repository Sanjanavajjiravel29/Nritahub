package com.simats.nritahub.ui.receipt

sealed interface PaymentReceiptNavigationEvent {
    data object None : PaymentReceiptNavigationEvent
    data object NavigateBack : PaymentReceiptNavigationEvent
    data object NavigateToFees : PaymentReceiptNavigationEvent
    data object DownloadReceipt : PaymentReceiptNavigationEvent
    data object ShareReceipt : PaymentReceiptNavigationEvent
}

data class PaymentReceiptUiState(
    val headerNumber: String = "18",
    val title: String = "Payment received",
    val greetingTitle: String = "You're all set, Ananya.",
    val greetingSubtitle: String = "Your October tuition payment has been recorded. Thank you.",
    val amountText: String = "₹2,400",
    val academyName: String = "Sri Kanchi Shethira Kala Mandir",
    val invoiceNumber: String = "INV-1026-041",
    val studentName: String = "Ananya Rao",
    val forDescription: String = "October tuition - F1",
    val paidOnDate: String = "8 Oct 2026 · 10:24 AM",
    val paymentMethod: String = "UPI",
    val transactionRef: String = "Transaction reference: NH2610081041 Receipt: RCP-1026-041",
    val balanceStatusText: String = "October balance: ₹0 · No outstanding fees",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: PaymentReceiptNavigationEvent = PaymentReceiptNavigationEvent.None
)
