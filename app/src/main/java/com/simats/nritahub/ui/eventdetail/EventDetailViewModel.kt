package com.simats.nritahub.ui.eventdetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EventDetailUiState())
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

    fun toggleCheckItem(index: Int) {
        _uiState.update { currentState ->
            val updated = currentState.preparationItems.mapIndexed { i, item ->
                if (i == index) item.copy(isChecked = !item.isChecked) else item
            }
            currentState.copy(preparationItems = updated)
        }
    }

    fun onRegisterInterestClicked() {
        _uiState.update {
            it.copy(
                isRegistered = true,
                navigationEvent = EventDetailNavigationEvent.RegisterInterest
            )
        }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = EventDetailNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = EventDetailNavigationEvent.None) }
    }
}
