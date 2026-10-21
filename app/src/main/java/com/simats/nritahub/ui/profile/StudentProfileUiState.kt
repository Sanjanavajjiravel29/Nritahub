package com.simats.nritahub.ui.profile

sealed interface StudentProfileNavigationEvent {
    data object None : StudentProfileNavigationEvent
    data object NavigateBack : StudentProfileNavigationEvent
    data object NavigateToHome : StudentProfileNavigationEvent
}

data class StudentProfileUiState(
    val headerNumber: String = "06",
    val title: String = "A little about you",
    val studentInitials: String = "AR",
    val studentName: String = "Ananya Rao",
    val stepLabel: String = "Step 2 of 2",
    val age: String = "19 years",
    val selectedDanceStyle: String = "Bharatanatyam",
    val danceStyles: List<String> = listOf("Bharatanatyam", "Kathak", "Kuchipudi", "Odissi"),
    val selectedExperience: String = "Foundation · less than 1 year",
    val experienceLevels: List<String> = listOf(
        "Foundation · less than 1 year",
        "Intermediate · 1-3 years",
        "Advanced · 3+ years"
    ),
    val selectedBatch: String = "Bharatanatyam F1 · Tue / Thu / Sat",
    val availableBatches: List<String> = listOf(
        "Bharatanatyam F1 · Tue / Thu / Sat",
        "Bharatanatyam F2 · Mon / Wed / Fri",
        "Bharatanatyam Weekend · Sat / Sun"
    ),
    val batchDetailTitle: String = "Batch F1 · 6:00–7:00 PM",
    val batchDetailDescription: String = "Meera Iyer · Studio 2 · 4 spaces available · ₹2,400 / month",
    val documentName: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: StudentProfileNavigationEvent = StudentProfileNavigationEvent.None
)
