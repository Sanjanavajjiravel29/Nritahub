package com.simats.nritahub.ui.fees

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FeesInvoicesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FeesInvoicesUiState())
    val uiState: StateFlow<FeesInvoicesUiState> = _uiState.asStateFlow()

    fun onGenerateInvoiceClicked() {
        _uiState.update { it.copy(isInvoiceGenerated = true) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = FeesInvoicesNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = FeesInvoicesNavigationEvent.None) }
    }
}
