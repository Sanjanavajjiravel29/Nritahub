package com.simats.nritahub.ui.submit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SubmitAssignmentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SubmitAssignmentUiState())
    val uiState: StateFlow<SubmitAssignmentUiState> = _uiState.asStateFlow()

    fun onInstructorNoteChanged(note: String) {
        _uiState.update { it.copy(instructorNote = note) }
    }

    fun toggleConsent() {
        _uiState.update { it.copy(isConsentChecked = !it.isConsentChecked) }
    }

    fun onChooseFileClicked() {
        _uiState.update { it.copy(navigationEvent = SubmitAssignmentNavigationEvent.ChooseFileClicked) }
    }

    fun onRemoveFileClicked() {
        _uiState.update { it.copy(isFileAttached = false, navigationEvent = SubmitAssignmentNavigationEvent.RemoveFileClicked) }
    }

    fun onSubmitClicked() {
        _uiState.update { it.copy(navigationEvent = SubmitAssignmentNavigationEvent.SubmittedToInstructor) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = SubmitAssignmentNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = SubmitAssignmentNavigationEvent.None) }
    }
}
