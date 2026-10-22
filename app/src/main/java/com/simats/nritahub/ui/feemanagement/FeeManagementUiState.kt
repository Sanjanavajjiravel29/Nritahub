package com.simats.nritahub.ui.feemanagement

import com.simats.nritahub.ui.admin.AdminTab

enum class FeeFilter { ALL, PAID, PENDING, OVERDUE }

enum class InvoiceStatus { PAID, PENDING, OVERDUE }

data class AdminInvoiceItem(
    val id: String,
    val studentInitials: String,
    val studentName: String,
    val invoiceNumber: String,
    val batchAndDueInfo: String,
    val amountText: String,
    val status: InvoiceStatus
)

sealed interface FeeManagementNavigationEvent {
    data object None : FeeManagementNavigationEvent
    data object NavigateToOverview : FeeManagementNavigationEvent
    data object NavigateBack : FeeManagementNavigationEvent
    data class SendReminderClicked(val invoice: AdminInvoiceItem) : FeeManagementNavigationEvent
    data object RemindOverdueFamiliesClicked : FeeManagementNavigationEvent
}

data class FeeManagementUiState(
    val headerNumber: String = "31",
    val title: String = "Keep fees on track",
    val totalCollectedAmount: String = "₹1,32,000",
    val totalBilledText: String = "₹1,87,200 billed · Includes carried balances",
    val paidCountText: String = "55 of 78 paid",
    val pendingAmountText: String = "₹43,200",
    val pendingCountText: String = "18 pending",
    val overdueAmountText: String = "₹12,000",
    val overdueCountText: String = "5 overdue",
    val searchQuery: String = "",
    val selectedFilter: FeeFilter = FeeFilter.ALL,
    val invoices: List<AdminInvoiceItem> = listOf(
        AdminInvoiceItem("1", "AR", "Ananya Rao", "INV-1026-041", "Bharatanatyam F1 · Due 10 Oct", "₹2,400", InvoiceStatus.PENDING),
        AdminInvoiceItem("2", "KS", "Kavya Shah", "INV-1026-042", "Bharatanatyam F1 · Due 10 Oct", "₹2,400", InvoiceStatus.PENDING),
        AdminInvoiceItem("3", "DM", "Diya Menon", "INV-1026-043", "Bharatanatyam F1 · Paid 5 Oct", "₹2,400", InvoiceStatus.PAID),
        AdminInvoiceItem("4", "RP", "Rohan Patel", "INV-0926-044", "Bharatanatyam F1 · Due 10 Sep", "₹2,400", InvoiceStatus.OVERDUE)
    ),
    val viewAllInvoicesText: String = "View all 78 invoices",
    val remindOverdueButtonText: String = "Remind overdue families · 5",
    val privacyNotice: String = "Fee reminders are private. Families can contact the academy office if they need help.",
    val selectedTab: AdminTab = AdminTab.FEES,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: FeeManagementNavigationEvent = FeeManagementNavigationEvent.None
)
