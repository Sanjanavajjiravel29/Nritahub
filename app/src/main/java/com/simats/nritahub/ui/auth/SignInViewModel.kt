package com.simats.nritahub.ui.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onMobileNumberChanged(number: String) {
        _uiState.update { it.copy(mobileNumber = number) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onSignInClicked() {
        _uiState.update { it.copy(navigationEvent = SignInNavigationEvent.NavigateToHome) }
    }

    fun onCreateAccountClicked() {
        _uiState.update { it.copy(navigationEvent = SignInNavigationEvent.NavigateToCreateAccount) }
    }

    fun onForgotPasswordClicked() {
        _uiState.update { it.copy(navigationEvent = SignInNavigationEvent.ForgotPasswordClicked) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = SignInNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = SignInNavigationEvent.None) }
    }
}
