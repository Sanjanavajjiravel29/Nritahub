package com.simats.nritahub.ui.classdetail

data class PreparationItem(
    val title: String,
    val isCompleted: Boolean = false
)

sealed interface ClassDetailNavigationEvent {
    data object None : ClassDetailNavigationEvent
    data object NavigateBack : ClassDetailNavigationEvent
    data object AddedToCalendar : ClassDetailNavigationEvent
    data object ViewPreparationResources : ClassDetailNavigationEvent
}

data class ClassDetailUiState(
    val headerNumber: String = "09",
    val title: String = "Bharatanatyam class",
    val statusBadge: String = "CONFIRMED",
    val batchBadge: String = "FOUNDATION F1",
    val dateText: String = "Tuesday, 6 October 2026",
    val timeText: String = "6:00–7:00 PM · 60 minutes",
    val studioName: String = "Studio 2 · Sri Kanchi Shethira Kala Mandir",
    val studioLocation: String = "Villakadi Koil Street, Kanchipuram",
    val instructorInitials: String = "MI",
    val instructorNameAndRole: String = "Meera Iyer · Your instructor",
    val todaysFocusTitle: String = "Today's focus",
    val todaysFocusDescription: String = "Tatta Adavu: finding even footwork, a comfortable araimandi and relaxed shoulders.",
    val preparationItems: List<PreparationItem> = listOf(
        PreparationItem("Comfortable practice attire", false),
        PreparationItem("Water bottle & practice notebook", false),
        PreparationItem("Review the 8-minute warm-up video", false)
    ),
    val arrivalNotice: String = "Arrive 10 minutes early. Tell Meera about any discomfort before class begins.",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: ClassDetailNavigationEvent = ClassDetailNavigationEvent.None
)
