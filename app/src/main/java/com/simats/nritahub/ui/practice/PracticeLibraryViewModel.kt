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

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onCategorySelected(category: CategoryFilter) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun onTabSelected(tab: HomeTab) {
        if (tab == HomeTab.HOME) {
            _uiState.update { it.copy(navigationEvent = PracticeLibraryNavigationEvent.NavigateToHome) }
        } else {
            _uiState.update { it.copy(selectedTab = tab) }
        }
    }

    fun onPlayFeaturedClicked() {
        _uiState.update { it.copy(navigationEvent = PracticeLibraryNavigationEvent.PlayFeaturedVideo) }
    }

    fun onResourceClicked() {
        _uiState.update { it.copy(navigationEvent = PracticeLibraryNavigationEvent.OpenResource) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = PracticeLibraryNavigationEvent.None) }
    }
}
