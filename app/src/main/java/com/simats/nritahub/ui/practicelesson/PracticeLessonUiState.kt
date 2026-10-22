package com.simats.nritahub.ui.practicelesson

data class LessonChapter(
    val title: String,
    val timestampAndDesc: String
)

sealed interface PracticeLessonNavigationEvent {
    data object None : PracticeLessonNavigationEvent
    data object NavigateBack : PracticeLessonNavigationEvent
    data object DownloadOffline : PracticeLessonNavigationEvent
    data object OpenAssignment : PracticeLessonNavigationEvent
}

data class PracticeLessonUiState(
    val headerNumber: String = "11",
    val title: String = "Find steady rhythm",
    val isPlaying: Boolean = false,
    val currentTimeText: String = "03:24",
    val totalTimeText: String = "08:00",
    val progressFraction: Float = 0.42f,
    val styleBadge: String = "BHARATANATYAM",
    val levelBadge: String = "Foundation",
    val instructorInitials: String = "MI",
    val instructorAndLesson: String = "Meera Iyer · Tatta Adavu, lesson 04",
    val quoteTitle: String = "Let each step feel grounded.",
    val quoteSubtitle: String = "Move at a comfortable pace. Pause whenever you need a moment to reset.",
    val chapters: List<LessonChapter> = listOf(
        LessonChapter("01 · Settle into your stance", "00:00 · Soft knees, relaxed shoulders"),
        LessonChapter("02 · Follow the count", "02:10 · Eight steady beats"),
        LessonChapter("03 · Repeat on both sides", "05:20 · Aim for even weight")
    ),
    val downloadButtonText: String = "Download for offline practice · 28 MB",
    val isDownloaded: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: PracticeLessonNavigationEvent = PracticeLessonNavigationEvent.None
)
