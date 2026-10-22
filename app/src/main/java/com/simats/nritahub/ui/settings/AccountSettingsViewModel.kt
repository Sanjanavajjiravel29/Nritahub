package com.simats.nritahub.ui.settings

import androidx.lifecycle.ViewModel
import com.simats.nritahub.ui.home.HomeTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AccountSettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AccountSettingsUiState())
    val uiState: StateFlow<AccountSettingsUiState> = _uiState.asStateFlow()

    fun onOptionClicked(actionType: String) {
        when (actionType) {
            "edit_profile" -> _uiState.update { it.copy(navigationEvent = AccountSettingsNavigationEvent.EditProfileClicked) }
            "change_password" -> _uiState.update { it.copy(navigationEvent = AccountSettingsNavigationEvent.ChangePasswordClicked) }
            else -> {}
        }
    }

    fun onSignOutClicked() {
        _uiState.update { it.copy(navigationEvent = AccountSettingsNavigationEvent.SignOutClicked) }
    }

    fun onDeleteAccountClicked() {
        _uiState.update { it.copy(navigationEvent = AccountSettingsNavigationEvent.DeleteAccountClicked) }
    }

    fun onTabSelected(tab: HomeTab) {
        if (tab == HomeTab.HOME) {
            _uiState.update { it.copy(navigationEvent = AccountSettingsNavigationEvent.NavigateToHome) }
        } else {
            _uiState.update { it.copy(selectedTab = tab) }
        }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = AccountSettingsNavigationEvent.None) }
    }
}
