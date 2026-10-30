package com.simats.nritahub.ui.academy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class JoinAcademyViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(JoinAcademyUiState())
    val uiState: StateFlow<JoinAcademyUiState> = _uiState.asStateFlow()

    fun onUsernameOrEmailChanged(value: String) {
        _uiState.update { it.copy(usernameOrEmail = value) }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onSignInClicked() {
        _uiState.update { it.copy(navigationEvent = JoinAcademyNavigationEvent.SignInSuccess) }
    }

    fun onForgotPasswordClicked() {
        _uiState.update { it.copy(navigationEvent = JoinAcademyNavigationEvent.ForgotPassword) }
    }

    fun onSignUpClicked() {
        _uiState.update { it.copy(navigationEvent = JoinAcademyNavigationEvent.NavigateToSignUp) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = JoinAcademyNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = JoinAcademyNavigationEvent.None) }
    }
}
