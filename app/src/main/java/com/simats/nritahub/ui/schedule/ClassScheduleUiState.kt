package com.simats.nritahub.ui.schedule

import com.simats.nritahub.ui.home.HomeTab

data class ScheduleCheckItem(
    val title: String,
    val isChecked: Boolean = false
)

sealed interface ClassScheduleNavigationEvent {
    data object None : ClassScheduleNavigationEvent
    data object NavigateToHome : ClassScheduleNavigationEvent
    data object NavigateToClassDetail : ClassScheduleNavigationEvent
}

data class ClassScheduleUiState(
    val headerNumber: String = "08",
    val title: String = "Class schedule",
    val checklistItems: List<ScheduleCheckItem> = listOf(
        ScheduleCheckItem("Dance Note", true),
        ScheduleCheckItem("Theory Book", false),
        ScheduleCheckItem("Water Bottle", true),
        ScheduleCheckItem("Salangai / Nattuvangam", false),
        ScheduleCheckItem("Practice Saree / Uniform", true)
    ),
    val selectedTab: HomeTab = HomeTab.SCHEDULE,
    val navigationEvent: ClassScheduleNavigationEvent = ClassScheduleNavigationEvent.None
)
