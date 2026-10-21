package com.simats.nritahub.ui.academy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class JoinAcademyViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(JoinAcademyUiState())
    val uiState: StateFlow<JoinAcademyUiState> = _uiState.asStateFlow()

    fun onRoleSelected(role: RoleType) {
        _uiState.update { it.copy(selectedRole = role) }
    }

    fun onAcademySelected(academy: String) {
        _uiState.update { it.copy(selectedAcademy = academy) }
    }

    fun onInvitationCodeChanged(code: String) {
        _uiState.update { it.copy(invitationCode = code) }
    }

    fun onContinueClicked() {
        val currentState = _uiState.value
        _uiState.update {
            it.copy(
                navigationEvent = JoinAcademyNavigationEvent.NavigateToHome(
                    role = currentState.selectedRole,
                    academy = currentState.selectedAcademy,
                    code = currentState.invitationCode
                )
            )
        }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = JoinAcademyNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = JoinAcademyNavigationEvent.None) }
    }
}
