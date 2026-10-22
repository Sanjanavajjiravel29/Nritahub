package com.simats.nritahub.ui.notifications

enum class NotificationCategory { ALL, CLASSES, PRACTICE, FEES }

data class NotificationItem(
    val id: String,
    val categoryBadge: String,
    val title: String,
    val body: String,
    val timestampText: String,
    val isUnread: Boolean,
    val category: NotificationCategory
)

sealed interface NotificationsNavigationEvent {
    data object None : NotificationsNavigationEvent
    data object NavigateBack : NotificationsNavigationEvent
    data class NotificationClicked(val notification: NotificationItem) : NotificationsNavigationEvent
}

data class NotificationsUiState(
    val headerNumber: String = "21",
    val title: String = "Stay in the rhythm",
    val unreadCount: Int = 3,
    val selectedCategory: NotificationCategory = NotificationCategory.ALL,
    val notifications: List<NotificationItem> = listOf(
        NotificationItem(
            id = "1",
            categoryBadge = "CLASS",
            title = "Your class is this evening",
            body = "Bharatanatyam F1 · 6:00 PM · Studio 2. Bring your practice notebook.",
            timestampText = "Today · 9:00 AM · Unread",
            isUnread = true,
            category = NotificationCategory.CLASSES
        ),
        NotificationItem(
            id = "2",
            categoryBadge = "PRACTICE",
            title = "Meera shared a new practice plan",
            body = "Steady steps, softer landings. Submit your practice by 9 October.",
            timestampText = "Today · 8:15 AM · Unread",
            isUnread = true,
            category = NotificationCategory.PRACTICE
        ),
        NotificationItem(
            id = "3",
            categoryBadge = "FEES",
            title = "October tuition is due soon",
            body = "₹2,400 · INV-1026-041 · Due 10 October.",
            timestampText = "Yesterday · 5:00 PM · Unread",
            isUnread = true,
            category = NotificationCategory.FEES
        ),
        NotificationItem(
            id = "4",
            categoryBadge = "ACADEMY",
            title = "The autumn recital is coming",
            body = "Register your interest by 12 October. Everyone is welcome.",
            timestampText = "5 Oct · 11:30 AM · Read",
            isUnread = false,
            category = NotificationCategory.ALL
        ),
        NotificationItem(
            id = "5",
            categoryBadge = "FEEDBACK",
            title = "A new note on your progress",
            body = "Meera: \"Your rhythm is settling beautifully.\"",
            timestampText = "5 Oct · 10:00 AM · Read",
            isUnread = false,
            category = NotificationCategory.PRACTICE
        )
    ),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: NotificationsNavigationEvent = NotificationsNavigationEvent.None
)
