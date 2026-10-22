package com.simats.nritahub.ui.events

enum class EventFilter { ALL, EVENTS, WORKSHOPS, UPDATES }

data class UpcomingEventItem(
    val badge: String,
    val isExamination: Boolean,
    val title: String,
    val subtitle: String
)

sealed interface EventsAnnouncementsNavigationEvent {
    data object None : EventsAnnouncementsNavigationEvent
    data object NavigateBack : EventsAnnouncementsNavigationEvent
    data object RegisterForRecital : EventsAnnouncementsNavigationEvent
    data object OpenEventDetail : EventsAnnouncementsNavigationEvent
}

data class EventsAnnouncementsUiState(
    val headerNumber: String = "19",
    val title: String = "Beyond the classroom",
    val selectedFilter: EventFilter = EventFilter.ALL,
    val featuredBadge: String = "PERFORMANCE",
    val featuredDate: String = "Sun, 18 Oct",
    val featuredTitle: String = "Autumn recital",
    val featuredSubtitle: String = "A celebration of every student's journey. Sadhana auditorium · 5:00 PM",
    val comingTogetherTitle: String = "Coming together",
    val upcomingEvents: List<UpcomingEventItem> = listOf(
        UpcomingEventItem("WORKSHOP", false, "The language of abhinaya", "24 Oct · 10:00 AM · With Meera Iyer"),
        UpcomingEventItem("EXAMINATION", true, "Foundation learning review", "31 Oct · F1 batch · In-studio assessment")
    ),
    val academyUpdateNotice: String = "Academy update · 5 Oct Recital registrations are open until 12 Oct. Speak to your instructor about preparation.",
    val timeZoneFooter: String = "All academy times are in India Standard Time.",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: EventsAnnouncementsNavigationEvent = EventsAnnouncementsNavigationEvent.None
)
