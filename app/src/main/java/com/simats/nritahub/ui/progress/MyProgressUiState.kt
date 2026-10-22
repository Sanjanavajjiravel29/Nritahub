package com.simats.nritahub.ui.progress

import com.simats.nritahub.ui.home.HomeTab

enum class ProgressTimeframe { THIS_WEEK, THIS_MONTH }

data class WeeklyBarData(
    val weekLabel: String,
    val sessionCount: Int,
    val isHighlighted: Boolean = false
)

data class SkillItem(
    val skillName: String,
    val status: String,
    val iconType: String
)

sealed interface MyProgressNavigationEvent {
    data object None : MyProgressNavigationEvent
    data object NavigateToHome : MyProgressNavigationEvent
    data object ExportReport : MyProgressNavigationEvent
}

data class MyProgressUiState(
    val headerNumber: String = "14",
    val title: String = "Growing, step by step",
    val timeframe: ProgressTimeframe = ProgressTimeframe.THIS_MONTH,
    val overviewSubtitle: String = "September overview · Updated 6 October",
    val attendancePercentage: String = "92%",
    val attendanceLabel: String = "Classes · 12 of 13",
    val practiceWeeklyScore: String = "4/5",
    val practiceWeeklyLabel: String = "Practice this week",
    val consistencyTitle: String = "Practice consistency",
    val habitBadge: String = "Building a habit",
    val consistencySubtitle: String = "Sessions per week · September",
    val weeklyBars: List<WeeklyBarData> = listOf(
        WeeklyBarData("7 Sep", 2, false),
        WeeklyBarData("14 Sep", 3, false),
        WeeklyBarData("21 Sep", 3, false),
        WeeklyBarData("28 Sep", 4, true)
    ),
    val skillsTitle: String = "Skills taking shape",
    val skills: List<SkillItem> = listOf(
        SkillItem("Rhythm & timing", "Developing well", "note"),
        SkillItem("Posture & balance", "Keep exploring", "plant"),
        SkillItem("Hasta coordination", "Growing steadily", "hand")
    ),
    val instructorInitials: String = "MI",
    val instructorNoteTitle: String = "A note from Meera · 5 Oct",
    val instructorNoteQuote: String = "\"Your rhythm is settling beautifully. Try a slow count with relaxed shoulders before adding speed.\"",
    val nextFocusText: String = "Next focus: a comfortable araimandi",
    val selectedTab: HomeTab = HomeTab.PROGRESS,
    val navigationEvent: MyProgressNavigationEvent = MyProgressNavigationEvent.None
)
