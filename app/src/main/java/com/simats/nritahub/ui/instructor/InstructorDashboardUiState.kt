package com.simats.nritahub.ui.instructor

enum class InstructorTab { TODAY, BATCHES, REVIEWS, PROFILE }

data class PracticeReviewItem(
    val studentNameAndTopic: String,
    val submissionDetail: String,
    val isHighlighted: Boolean = false
)

sealed interface InstructorDashboardNavigationEvent {
    data object None : InstructorDashboardNavigationEvent
    data object NavigateBack : InstructorDashboardNavigationEvent
    data object MarkAttendance : InstructorDashboardNavigationEvent
    data object OpenReviewDetail : InstructorDashboardNavigationEvent
    data object NotificationsClicked : InstructorDashboardNavigationEvent
}

data class InstructorDashboardUiState(
    val headerNumber: String = "24",
    val title: String = "Namaste, Meera",
    val selectedTab: InstructorTab = InstructorTab.TODAY,
    val instructorInitials: String = "MI",
    val dateAndAcademyText: String = "Tuesday, 6 October · Sri Kanchi Shethira Kala Mandir",
    val classesTodayCount: String = "2",
    val toReviewCount: String = "3",
    val totalStudentsCount: String = "38",
    val class1Time: String = "4:30–5:30 PM",
    val class1Title: String = "Bharatanatyam Foundation F2",
    val class1Detail: String = "18 students · Studio 1 · Hasta basics",
    val class1Status: String = "Attendance not marked",
    val class1Badge: String = "To do",
    val class2Time: String = "6:00–7:00 PM",
    val class2Title: String = "Bharatanatyam Foundation F1",
    val class2Detail: String = "20 students · Studio 2 · Tatta Adavu & posture",
    val reviewItems: List<PracticeReviewItem> = listOf(
        PracticeReviewItem("Ananya Rao · Tatta Adavu", "Submitted 6 Oct · 01:18 recording", true),
        PracticeReviewItem("Kavya Shah · Hasta practice", "Submitted 5 Oct · 00:52 recording", false),
        PracticeReviewItem("Diya Menon · Rhythm count", "Submitted 4 Oct · 01:05 recording", false)
    ),
    val encouragementNotice: String = "A little encouragement goes a long way. Share one strength and one next step in every review.",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: InstructorDashboardNavigationEvent = InstructorDashboardNavigationEvent.None
)
