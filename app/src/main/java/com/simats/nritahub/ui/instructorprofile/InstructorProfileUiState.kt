package com.simats.nritahub.ui.instructorprofile

import com.simats.nritahub.ui.instructor.InstructorTab

data class InstructorSettingOption(
    val title: String,
    val subtitle: String,
    val actionType: String
)

sealed interface InstructorProfileNavigationEvent {
    data object None : InstructorProfileNavigationEvent
    data object NavigateToToday : InstructorProfileNavigationEvent
    data object EditProfileClicked : InstructorProfileNavigationEvent
    data object SignOutClicked : InstructorProfileNavigationEvent
}

data class InstructorProfileUiState(
    val headerNumber: String = "28",
    val title: String = "Your instructor profile",
    val instructorName: String = "Meera Iyer",
    val instructorTitle: String = "Bharatanatyam Instructor",
    val roleBadge: String = "Instructor",
    val academyName: String = "Sri Kanchi Shethira Kala Mandir",
    val yearsExperienceText: String = "8 years of teaching experience",
    val bioText: String = "I help young dancers build a strong foundation in Bharatanatyam, with care for posture, rhythm and expression. Every class is a small step towards confident, joyful practice.",
    val specialisations: List<String> = listOf("Bharatanatyam", "Adavu & posture", "Hasta basics"),
    val activeBatchesSummary: String = "2 active batches · 38 students",
    val batch1Title: String = "Bharatanatyam Foundation F2",
    val batch1Time: String = "4:30–5:30 PM",
    val batch1Details: String = "18 students · Studio 1 · Hasta basics",
    val batch1Schedule: String = "Today · Tuesday, 6 October",
    val batch2Title: String = "Bharatanatyam Foundation F1",
    val batch2Time: String = "6:00–7:00 PM",
    val batch2Details: String = "20 students · Studio 2 · Tatta Adavu & posture",
    val batch2Schedule: String = "Tue, Thu & Sat · Repeat 3x weekly",
    val settingsOptions: List<InstructorSettingOption> = listOf(
        InstructorSettingOption("Account & security", "Contact details and password", "account"),
        InstructorSettingOption("Notification preferences", "Class reminders & submission alerts · On", "notifications"),
        InstructorSettingOption("Privacy & data consent", "Recording and student sharing settings", "privacy"),
        InstructorSettingOption("Help & academy contact", "office@srikanchikalamandir.example", "help")
    ),
    val appVersionFooter: String = "NrityaHub · Version 1.0 · Learning, together",
    val selectedTab: InstructorTab = InstructorTab.PROFILE,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: InstructorProfileNavigationEvent = InstructorProfileNavigationEvent.None
)
