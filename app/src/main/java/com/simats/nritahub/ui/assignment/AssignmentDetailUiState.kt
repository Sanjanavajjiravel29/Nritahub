package com.simats.nritahub.ui.assignment

data class ChecklistTask(
    val title: String,
    val isCompleted: Boolean = false
)

data class AttachedResource(
    val title: String,
    val subtitle: String,
    val resourceType: String
)

sealed interface AssignmentDetailNavigationEvent {
    data object None : AssignmentDetailNavigationEvent
    data object NavigateBack : AssignmentDetailNavigationEvent
    data object OpenResource : AssignmentDetailNavigationEvent
    data object PracticeSubmitted : AssignmentDetailNavigationEvent
}

data class AssignmentDetailUiState(
    val headerNumber: String = "12",
    val title: String = "Your practice plan",
    val statusBadge: String = "IN PROGRESS",
    val dueDateBadge: String = "Due Fri, 9 Oct",
    val bannerTitle: String = "Steady steps, softer landings",
    val bannerSubtitle: String = "Bharatanatyam · Foundation F1 20 minutes a day · 6–9 October",
    val instructorInitials: String = "MI",
    val instructorName: String = "Prescribed by Meera Iyer",
    val guidanceText: String = "Focus on comfortable posture and even timing, not speed. Listen to your body.",
    val checklistTasks: List<ChecklistTask> = listOf(
        ChecklistTask("Warm up gently · 5 minutes", true),
        ChecklistTask("Watch Tatta Adavu lesson 04", true),
        ChecklistTask("Repeat 4 sets at a steady count", false),
        ChecklistTask("Record a 60–90 second practice clip", false),
        ChecklistTask("Note one thing that felt easier", false)
    ),
    val attachedResources: List<AttachedResource> = listOf(
        AttachedResource("Tatta Adavu · Finding steady rhythm", "Video · 8 minutes", "video"),
        AttachedResource("Practice counts & posture notes", "PDF · 2 pages · 1.4 MB", "pdf")
    ),
    val mindfulNotice: String = "Small, consistent practice matters. If a movement is uncomfortable, stop and tell your instructor.",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: AssignmentDetailNavigationEvent = AssignmentDetailNavigationEvent.None
)
