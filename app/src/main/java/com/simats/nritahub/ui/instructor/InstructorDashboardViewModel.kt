package com.simats.nritahub.ui.instructor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InstructorDashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InstructorDashboardUiState())
    val uiState: StateFlow<InstructorDashboardUiState> = _uiState.asStateFlow()

    fun onTabSelected(tab: InstructorTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onMarkAttendanceClicked() {
        _uiState.update { it.copy(navigationEvent = InstructorDashboardNavigationEvent.MarkAttendance) }
    }

    fun onReviewClicked() {
        _uiState.update { it.copy(navigationEvent = InstructorDashboardNavigationEvent.OpenReviewDetail) }
    }

    fun onNotificationsClicked() {
        _uiState.update { it.copy(navigationEvent = InstructorDashboardNavigationEvent.NotificationsClicked) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = InstructorDashboardNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = InstructorDashboardNavigationEvent.None) }
    }
}
