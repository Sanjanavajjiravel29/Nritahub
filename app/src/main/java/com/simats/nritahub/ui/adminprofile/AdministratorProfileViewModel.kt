package com.simats.nritahub.ui.adminprofile

import androidx.lifecycle.ViewModel
import com.simats.nritahub.ui.admin.AdminTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AdministratorProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdministratorProfileUiState())
    val uiState: StateFlow<AdministratorProfileUiState> = _uiState.asStateFlow()

    fun onEditProfileClicked() {
        _uiState.update { it.copy(navigationEvent = AdministratorProfileNavigationEvent.EditProfileClicked) }
    }

    fun onSignOutClicked() {
        _uiState.update { it.copy(navigationEvent = AdministratorProfileNavigationEvent.SignOutClicked) }
    }

    fun onTabSelected(tab: AdminTab) {
        if (tab == AdminTab.OVERVIEW) {
            _uiState.update { it.copy(navigationEvent = AdministratorProfileNavigationEvent.NavigateToOverview) }
        } else {
            _uiState.update { it.copy(selectedTab = tab) }
        }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = AdministratorProfileNavigationEvent.None) }
    }
}
