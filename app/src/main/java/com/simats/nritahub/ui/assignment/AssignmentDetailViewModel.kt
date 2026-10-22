package com.simats.nritahub.ui.assignment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AssignmentDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AssignmentDetailUiState())
    val uiState: StateFlow<AssignmentDetailUiState> = _uiState.asStateFlow()

    fun toggleChecklistTask(index: Int) {
        _uiState.update { currentState ->
            val updatedTasks = currentState.checklistTasks.mapIndexed { i, task ->
                if (i == index) task.copy(isCompleted = !task.isCompleted) else task
            }
            currentState.copy(checklistTasks = updatedTasks)
        }
    }

    fun onResourceClicked(resource: AttachedResource) {
        _uiState.update { it.copy(navigationEvent = AssignmentDetailNavigationEvent.OpenResource) }
    }

    fun onSubmitPracticeClicked() {
        _uiState.update { it.copy(navigationEvent = AssignmentDetailNavigationEvent.PracticeSubmitted) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = AssignmentDetailNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = AssignmentDetailNavigationEvent.None) }
    }
}
