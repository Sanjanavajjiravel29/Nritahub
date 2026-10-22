package com.simats.nritahub.ui.notifications

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotificationsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    fun onCategorySelected(category: NotificationCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun markAllAsRead() {
        _uiState.update { currentState ->
            val readNotifications = currentState.notifications.map {
                it.copy(
                    isUnread = false,
                    timestampText = it.timestampText.replace("Unread", "Read")
                )
            }
            currentState.copy(
                unreadCount = 0,
                notifications = readNotifications
            )
        }
    }

    fun onNotificationClicked(item: NotificationItem) {
        _uiState.update { currentState ->
            val updated = currentState.notifications.map {
                if (it.id == item.id) it.copy(isUnread = false, timestampText = it.timestampText.replace("Unread", "Read")) else it
            }
            val newUnread = updated.count { it.isUnread }
            currentState.copy(
                unreadCount = newUnread,
                notifications = updated,
                navigationEvent = NotificationsNavigationEvent.NotificationClicked(item)
            )
        }
    }

    fun onBackClicked() {
        _uiState.update { it.copy(navigationEvent = NotificationsNavigationEvent.NavigateBack) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(navigationEvent = NotificationsNavigationEvent.None) }
    }
}
