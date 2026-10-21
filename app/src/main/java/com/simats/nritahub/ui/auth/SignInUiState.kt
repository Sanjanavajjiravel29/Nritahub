package com.simats.nritahub.ui.auth

sealed interface SignInNavigationEvent {
    data object None : SignInNavigationEvent
    data object NavigateBack : SignInNavigationEvent
    data object NavigateToHome : SignInNavigationEvent
    data object NavigateToCreateAccount : SignInNavigationEvent
    data object ForgotPasswordClicked : SignInNavigationEvent
}

data class SignInUiState(
    val headerNumber: String = "03",
    val title: String = "Welcome back",
    val bannerTitle: String = "Make room for your practice.",
    val bannerSubtitle: String = "Your next step starts right here.",
    val academyAndRole: String = "Sri Kanchi Shethira Kala Mandir - Student",
    val mobileNumber: String = "+91 98765 43210",
    val password: String = "••••••••••",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: SignInNavigationEvent = SignInNavigationEvent.None
)
