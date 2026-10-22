package com.simats.nritahub.ui.fees

data class InvoiceItem(
    val title: String,
    val invoiceIdAndDate: String,
    val studentAndPlan: String,
    val amountText: String,
    val isPending: Boolean
)

data class PaymentHistoryItem(
    val title: String,
    val invoiceIdAndDate: String,
    val amountText: String,
    val isPaid: Boolean
)

sealed interface FeesInvoicesNavigationEvent {
    data object None : FeesInvoicesNavigationEvent
    data object NavigateBack : FeesInvoicesNavigationEvent
    data object ProcessPayment : FeesInvoicesNavigationEvent
    data object ContactOffice : FeesInvoicesNavigationEvent
}

data class FeesInvoicesUiState(
    val headerNumber: String = "16",
    val title: String = "Fees, made clear",
    val outstandingBalanceText: String = "₹2,400",
    val dueDateBadgeText: String = "Due 10 Oct",
    val balanceSubtitle: String = "October tuition · Bharatanatyam F1",
    val payButtonText: String = "Pay ₹2,400",
    val currentInvoice: InvoiceItem = InvoiceItem(
        title = "October 2026 tuition",
        invoiceIdAndDate = "INV-1026-041 · Issued 1 Oct",
        studentAndPlan = "Ananya Rao · Monthly plan",
        amountText = "₹2,400",
        isPending = true
    ),
    val paymentHistory: List<PaymentHistoryItem> = listOf(
        PaymentHistoryItem("September tuition", "INV-0926-041 · Paid 8 Sep", "₹2,400", true),
        PaymentHistoryItem("August tuition", "INV-0826-041 · Paid 8 Aug", "₹2,400", true)
    ),
    val alertNotice: String = "We'll remind you before your due date. You can manage fee alerts in notification preferences.",
    val footerText: String = "Questions about your invoice? Contact office",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: FeesInvoicesNavigationEvent = FeesInvoicesNavigationEvent.None
)
