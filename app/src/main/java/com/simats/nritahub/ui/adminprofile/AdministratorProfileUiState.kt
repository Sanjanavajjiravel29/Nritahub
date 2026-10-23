package com.simats.nritahub.ui.adminprofile

import com.simats.nritahub.ui.admin.AdminTab

data class AdminSettingOption(
    val title: String,
    val subtitle: String,
    val actionType: String
)

sealed interface AdministratorProfileNavigationEvent {
    data object None : AdministratorProfileNavigationEvent
    data object NavigateToOverview : AdministratorProfileNavigationEvent
    data object EditProfileClicked : AdministratorProfileNavigationEvent
    data object SignOutClicked : AdministratorProfileNavigationEvent
}

data class AdministratorProfileUiState(
    val headerNumber: String = "32",
    val title: String = "Your administrator profile",
    val userInitials: String = "SV",
    val adminName: String = "Sanjana Vajjiravel",
    val adminTitle: String = "Academy administrator",
    val roleBadge: String = "Administrator",
    val academyName: String = "Sri Kanchi Shethira Kala Mandir",
    val emailAddress: String = "sanjana22@srikanchikalamandir.example",
    val mobileNumber: String = "+91 98765 43220",
    val academySummaryBadge: String = "156 enrolled students · 8 active batches",
    val academyManagementOptions: List<AdminSettingOption> = listOf(
        AdminSettingOption("Academy details", "Academy profile and office contact", "academy_details"),
        AdminSettingOption("Staff & access", "Manage instructors and permissions", "staff_access"),
        AdminSettingOption("Batches & schedules", "8 active batches · 24 classes per week", "batches_schedules"),
        AdminSettingOption("Fees & collections", "Invoices, payments and fee reminders", "fees_collections")
    ),
    val personalSettingsOptions: List<AdminSettingOption> = listOf(
        AdminSettingOption("Account & security", "Password, two-step verification and devices", "account_security"),
        AdminSettingOption("Notification preferences", "Registrations, attendance & fee alerts · On", "notifications"),
        AdminSettingOption("Privacy & data consent", "Student records and sharing permissions", "privacy"),
        AdminSettingOption("Help & academy contact", "office@srikanchikalamandir.example", "help")
    ),
    val appVersionFooter: String = "NrityaHub · Version 1.0 · Learning, together",
    val selectedTab: AdminTab = AdminTab.PROFILE,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: AdministratorProfileNavigationEvent = AdministratorProfileNavigationEvent.None
)
