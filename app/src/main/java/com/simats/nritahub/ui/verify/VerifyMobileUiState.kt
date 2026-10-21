package com.simats.nritahub.ui.verify

sealed interface VerifyMobileNavigationEvent {
    data object None : VerifyMobileNavigationEvent
    data object NavigateBack : VerifyMobileNavigationEvent
    data object NavigateToHome : VerifyMobileNavigationEvent
    data object NavigateToChangeMobile : VerifyMobileNavigationEvent
}

data class VerifyMobileUiState(
    val headerNumber: String = "05",
    val title: String = "Check your phone",
    val bannerTitle: String = "One small step to connect.",
    val bannerSubtitle: String = "We sent a 6-digit verification code to your mobile number.",
    val mobileNumber: String = "+91 98765 43210",
    val otpDigits: List<String> = listOf("4", "8", "2", "6", "", ""),
    val focusedDigitIndex: Int = 4,
    val expiryTimerText: String = "04:32",
    val resendTimerText: String = "00:28",
    val canResend: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: VerifyMobileNavigationEvent = VerifyMobileNavigationEvent.None
)
