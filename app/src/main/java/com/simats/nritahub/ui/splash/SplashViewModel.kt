package com.simats.nritahub.ui.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SplashViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    fun onGetStartedClicked() {
        _uiState.update {
            it.copy(navigationEvent = SplashNavigationEvent.NavigateToHome)
        }
    }

    fun onSignInClicked() {
        _uiState.update {
            it.copy(navigationEvent = SplashNavigationEvent.NavigateToSignIn)
        }
    }

    fun onNavigationHandled() {
        _uiState.update {
            it.copy(navigationEvent = SplashNavigationEvent.None)
        }
    }
}
