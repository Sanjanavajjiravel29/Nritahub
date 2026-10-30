package com.simats.nritahub.ui.fees

sealed interface FeesInvoicesNavigationEvent {
    data object None : FeesInvoicesNavigationEvent
    data object NavigateBack : FeesInvoicesNavigationEvent
}

data class FeesInvoicesUiState(
    val title: String = "Fee Details",
    val subtitle: String = "STUDENT VIEW · SAMPLE",
    val studentName: String = "Ananya Sharma",
    val studentId: String = "STU001",
    val studentClass: String = "Bharatanatyam – Beginner",
    val feePeriod: String = "October 2026",
    val paymentDate: String = "15 Oct 2026",
    val invoiceNo: String = "NH2026-1015",
    val paidFeeAmount: String = "₹ 3,000",
    val outstandingFeeAmount: String = "₹ 3,000",
    val isInvoiceGenerated: Boolean = false,
    val clearDuesTitle: String = "Clear your fee dues",
    val clearDuesNotice: String = "Your fee payment has not yet been confirmed. Please contact your teacher or academy administrator.",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: FeesInvoicesNavigationEvent = FeesInvoicesNavigationEvent.None
)
