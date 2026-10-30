package com.simats.nritahub.ui.events

sealed interface EventsAnnouncementsNavigationEvent {
    data object None : EventsAnnouncementsNavigationEvent
    data object NavigateBack : EventsAnnouncementsNavigationEvent
    data object WorkshopRegistered : EventsAnnouncementsNavigationEvent
}

data class EventsAnnouncementsUiState(
    val headerNumber: String = "19",
    val title: String = "Beyond the classroom",
    val workshopBadge: String = "WORKSHOP DETAILS",
    val workshopTitle: String = "Tala & Nattuvangam Workshop",
    val workshopInstructor: String = "Led by Guru Rukmini & Meera Iyer",
    val workshopDateAndTime: String = "Saturday, 24 October 2026 · 10:00 AM – 1:00 PM",
    val workshopVenue: String = "Studio 1, Sri Kanchi Shethira Kala Mandir",
    val workshopFeeText: String = "₹500 for enrolled students",
    val workshopDescription: String = "An interactive workshop exploring rhythmic patterns, korvais, hand cymbals and hands-on nattuvangam practice.",
    val isWorkshopRegistered: Boolean = false,
    val performanceTitle: String = "Annual Margazhi Bharatanatyam Performance",
    val performanceDateAndTime: String = "Sunday, 20 December 2026 · 5:30 PM onwards",
    val performanceVenue: String = "Main Auditorium, Sri Kanchi Shethira Kala Mandir",
    val performanceDescription: String = "Full ensemble showcase featuring Foundation, Intermediate, and Advanced batch students.",
    val timeZoneFooter: String = "All academy times are in India Standard Time.",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: EventsAnnouncementsNavigationEvent = EventsAnnouncementsNavigationEvent.None
)
