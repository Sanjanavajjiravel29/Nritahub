package com.simats.nritahub.ui.instructorprofile

import androidx.lifecycle.ViewModel
import com.simats.nritahub.ui.instructor.InstructorTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InstructorProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InstructorProfileUiState())
    val uiState: StateFlow<InstructorProfileUiState> = _uiState.asStateFlow()

    fun onEditProfileClicked() {
        _uiState.update { it.copy(navigationEvent = InstructorProfileNavigationEvent.EditProfileClicked) }
    }

    fun onSignOutClicked() {
        _uiState.update { it.copy(navigationEvent = InstructorProfileNavigationEvent.SignOutClicked) }
    }

    fun onTabSelected(tab: InstructorTab) {
        if (tab == InstructorTab.TODAY) {
            _uiState.update { it.copy(navigationEvent = InstructorProfileNavigationEvent.NavigateToToday) }
        } else {
            _uiState.update { it.copy(selectedTab = tab) }
        }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = InstructorProfileNavigationEvent.None) }
    }
}
