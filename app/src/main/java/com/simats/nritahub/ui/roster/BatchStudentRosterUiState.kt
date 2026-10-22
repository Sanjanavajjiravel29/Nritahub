package com.simats.nritahub.ui.roster

import com.simats.nritahub.ui.instructor.InstructorTab

enum class RosterFilter { ALL, NEEDS_FOLLOW_UP, NEW }

data class StudentRosterItem(
    val initials: String,
    val name: String,
    val levelAndAttendance: String
)

sealed interface BatchStudentRosterNavigationEvent {
    data object None : BatchStudentRosterNavigationEvent
    data object NavigateToToday : BatchStudentRosterNavigationEvent
    data object NavigateBack : BatchStudentRosterNavigationEvent
    data class StudentClicked(val student: StudentRosterItem) : BatchStudentRosterNavigationEvent
    data object OpenBatchAttendanceClicked : BatchStudentRosterNavigationEvent
}

data class BatchStudentRosterUiState(
    val headerNumber: String = "25",
    val title: String = "Your learners",
    val batchTitle: String = "Bharatanatyam - Foundation F1",
    val batchSchedule: String = "Meera Iyer · Tue / Thu / Sat · 6:00 PM",
    val studentCountBadge: String = "20 students",
    val studioBadge: String = "Studio 2",
    val searchQuery: String = "",
    val selectedFilter: RosterFilter = RosterFilter.ALL,
    val selectedMonthTitle: String = "September attendance",
    val students: List<StudentRosterItem> = listOf(
        StudentRosterItem("AR", "Ananya Rao", "Foundation · 92% · 12/13 classes"),
        StudentRosterItem("AV", "Arjun Varma", "Foundation · 77% · 10/13 classes"),
        StudentRosterItem("DM", "Diya Menon", "Foundation · 85% · 11/13 classes"),
        StudentRosterItem("KS", "Kavya Shah", "Foundation · 100% · 13/13 classes"),
        StudentRosterItem("RP", "Rohan Patel", "Foundation · 92% · 12/13 classes"),
        StudentRosterItem("SN", "Sana Nair", "Foundation · 92% · 12/13 classes")
    ),
    val viewAllText: String = "View all 20 students",
    val guidanceNotice: String = "Open a learner to see practice, progress and private feedback. Attendance is for support, not ranking.",
    val selectedTab: InstructorTab = InstructorTab.BATCHES,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: BatchStudentRosterNavigationEvent = BatchStudentRosterNavigationEvent.None
)
