package com.simats.nritahub.ui.academy

enum class RoleType(
    val title: String,
    val description: String
) {
    STUDENT("Student", "Learn, practice and see your growth."),
    INSTRUCTOR("Instructor", "Guide your batches and share feedback."),
    ADMINISTRATOR("Administrator", "Manage academy classes and fees.")
}
