package com.simats.nritahub.ui.receipt

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PaymentReceiptViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentReceiptUiState())
    val uiState: StateFlow<PaymentReceiptUiState> = _uiState.asStateFlow()

    fun onDownloadReceiptClicked() {
        _uiState.update { it.copy(navigationEvent = PaymentReceiptNavigationEvent.DownloadReceipt) }
    }

    fun onShareReceiptClicked() {
        _uiState.update { it.copy(navigationEvent = PaymentReceiptNavigationEvent.ShareReceipt) }
    }

    fun onBackToFeesClicked() {
        _uiState.update { it.copy(navigationEvent = PaymentReceiptNavigationEvent.NavigateToFees) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = PaymentReceiptNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = PaymentReceiptNavigationEvent.None) }
    }
}
