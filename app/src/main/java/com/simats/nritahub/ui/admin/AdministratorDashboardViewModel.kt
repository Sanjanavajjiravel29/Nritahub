package com.simats.nritahub.ui.admin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AdministratorDashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdministratorDashboardUiState())
    val uiState: StateFlow<AdministratorDashboardUiState> = _uiState.asStateFlow()

    fun onTabSelected(tab: AdminTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onManageCollectionsClicked() {
        _uiState.update { it.copy(navigationEvent = AdministratorDashboardNavigationEvent.ManageCollections) }
    }

    fun onManageSchedulesClicked() {
        _uiState.update { it.copy(navigationEvent = AdministratorDashboardNavigationEvent.ManageSchedules) }
    }

    fun onAttentionItemClicked(actionType: String) {
        _uiState.update { it.copy(navigationEvent = AdministratorDashboardNavigationEvent.AttentionItemClicked(actionType)) }
    }

    fun onNotificationsClicked() {
        _uiState.update { it.copy(navigationEvent = AdministratorDashboardNavigationEvent.NotificationsClicked) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = AdministratorDashboardNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = AdministratorDashboardNavigationEvent.None) }
    }
}
