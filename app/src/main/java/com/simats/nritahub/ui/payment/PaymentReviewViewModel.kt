package com.simats.nritahub.ui.payment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PaymentReviewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentReviewUiState())
    val uiState: StateFlow<PaymentReviewUiState> = _uiState.asStateFlow()

    fun onPaymentMethodSelected(method: PaymentMethod) {
        _uiState.update { it.copy(selectedMethod = method) }
    }

    fun onContinuePaymentClicked() {
        _uiState.update { it.copy(navigationEvent = PaymentReviewNavigationEvent.PaymentConfirmed) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = PaymentReviewNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = PaymentReviewNavigationEvent.None) }
    }
}
