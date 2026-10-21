package com.simats.nritahub.ui.register

sealed interface CreateAccountNavigationEvent {
    data object None : CreateAccountNavigationEvent
    data object NavigateBack : CreateAccountNavigationEvent
    data object NavigateToSignIn : CreateAccountNavigationEvent
    data object NavigateToHome : CreateAccountNavigationEvent
}

data class CreateAccountUiState(
    val headerNumber: String = "04",
    val title: String = "Begin your journey",
    val roleAndAcademyBadge: String = "STUDENT · SADHANA DANCE ACADEMY",
    val subtitle: String = "A few details to keep you connected to your academy.",
    val fullName: String = "Ananya Rao",
    val mobileNumber: String = "+91 98765 43210",
    val emailAddress: String = "ananya.rao@example.com",
    val password: String = "••••••••••",
    val isPasswordVisible: Boolean = false,
    val isTermsAccepted: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: CreateAccountNavigationEvent = CreateAccountNavigationEvent.None
)
