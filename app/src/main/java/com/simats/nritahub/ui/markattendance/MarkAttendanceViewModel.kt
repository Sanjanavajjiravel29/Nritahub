package com.simats.nritahub.ui.markattendance

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MarkAttendanceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MarkAttendanceUiState())
    val uiState: StateFlow<MarkAttendanceUiState> = _uiState.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setStudentStatus(id: String, status: AttendanceStatus) {
        _uiState.update { currentState ->
            val updated = currentState.students.map {
                if (it.id == id) it.copy(status = status) else it
            }
            currentState.copy(students = updated)
        }
    }

    fun onSaveAttendanceClicked() {
        _uiState.update {
            it.copy(
                isSaved = true,
                navigationEvent = MarkAttendanceNavigationEvent.SaveAttendanceSuccess
            )
        }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = MarkAttendanceNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = MarkAttendanceNavigationEvent.None) }
    }
}
