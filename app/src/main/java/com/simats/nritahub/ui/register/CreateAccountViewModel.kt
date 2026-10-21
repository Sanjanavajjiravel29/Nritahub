package com.simats.nritahub.ui.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateAccountViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountUiState())
    val uiState: StateFlow<CreateAccountUiState> = _uiState.asStateFlow()

    fun onFullNameChanged(name: String) {
        _uiState.update { it.copy(fullName = name) }
    }

    fun onMobileNumberChanged(number: String) {
        _uiState.update { it.copy(mobileNumber = number) }
    }

    fun onEmailAddressChanged(email: String) {
        _uiState.update { it.copy(emailAddress = email) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun toggleTermsAcceptance() {
        _uiState.update { it.copy(isTermsAccepted = !it.isTermsAccepted) }
    }

    fun onCreateAccountClicked() {
        _uiState.update { it.copy(navigationEvent = CreateAccountNavigationEvent.NavigateToHome) }
    }

    fun onSignInClicked() {
        _uiState.update { it.copy(navigationEvent = CreateAccountNavigationEvent.NavigateToSignIn) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = CreateAccountNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = CreateAccountNavigationEvent.None) }
    }
}
