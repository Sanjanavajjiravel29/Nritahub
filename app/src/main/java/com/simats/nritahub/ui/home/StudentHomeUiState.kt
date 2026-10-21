package com.simats.nritahub.ui.home

enum class HomeTab {
    HOME, SCHEDULE, PRACTICE, PROGRESS, PROFILE
}

sealed interface StudentHomeNavigationEvent {
    data object None : StudentHomeNavigationEvent
    data object ViewClassDetails : StudentHomeNavigationEvent
    data object OpenSadhana : StudentHomeNavigationEvent
    data object ExploreEvents : StudentHomeNavigationEvent
    data object NotificationsClicked : StudentHomeNavigationEvent
}

data class StudentHomeUiState(
    val headerNumber: String = "07",
    val title: String = "Namaste, Ananya",
    val selectedTab: HomeTab = HomeTab.HOME,
    val studentInitials: String = "AR",
    val studentTagline: String = "Bharatanatyam · Foundation F1",
    val nextClassBadge: String = "NEXT CLASS · TODAY",
    val nextClassDate: String = "Tue, 6 Oct",
    val nextClassTitle: String = "Find your rhythm",
    val nextClassTimeAndStudio: String = "6:00–7:00 PM · Studio 2",
    val nextClassInstructorAndTopic: String = "Meera Iyer · Tatta Adavu & posture",
    val sadhanaTitle: String = "Steady steps, softer landings",
    val sadhanaSubtitle: String = "Assigned by Meera · 20 min · Due 9 Oct",
    val attendancePercentage: String = "92%",
    val attendanceRatio: String = "Attendance · 12/13",
    val practiceWeeklyScore: String = "4/5",
    val practiceWeeklyLabel: String = "Practice this week",
    val instructorFeedback: String = "Meera: \"Your rhythm is settling beautifully. Keep your shoulders relaxed as you practice.\"",
    val announcementTitle: String = "Autumn recital registrations are open",
    val announcementBody: String = "Join us on 18 Oct. Your instructor will help you prepare.",
    val navigationEvent: StudentHomeNavigationEvent = StudentHomeNavigationEvent.None
)
