package com.simats.nritahub.ui.classdetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ClassDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ClassDetailUiState())
    val uiState: StateFlow<ClassDetailUiState> = _uiState.asStateFlow()

    fun togglePreparationItem(index: Int) {
        _uiState.update { currentState ->
            val updatedItems = currentState.preparationItems.mapIndexed { i, item ->
                if (i == index) item.copy(isCompleted = !item.isCompleted) else item
            }
            currentState.copy(preparationItems = updatedItems)
        }
    }

    fun onAddToCalendarClicked() {
        _uiState.update { it.copy(navigationEvent = ClassDetailNavigationEvent.AddedToCalendar) }
    }

    fun onViewPreparationResourcesClicked() {
        _uiState.update { it.copy(navigationEvent = ClassDetailNavigationEvent.ViewPreparationResources) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = ClassDetailNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = ClassDetailNavigationEvent.None) }
    }
}
