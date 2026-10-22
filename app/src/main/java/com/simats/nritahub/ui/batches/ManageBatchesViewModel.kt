package com.simats.nritahub.ui.batches

import androidx.lifecycle.ViewModel
import com.simats.nritahub.ui.admin.AdminTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ManageBatchesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ManageBatchesUiState())
    val uiState: StateFlow<ManageBatchesUiState> = _uiState.asStateFlow()

    fun onBatchModeSelected(mode: BatchMode) {
        _uiState.update { it.copy(activeMode = mode) }
    }

    fun onBatchNameChanged(name: String) {
        _uiState.update { it.copy(batchNameInput = name) }
    }

    fun onTuitionChanged(tuition: String) {
        _uiState.update { it.copy(monthlyTuition = tuition) }
    }

    fun toggleNotifyStudents() {
        _uiState.update { it.copy(notifyStudentsChecked = !it.notifyStudentsChecked) }
    }

    fun toggleDay(day: String) {
        _uiState.update { currentState ->
            val updated = if (currentState.selectedDays.contains(day)) {
                currentState.selectedDays - day
            } else {
                currentState.selectedDays + day
            }
            currentState.copy(selectedDays = updated)
        }
    }

    fun onSaveBatchClicked() {
        _uiState.update {
            it.copy(
                isSaved = true,
                navigationEvent = ManageBatchesNavigationEvent.SaveBatchSuccess
            )
        }
    }

    fun onTabSelected(tab: AdminTab) {
        if (tab == AdminTab.OVERVIEW) {
            _uiState.update { it.copy(navigationEvent = ManageBatchesNavigationEvent.NavigateToOverview) }
        } else {
            _uiState.update { it.copy(selectedTab = tab) }
        }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = ManageBatchesNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = ManageBatchesNavigationEvent.None) }
    }
}
