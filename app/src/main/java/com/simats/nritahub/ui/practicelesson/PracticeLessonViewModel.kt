package com.simats.nritahub.ui.practicelesson

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PracticeLessonViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PracticeLessonUiState())
    val uiState: StateFlow<PracticeLessonUiState> = _uiState.asStateFlow()

    fun togglePlayPause() {
        _uiState.update { it.copy(isPlaying = !it.isPlaying) }
    }

    fun onDownloadClicked() {
        _uiState.update {
            it.copy(
                isDownloaded = true,
                navigationEvent = PracticeLessonNavigationEvent.DownloadOffline
            )
        }
    }

    fun onOpenAssignmentClicked() {
        _uiState.update { it.copy(navigationEvent = PracticeLessonNavigationEvent.OpenAssignment) }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = PracticeLessonNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = PracticeLessonNavigationEvent.None) }
    }
}
