package com.simats.nritahub.ui.roster

import androidx.lifecycle.ViewModel
import com.simats.nritahub.ui.instructor.InstructorTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BatchStudentRosterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BatchStudentRosterUiState())
    val uiState: StateFlow<BatchStudentRosterUiState> = _uiState.asStateFlow()

    fun onBatchSelected(batchId: String) {
        _uiState.update { it.copy(selectedBatchId = batchId) }
    }

    fun onStudentClicked(student: StudentRosterItem) {
        _uiState.update { it.copy(navigationEvent = BatchStudentRosterNavigationEvent.StudentClicked(student)) }
    }

    fun onOpenBatchAttendanceClicked() {
        _uiState.update { it.copy(navigationEvent = BatchStudentRosterNavigationEvent.OpenBatchAttendanceClicked) }
    }

    fun onTabSelected(tab: InstructorTab) {
        if (tab == InstructorTab.TODAY) {
            _uiState.update { it.copy(navigationEvent = BatchStudentRosterNavigationEvent.NavigateToToday) }
        } else {
            _uiState.update { it.copy(selectedTab = tab) }
        }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = BatchStudentRosterNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = BatchStudentRosterNavigationEvent.None) }
    }
}
