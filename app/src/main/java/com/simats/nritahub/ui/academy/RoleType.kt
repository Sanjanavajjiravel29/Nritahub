package com.simats.nritahub.ui.academy

enum class RoleType(
    val title: String,
    val description: String
) {
    STUDENT("Student", "Learn, practice and see your growth."),
    PARENT("Parent", "Stay connected to your child's journey."),
    INSTRUCTOR("Instructor", "Guide your batches and share feedback."),
    ADMINISTRATOR("Administrator", "Manage academy classes and fees.")
}
