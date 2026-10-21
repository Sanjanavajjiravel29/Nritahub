package com.simats.nritahub.ui.academy

sealed interface JoinAcademyNavigationEvent {
    data object None : JoinAcademyNavigationEvent
    data object NavigateBack : JoinAcademyNavigationEvent
    data class NavigateToHome(
        val role: RoleType,
        val academy: String,
        val code: String
    ) : JoinAcademyNavigationEvent
}

data class JoinAcademyUiState(
    val headerNumber: String = "02",
    val titlePrefix: String = "Find your",
    val titleSuffix: String = "place",
    val subtitle: String = "Choose how you'll use NrityaHub. You can link another role later.",
    val selectedRole: RoleType = RoleType.STUDENT,
    val selectedAcademy: String = "SRI KANCHI SHETHIRA KALA MANDIR",
    val availableAcademies: List<String> = listOf(
        "SRI KANCHI SHETHIRA KALA MANDIR",
        "NATYANJALI DANCE ACADEMY",
        "KALAKSHETRA FOUNDATION",
        "NRITYA KALA KENDRA"
    ),
    val invitationCode: String = "",
    val navigationEvent: JoinAcademyNavigationEvent = JoinAcademyNavigationEvent.None
)
