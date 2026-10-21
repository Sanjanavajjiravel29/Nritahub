package com.simats.nritahub.ui.verify

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VerifyMobileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyMobileUiState())
    val uiState: StateFlow<VerifyMobileUiState> = _uiState.asStateFlow()

    fun onOtpDigitEntered(index: Int, digit: String) {
        _uiState.update { currentState ->
            val updatedDigits = currentState.otpDigits.toMutableList()
            if (index in updatedDigits.indices) {
                updatedDigits[index] = digit
            }
            val nextFocus = if (digit.isNotEmpty() && index < 5) index + 1 else index
            currentState.copy(otpDigits = updatedDigits, focusedDigitIndex = nextFocus)
        }
    }

    fun onChangeMobileClicked() {
        _uiState.update { it.copy(navigationEvent = VerifyMobileNavigationEvent.NavigateToChangeMobile) }
    }

    fun onVerifyClicked() {
        _uiState.update { it.copy(navigationEvent = VerifyMobileNavigationEvent.NavigateToHome) }
    }

    fun onResendClicked() {
        _uiState.update { it.copy(resendTimerText = "00:30", canResend = false) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = VerifyMobileNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = VerifyMobileNavigationEvent.None) }
    }
}
