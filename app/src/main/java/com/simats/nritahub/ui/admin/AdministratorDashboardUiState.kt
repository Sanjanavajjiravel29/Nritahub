package com.simats.nritahub.ui.admin

enum class AdminTab { OVERVIEW, BATCHES, FEES, PROFILE }

data class AttentionItem(
    val title: String,
    val subtitle: String,
    val actionType: String
)

sealed interface AdministratorDashboardNavigationEvent {
    data object None : AdministratorDashboardNavigationEvent
    data object NavigateBack : AdministratorDashboardNavigationEvent
    data object ManageCollections : AdministratorDashboardNavigationEvent
    data object ManageSchedules : AdministratorDashboardNavigationEvent
    data object NotificationsClicked : AdministratorDashboardNavigationEvent
    data class AttentionItemClicked(val actionType: String) : AdministratorDashboardNavigationEvent
}

data class AdministratorDashboardUiState(
    val headerNumber: String = "29",
    val title: String = "Your academy, in focus",
    val academyAndDateText: String = "Sri Kanchi Shethira Kala Mandir · 6 October 2026",
    val overviewBadge: String = "OCTOBER OVERVIEW",
    val enrolledStudentsCount: String = "156",
    val activeBatchesCount: String = "8",
    val classesPerWeekCount: String = "24",
    val septemberAttendancePercentage: String = "91%",
    val feeCollectionTitle: String = "October fee collection",
    val feePercentageText: String = "71% collected",
    val totalCollectedAmount: String = "₹1,32,000",
    val totalBilledText: String = "of ₹1,87,200 billed · 78 invoices",
    val collectionProgress: Float = 0.71f,
    val pendingAmountText: String = "Pending ₹43,200",
    val overdueAmountText: String = "Overdue ₹12,000",
    val attentionTitle: String = "Needs your attention",
    val attentionItems: List<AttentionItem> = listOf(
        AttentionItem("3 new registration requests", "Confirm profile details and batch placement", "registrations"),
        AttentionItem("2 classes awaiting attendance", "Today · Meera Iyer · F1 and F2", "attendance"),
        AttentionItem("5 overdue invoices", "Send a gentle fee reminder", "overdue_fees")
    ),
    val recitalNotice: String = "Autumn recital · 18 October Registration closes 12 Oct. Share a reminder with academy families.",
    val selectedTab: AdminTab = AdminTab.OVERVIEW,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: AdministratorDashboardNavigationEvent = AdministratorDashboardNavigationEvent.None
)
