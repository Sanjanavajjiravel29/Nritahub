package com.simats.nritahub.ui.feemanagement

import com.simats.nritahub.ui.admin.AdminTab

data class StudentFeeRecord(
    val studentId: String,
    val studentName: String,
    val feeAmount: String,
    val isPaid: Boolean
)

sealed interface FeeManagementNavigationEvent {
    data object None : FeeManagementNavigationEvent
    data object NavigateToOverview : FeeManagementNavigationEvent
    data object NavigateBack : FeeManagementNavigationEvent
}

data class FeeManagementUiState(
    val headerNumber: String = "31",
    val title: String = "Fee Management",
    val subtitle: String = "ADMIN VIEW · SAMPLE",
    val studentFeeRecords: List<StudentFeeRecord> = listOf(
        StudentFeeRecord("001", "Ananya Kumar", "₹3,000", true),
        StudentFeeRecord("002", "Kavya Shah", "₹3,000", false),
        StudentFeeRecord("003", "Diya Menon", "₹3,000", true),
        StudentFeeRecord("004", "Rohan Patel", "₹3,000", false),
        StudentFeeRecord("005", "Sanjana Vajjiravel", "₹3,000", true)
    ),
    val selectedTab: AdminTab = AdminTab.FEES,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: FeeManagementNavigationEvent = FeeManagementNavigationEvent.None
)
