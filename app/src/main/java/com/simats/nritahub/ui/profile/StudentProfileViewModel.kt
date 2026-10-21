package com.simats.nritahub.ui.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StudentProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StudentProfileUiState())
    val uiState: StateFlow<StudentProfileUiState> = _uiState.asStateFlow()

    fun onAgeChanged(age: String) {
        _uiState.update { it.copy(age = age) }
    }

    fun onDanceStyleSelected(style: String) {
        _uiState.update { it.copy(selectedDanceStyle = style) }
    }

    fun onExperienceLevelSelected(experience: String) {
        _uiState.update { it.copy(selectedExperience = experience) }
    }

    fun onBatchSelected(batch: String) {
        _uiState.update { it.copy(selectedBatch = batch) }
    }

    fun onSaveProfileClicked() {
        _uiState.update { it.copy(navigationEvent = StudentProfileNavigationEvent.NavigateToHome) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = StudentProfileNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = StudentProfileNavigationEvent.None) }
    }
}
