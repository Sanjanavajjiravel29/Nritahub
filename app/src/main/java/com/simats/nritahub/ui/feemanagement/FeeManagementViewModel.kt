package com.simats.nritahub.ui.feemanagement

import androidx.lifecycle.ViewModel
import com.simats.nritahub.ui.admin.AdminTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FeeManagementViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FeeManagementUiState())
    val uiState: StateFlow<FeeManagementUiState> = _uiState.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onFilterSelected(filter: FeeFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    fun onSendReminderClicked(invoice: AdminInvoiceItem) {
        _uiState.update { it.copy(navigationEvent = FeeManagementNavigationEvent.SendReminderClicked(invoice)) }
    }

    fun onRemindOverdueFamiliesClicked() {
        _uiState.update { it.copy(navigationEvent = FeeManagementNavigationEvent.RemindOverdueFamiliesClicked) }
    }

    fun onTabSelected(tab: AdminTab) {
        if (tab == AdminTab.OVERVIEW) {
            _uiState.update { it.copy(navigationEvent = FeeManagementNavigationEvent.NavigateToOverview) }
        } else {
            _uiState.update { it.copy(selectedTab = tab) }
        }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = FeeManagementNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = FeeManagementNavigationEvent.None) }
    }
}
