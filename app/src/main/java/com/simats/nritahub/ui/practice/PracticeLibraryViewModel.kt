package com.simats.nritahub.ui.practice

import androidx.lifecycle.ViewModel
import com.simats.nritahub.ui.home.HomeTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PracticeLibraryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PracticeLibraryUiState())
    val uiState: StateFlow<PracticeLibraryUiState> = _uiState.asStateFlow()

    fun toggleCheckItem(index: Int) {
        _uiState.update { currentState ->
            val updated = currentState.evaluationChecklist.mapIndexed { i, item ->
                if (i == index) item.copy(isChecked = !item.isChecked) else item
            }
            currentState.copy(evaluationChecklist = updated)
        }
    }

    fun onUploadHomeworkClicked() {
        _uiState.update { it.copy(navigationEvent = PracticeLibraryNavigationEvent.UploadHomeworkVideo) }
    }

    fun onTabSelected(tab: HomeTab) {
        if (tab == HomeTab.HOME) {
            _uiState.update { it.copy(navigationEvent = PracticeLibraryNavigationEvent.NavigateToHome) }
        } else {
            _uiState.update { it.copy(selectedTab = tab) }
        }
    }

    fun onReferenceClicked() {
        _uiState.update { it.copy(navigationEvent = PracticeLibraryNavigationEvent.OpenReference) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = PracticeLibraryNavigationEvent.None) }
    }
}
