package com.simats.nritahub.ui.progress

import com.simats.nritahub.ui.home.HomeTab

sealed interface MyProgressNavigationEvent {
    data object None : MyProgressNavigationEvent
    data object NavigateToHome : MyProgressNavigationEvent
    data object ViewAttendanceHistory : MyProgressNavigationEvent
}

data class MyProgressUiState(
    val headerNumber: String = "14",
    val title: String = "Monthly progress report",
    val subtitle: String = "Monthly report & feedback curated by your instructor",
    val instructorName: String = "Meera Iyer",
    val monthLabel: String = "October 2026",
    val totalClassesAttended: String = "12",
    val totalSessionsInMonth: String = "13",
    val attendancePercentage: String = "92%",
    val practiceWeeklyScore: String = "4 / 5 sessions",
    val totalPracticeTime: String = "4 hrs 20 mins",
    val assignmentsSubmitted: String = "3 / 3 completed",
    val instructorComment: String = "Meera Iyer: \"Ananya has shown remarkable dedication this month. Her footwork rhythm in Thattadavu 1-5 is steady and posture in aramandi is improving consistently. Keep practicing slokas daily.\"",
    val selectedTab: HomeTab = HomeTab.PROGRESS,
    val navigationEvent: MyProgressNavigationEvent = MyProgressNavigationEvent.None
)
