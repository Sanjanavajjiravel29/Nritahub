package com.simats.nritahub.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StudentHomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StudentHomeUiState())
    val uiState: StateFlow<StudentHomeUiState> = _uiState.asStateFlow()

    fun onTabSelected(tab: HomeTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onViewClassDetailsClicked() {
        _uiState.update { it.copy(navigationEvent = StudentHomeNavigationEvent.ViewClassDetails) }
    }

    fun onOpenSadhanaClicked() {
        _uiState.update { it.copy(navigationEvent = StudentHomeNavigationEvent.OpenSadhana) }
    }

    fun onExploreEventsClicked() {
        _uiState.update { it.copy(navigationEvent = StudentHomeNavigationEvent.ExploreEvents) }
    }

    fun onNotificationsClicked() {
        _uiState.update { it.copy(navigationEvent = StudentHomeNavigationEvent.NotificationsClicked) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = StudentHomeNavigationEvent.None) }
    }
}
