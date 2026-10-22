package com.simats.nritahub.ui.settings

import com.simats.nritahub.ui.home.HomeTab

data class SettingOption(
    val title: String,
    val subtitle: String? = null,
    val actionType: String
)

sealed interface AccountSettingsNavigationEvent {
    data object None : AccountSettingsNavigationEvent
    data object NavigateToHome : AccountSettingsNavigationEvent
    data object EditProfileClicked : AccountSettingsNavigationEvent
    data object ChangePasswordClicked : AccountSettingsNavigationEvent
    data object SignOutClicked : AccountSettingsNavigationEvent
    data object DeleteAccountClicked : AccountSettingsNavigationEvent
}

data class AccountSettingsUiState(
    val headerNumber: String = "22",
    val title: String = "Your account",
    val userInitials: String = "AR",
    val userName: String = "Ananya Rao",
    val roleBadge: String = "Student",
    val userDetails: String = "Bharatanatyam · Foundation F1 · +91 98765 43210",
    val academyName: String = "Sri Kanchi Shethira Kala Mandir",
    val makeItYoursTitle: String = "Make it yours",
    val makeItYoursOptions: List<SettingOption> = listOf(
        SettingOption("Edit profile", "Photo, age, contact & dance details", "edit_profile"),
        SettingOption("Change password", "Keep your account secure", "change_password"),
        SettingOption("Notification preferences", "Classes, practice & fee alerts · On", "notifications"),
        SettingOption("Offline downloads", "2 resources · 42 MB used", "downloads")
    ),
    val privacyTitle: String = "Privacy & support",
    val privacyOptions: List<SettingOption> = listOf(
        SettingOption("Privacy & data consent", "Manage recording and parent sharing", "privacy"),
        SettingOption("Download my account data", null, "download_data"),
        SettingOption("Help & academy contact", "office@srikanchikalamandir.example", "help")
    ),
    val deleteTitle: String = "Delete account",
    val deleteDescription: String = "Permanently request removal of your account. This is different from signing out.",
    val deleteLinkText: String = "Review deletion options",
    val appVersionFooter: String = "NrityaHub · Version 1.0 · Learning, together",
    val selectedTab: HomeTab = HomeTab.PROFILE,
    val navigationEvent: AccountSettingsNavigationEvent = AccountSettingsNavigationEvent.None
)
