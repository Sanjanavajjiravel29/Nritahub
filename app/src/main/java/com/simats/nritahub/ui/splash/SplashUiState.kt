package com.simats.nritahub.ui.splash

sealed interface SplashNavigationEvent {
    data object None : SplashNavigationEvent
    data object NavigateToHome : SplashNavigationEvent
    data object NavigateToSignIn : SplashNavigationEvent
}

data class SplashUiState(
    val headerBadgeNumber: String = "01",
    val appTitle: String = "NrityaHub",
    val tagLine: String = "YOUR DANCE JOURNEY",
    val academyName: String = "SRI KANCHI SHETHIRA KALA MANDIR",
    val headline: String = "Every step, a little\ncloser.",
    val subtitle: String = "Learn | Practice | Grow",
    val description: String = "A home for your classes, daily practice and the joy of learning dance together.",
    val footerText: String = "For students, families and the people who teach.",
    val navigationEvent: SplashNavigationEvent = SplashNavigationEvent.None
)
