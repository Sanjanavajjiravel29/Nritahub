package com.simats.nritahub.ui.practice

import com.simats.nritahub.ui.home.HomeTab

data class PracticeScheduleItem(
    val title: String,
    val detail: String
)

data class ReferenceMaterialItem(
    val title: String,
    val typeLabel: String,
    val isVideo: Boolean
)

data class EvaluationCheckItem(
    val criteriaName: String,
    val isChecked: Boolean = false
)

sealed interface PracticeLibraryNavigationEvent {
    data object None : PracticeLibraryNavigationEvent
    data object NavigateToHome : PracticeLibraryNavigationEvent
    data object OpenReference : PracticeLibraryNavigationEvent
    data object UploadHomeworkVideo : PracticeLibraryNavigationEvent
}

data class PracticeLibraryUiState(
    val headerNumber: String = "10",
    val title: String = "Practice, at your pace",
    val scheduleItems: List<PracticeScheduleItem> = listOf(
        PracticeScheduleItem("Thattadavu", "Steps 1 – 5"),
        PracticeScheduleItem("Mooshika Vahana Sloka", "Recitation & mudra practice"),
        PracticeScheduleItem("Dhyana Sloka", "Read & memorize meaning")
    ),
    val instructorName: String = "Meera Iyer",
    val referenceMaterials: List<ReferenceMaterialItem> = listOf(
        ReferenceMaterialItem("Thattadavu 1-5 Practice Demonstration", "Video · 06:15", true),
        ReferenceMaterialItem("Dhyana Sloka & Mooshika Vahana Text Notes", "PDF Notes · 3 pages", false)
    ),
    val uploadHomeworkTitle: String = "Upload your homework videos",
    val uploadHomeworkSubtitle: String = "Upload your Thattadavu 1-5 or Sloka practice video for instructor feedback.",
    val evaluationTitle: String = "Instructor evaluation",
    val evaluationChecklist: List<EvaluationCheckItem> = listOf(
        EvaluationCheckItem("Anga Shuddham", true),
        EvaluationCheckItem("Aramandi", true),
        EvaluationCheckItem("Mudras Clarity", true),
        EvaluationCheckItem("Bhavam", false),
        EvaluationCheckItem("Steps", true),
        EvaluationCheckItem("Tala", true),
        EvaluationCheckItem("Leg Posture", true),
        EvaluationCheckItem("Neatness", true),
        EvaluationCheckItem("Body Stiffness", false),
        EvaluationCheckItem("Back Position", true)
    ),
    val instructorComment: String = "Meera Iyer: \"Good progress on aramandi posture and footwork. Focus on mudra clarity in steps 3 to 5 during daily practice.\"",
    val selectedTab: HomeTab = HomeTab.PRACTICE,
    val navigationEvent: PracticeLibraryNavigationEvent = PracticeLibraryNavigationEvent.None
)
