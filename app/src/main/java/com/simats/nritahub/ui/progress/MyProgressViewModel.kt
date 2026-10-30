package com.simats.nritahub.ui.progress

import androidx.lifecycle.ViewModel
import com.simats.nritahub.ui.home.HomeTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyProgressViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MyProgressUiState())
    val uiState: StateFlow<MyProgressUiState> = _uiState.asStateFlow()

    fun onViewAttendanceHistoryClicked() {
        _uiState.update { it.copy(navigationEvent = MyProgressNavigationEvent.ViewAttendanceHistory) }
    }

    fun onTabSelected(tab: HomeTab) {
        if (tab == HomeTab.HOME) {
            _uiState.update { it.copy(navigationEvent = MyProgressNavigationEvent.NavigateToHome) }
        } else {
            _uiState.update { it.copy(selectedTab = tab) }
        }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = MyProgressNavigationEvent.None) }
    }
}
