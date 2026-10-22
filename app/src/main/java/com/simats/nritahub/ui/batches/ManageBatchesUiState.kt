package com.simats.nritahub.ui.batches

import com.simats.nritahub.ui.admin.AdminTab

enum class BatchMode { DETAILS, SCHEDULE }

sealed interface ManageBatchesNavigationEvent {
    data object None : ManageBatchesNavigationEvent
    data object NavigateToOverview : ManageBatchesNavigationEvent
    data object NavigateBack : ManageBatchesNavigationEvent
    data object SaveBatchSuccess : ManageBatchesNavigationEvent
}

data class ManageBatchesUiState(
    val headerNumber: String = "30",
    val title: String = "Make space for learning",
    val activeMode: BatchMode = BatchMode.DETAILS,
    val selectedBatchName: String = "Bharatanatyam - Foundation F1",
    val statusBadge: String = "Active",
    val enrollmentBadge: String = "20 enrolled · 4 spaces",
    val batchNameInput: String = "Bharatanatyam Foundation F1",
    val danceStyle: String = "Bharatanatyam",
    val level: String = "Foundation",
    val instructor: String = "Meera Iyer",
    val capacity: String = "24 students",
    val studio: String = "Studio 2",
    val selectedDays: List<String> = listOf("Tue", "Thu", "Sat"),
    val startTime: String = "6:00 PM",
    val endTime: String = "7:00 PM",
    val timezoneNote: String = "India Standard Time · Repeats weekly",
    val monthlyTuition: String = "₹2,400",
    val notifyStudentsChecked: Boolean = true,
    val conflictNotice: String = "No scheduling conflict. Meera and Studio 2 are available for these meeting times.",
    val isSaved: Boolean = false,
    val selectedTab: AdminTab = AdminTab.BATCHES,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: ManageBatchesNavigationEvent = ManageBatchesNavigationEvent.None
)
