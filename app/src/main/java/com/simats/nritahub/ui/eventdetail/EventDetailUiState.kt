package com.simats.nritahub.ui.eventdetail

data class PreparationCheckItem(
    val title: String,
    val isChecked: Boolean = false
)

sealed interface EventDetailNavigationEvent {
    data object None : EventDetailNavigationEvent
    data object NavigateBack : EventDetailNavigationEvent
    data object RegisterInterest : EventDetailNavigationEvent
}

data class EventDetailUiState(
    val headerNumber: String = "20",
    val title: String = "Autumn recital",
    val performanceBadge: String = "PERFORMANCE",
    val statusBadge: String = "Registration open",
    val description: String = "A welcoming stage for our Bharatanatyam, Kathak and Contemporary learners.",
    val dateText: String = "Sunday, 18 October 2026",
    val timeText: String = "5:00–7:00 PM · Arrive by 4:00 PM",
    val venueName: String = "Sadhana Auditorium",
    val venueAddress: String = "Villakadi Koil Street, Kanchipuram",
    val organizerText: String = "Organized by Sri Kanchi Shethira Kala Mandir",
    val coordinatorText: String = "Instructor coordinator · Meera Iyer",
    val whoCanParticipateTitle: String = "Who can participate?",
    val whoCanParticipateText: String = "All enrolled students, including Foundation F1. Your instructor will confirm a suitable group piece.",
    val preparationItems: List<PreparationCheckItem> = listOf(
        PreparationCheckItem("Attend the 15 Oct rehearsal · 6 PM", false),
        PreparationCheckItem("Confirm costume with your instructor", false),
        PreparationCheckItem("Bring water and arrive an hour early", false)
    ),
    val registrationNotice: String = "Free for enrolled students. Register by 12 Oct. You have not registered yet.",
    val isRegistered: Boolean = false,
    val footerText: String = "Your academy will confirm participation after registration.",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: EventDetailNavigationEvent = EventDetailNavigationEvent.None
)
