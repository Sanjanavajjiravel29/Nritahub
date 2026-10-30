package com.simats.nritahub.ui.schedule

import androidx.lifecycle.ViewModel
import com.simats.nritahub.ui.home.HomeTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ClassScheduleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ClassScheduleUiState())
    val uiState: StateFlow<ClassScheduleUiState> = _uiState.asStateFlow()

    fun toggleCheckItem(index: Int) {
        _uiState.update { currentState ->
            val updated = currentState.checklistItems.mapIndexed { i, item ->
                if (i == index) item.copy(isChecked = !item.isChecked) else item
            }
            currentState.copy(checklistItems = updated)
        }
    }

    fun onTabSelected(tab: HomeTab) {
        if (tab == HomeTab.HOME) {
            _uiState.update { it.copy(navigationEvent = ClassScheduleNavigationEvent.NavigateToHome) }
        } else {
            _uiState.update { it.copy(selectedTab = tab) }
        }
    }

    fun onClassClicked() {
        _uiState.update { it.copy(navigationEvent = ClassScheduleNavigationEvent.NavigateToClassDetail) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = ClassScheduleNavigationEvent.None) }
    }
}
