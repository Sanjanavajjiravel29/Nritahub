package com.simats.nritahub.ui.submit

sealed interface SubmitAssignmentNavigationEvent {
    data object None : SubmitAssignmentNavigationEvent
    data object NavigateBack : SubmitAssignmentNavigationEvent
    data object ChooseFileClicked : SubmitAssignmentNavigationEvent
    data object RemoveFileClicked : SubmitAssignmentNavigationEvent
    data object DraftSaved : SubmitAssignmentNavigationEvent
    data object SubmittedToInstructor : SubmitAssignmentNavigationEvent
}

data class SubmitAssignmentUiState(
    val headerNumber: String = "13",
    val title: String = "Share your practice",
    val badgeText: String = "STEADY STEPS · DUE 9 OCT",
    val subtitle: String = "Let Meera see what you've been working on. This is a space for learning, not comparison.",
    val isFileAttached: Boolean = true,
    val attachedFileName: String = "ananya_tatta_adavu.mp4",
    val attachedFileDetails: String = "01:18 · 24 MB · Upload complete",
    val instructorNote: String = "My count feels steadier now. I'd love help keeping my shoulders relaxed in the last set.",
    val isConsentChecked: Boolean = true,
    val privacyNotice: String = "Only you, your linked parent and authorized academy staff can view your submission. It is not posted publicly.",
    val submitButtonText: String = "Submit to Meera",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: SubmitAssignmentNavigationEvent = SubmitAssignmentNavigationEvent.None
)
