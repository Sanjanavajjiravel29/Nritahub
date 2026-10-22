package com.simats.nritahub.ui.review

data class AssessmentDimension(
    val dimensionName: String,
    val selectedRating: String
)

sealed interface ReviewPracticeNavigationEvent {
    data object None : ReviewPracticeNavigationEvent
    data object NavigateBack : ReviewPracticeNavigationEvent
    data object PublishFeedbackSuccess : ReviewPracticeNavigationEvent
    data object SaveDraftSuccess : ReviewPracticeNavigationEvent
}

data class ReviewPracticeUiState(
    val headerNumber: String = "27",
    val title: String = "Guide the next step",
    val studentInitials: String = "AR",
    val studentNameAndBatch: String = "Ananya Rao · Foundation F1",
    val statusBadge: String = "To review",
    val submissionDetails: String = "Tatta Adavu · Submitted 5 Oct, 7:40 PM Practice recording · 01:18",
    val studentNoteTitle: String = "ANANYA'S NOTE",
    val studentNoteText: String = "\"My count feels steadier now. I'd love help keeping my shoulders relaxed in the last set.\"",
    val assessmentTitle: String = "Supportive assessment",
    val dimensions: List<AssessmentDimension> = listOf(
        AssessmentDimension("Rhythm & timing", "Developing well"),
        AssessmentDimension("Posture & balance", "Keep exploring"),
        AssessmentDimension("Hasta coordination", "Growing steadily")
    ),
    val assessmentFootnote: String = "Descriptive guidance, not scores or rankings.",
    val feedbackText: String = "Your rhythm is settling beautifully. Try a slow count with relaxed shoulders before adding speed.",
    val nextStepText: String = "Practice a comfortable araimandi for 2 minutes daily.",
    val privacyNotice: String = "Published feedback is visible to Ananya and her approved linked parent. Keep it specific, kind and actionable.",
    val isPublished: Boolean = false,
    val isDraftSaved: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: ReviewPracticeNavigationEvent = ReviewPracticeNavigationEvent.None
)
