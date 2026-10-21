package com.simats.nritahub.ui.schedule

import com.simats.nritahub.ui.home.HomeTab

enum class ScheduleViewMode { WEEK, DAY }

data class DayItem(
    val dayName: String,
    val dayNumber: String,
    val isSelected: Boolean = false
)

data class UpcomingClass(
    val dateAndTime: String,
    val description: String
)

sealed interface ClassScheduleNavigationEvent {
    data object None : ClassScheduleNavigationEvent
    data object NavigateToHome : ClassScheduleNavigationEvent
    data object NavigateToClassDetail : ClassScheduleNavigationEvent
}

data class ClassScheduleUiState(
    val headerNumber: String = "08",
    val title: String = "Your week in dance",
    val dateRangeText: String = "5–11 October 2026",
    val viewMode: ScheduleViewMode = ScheduleViewMode.WEEK,
    val days: List<DayItem> = listOf(
        DayItem("M", "5", false),
        DayItem("T", "6", true),
        DayItem("W", "7", false),
        DayItem("T", "8", false),
        DayItem("F", "9", false),
        DayItem("S", "10", false),
        DayItem("S", "11", false)
    ),
    val selectedDayText: String = "Tuesday, 6 October",
    val classCountBadge: String = "1 class",
    val todayBadge: String = "TODAY · CONFIRMED",
    val className: String = "Bharatanatyam",
    val classTime: String = "6:00–7:00 PM",
    val classStudioAndInstructor: String = "Foundation F1 · Studio 2 Meera Iyer",
    val classTopic: String = "Tatta Adavu & posture",
    val classNotebookNote: String = "Bring your practice notebook",
    val upcomingClasses: List<UpcomingClass> = listOf(
        UpcomingClass("Thu, 8 Oct · 6:00 PM", "Bharatanatyam F1 · Hasta coordination"),
        UpcomingClass("Sat, 10 Oct · 6:00 PM", "Bharatanatyam F1 · Rhythm & revision")
    ),
    val scheduleNotice: String = "Your batch meets every Tuesday, Thursday and Saturday. Schedule changes appear here first.",
    val selectedTab: HomeTab = HomeTab.SCHEDULE,
    val navigationEvent: ClassScheduleNavigationEvent = ClassScheduleNavigationEvent.None
)
