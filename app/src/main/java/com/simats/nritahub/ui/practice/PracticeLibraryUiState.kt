package com.simats.nritahub.ui.practice

import com.simats.nritahub.ui.home.HomeTab

enum class CategoryFilter { FOR_YOU, VIDEOS, NOTES, SAVED }

data class PracticeResource(
    val title: String,
    val subtitle: String,
    val type: String,
    val iconType: String
)

sealed interface PracticeLibraryNavigationEvent {
    data object None : PracticeLibraryNavigationEvent
    data object NavigateToHome : PracticeLibraryNavigationEvent
    data object PlayFeaturedVideo : PracticeLibraryNavigationEvent
    data object OpenResource : PracticeLibraryNavigationEvent
}

data class PracticeLibraryUiState(
    val headerNumber: String = "10",
    val title: String = "Practice, at your pace",
    val searchQuery: String = "",
    val selectedCategory: CategoryFilter = CategoryFilter.FOR_YOU,
    val danceStyleFilter: String = "Bharatanatyam",
    val levelFilter: String = "Foundation",
    val featuredBadge: String = "INSTRUCTOR ASSIGNED",
    val featuredDuration: String = "08:00",
    val featuredTitle: String = "Tatta Adavu · Finding steady rhythm",
    val featuredSubtitle: String = "Meera Iyer · Lesson 04 · Foundation",
    val watchProgressText: String = "3:24 watched · 42% complete",
    val foundationResources: List<PracticeResource> = listOf(
        PracticeResource("Warm-up before you dance", "Video · 8 min · All styles", "Saved", "download"),
        PracticeResource("Hasta vocabulary", "Notes · 6 pages · Bharatanatyam", "Offline", "check"),
        PracticeResource("Count an eight-beat cycle", "Exercise · 5 min · Foundation", "", "audio")
    ),
    val offlineNotice: String = "2 resources downloaded · 42 MB. Your saved notes and warm-up video are ready offline.",
    val exploreStylesText: String = "Explore other styles: Kathak · Contemporary",
    val selectedTab: HomeTab = HomeTab.PRACTICE,
    val navigationEvent: PracticeLibraryNavigationEvent = PracticeLibraryNavigationEvent.None
)
