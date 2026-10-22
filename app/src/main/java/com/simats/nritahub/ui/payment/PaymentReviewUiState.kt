package com.simats.nritahub.ui.payment

enum class PaymentMethod(
    val title: String,
    val subtitle: String
) {
    UPI("UPI", "Pay with your preferred UPI app."),
    CARD("Debit or credit card", "Pay securely using Visa, Mastercard, or RuPay."),
    NET_BANKING("Net banking", "Select from major Indian banks.")
}

sealed interface PaymentReviewNavigationEvent {
    data object None : PaymentReviewNavigationEvent
    data object NavigateBack : PaymentReviewNavigationEvent
    data object PaymentConfirmed : PaymentReviewNavigationEvent
}

data class PaymentReviewUiState(
    val headerNumber: String = "17",
    val title: String = "Review your payment",
    val invoiceBadge: String = "INV-1026-041",
    val academyAndStudent: String = "Sri Kanchi Shethira Kala Mandir · Ananya Rao · Bharatanatyam F1",
    val itemTitle: String = "October tuition",
    val itemSubtitle: String = "1–31 Oct 2026 · Monthly fee",
    val itemAmount: String = "₹2,400",
    val additionalChargesText: String = "₹0",
    val totalPayableText: String = "₹2,400",
    val dueDateAndCurrencyText: String = "Due by 10 October 2026 · All amounts in INR",
    val selectedMethod: PaymentMethod = PaymentMethod.UPI,
    val securityNotice: String = "You'll complete payment on the payment provider's secure page. NrityaHub does not store your card details or UPI PIN.",
    val payButtonText: String = "Continue to pay ₹2,400",
    val footerNote: String = "A receipt will appear after payment is confirmed.",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: PaymentReviewNavigationEvent = PaymentReviewNavigationEvent.None
)
