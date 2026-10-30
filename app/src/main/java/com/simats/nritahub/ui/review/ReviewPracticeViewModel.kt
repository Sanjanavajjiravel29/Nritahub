package com.simats.nritahub.ui.review

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReviewPracticeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewPracticeUiState())
    val uiState: StateFlow<ReviewPracticeUiState> = _uiState.asStateFlow()

    fun onFeedbackChanged(text: String) {
        _uiState.update { it.copy(feedbackText = text) }
    }

    fun onNextStepChanged(text: String) {
        _uiState.update { it.copy(nextStepText = text) }
    }

    fun onChecklistItemToggled(itemId: String) {
        _uiState.update { state ->
            val updatedList = state.checklistItems.map { item ->
                if (item.id == itemId) item.copy(isChecked = !item.isChecked) else item
            }
            state.copy(checklistItems = updatedList)
        }
    }

    fun onPublishFeedbackClicked() {
        _uiState.update {
            it.copy(
                isPublished = true,
                navigationEvent = ReviewPracticeNavigationEvent.PublishFeedbackSuccess
            )
        }
    }

    fun onSaveDraftClicked() {
        _uiState.update {
            it.copy(
                isDraftSaved = true,
                navigationEvent = ReviewPracticeNavigationEvent.SaveDraftSuccess
            )
        }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = ReviewPracticeNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = ReviewPracticeNavigationEvent.None) }
    }
}
