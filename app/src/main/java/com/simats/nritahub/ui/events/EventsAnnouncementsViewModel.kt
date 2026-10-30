package com.simats.nritahub.ui.events

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventsAnnouncementsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EventsAnnouncementsUiState())
    val uiState: StateFlow<EventsAnnouncementsUiState> = _uiState.asStateFlow()

    fun onRegisterWorkshopClicked() {
        _uiState.update {
            it.copy(
                isWorkshopRegistered = true,
                navigationEvent = EventsAnnouncementsNavigationEvent.WorkshopRegistered
            )
        }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = EventsAnnouncementsNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = EventsAnnouncementsNavigationEvent.None) }
    }
}
