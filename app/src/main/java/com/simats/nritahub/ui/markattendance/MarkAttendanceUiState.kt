package com.simats.nritahub.ui.markattendance

enum class AttendanceStatus { PRESENT, ABSENT, EXCUSED }

data class AttendanceStudentItem(
    val id: String,
    val initials: String,
    val name: String,
    val status: AttendanceStatus
)

sealed interface MarkAttendanceNavigationEvent {
    data object None : MarkAttendanceNavigationEvent
    data object NavigateBack : MarkAttendanceNavigationEvent
    data object SaveAttendanceSuccess : MarkAttendanceNavigationEvent
}

data class MarkAttendanceUiState(
    val headerNumber: String = "26",
    val title: String = "Every learner counts",
    val classTitle: String = "Bharatanatyam · Foundation F1",
    val classScheduleAndStudio: String = "Tue, 6 Oct 2026 · 6:00–7:00 PM · Meera Iyer · Studio 2",
    val draftBadge: String = "Draft · Not saved",
    val searchQuery: String = "",
    val students: List<AttendanceStudentItem> = listOf(
        AttendanceStudentItem("1", "AR", "Ananya Rao", AttendanceStatus.PRESENT),
        AttendanceStudentItem("2", "KS", "Kavya Shah", AttendanceStatus.PRESENT),
        AttendanceStudentItem("3", "DM", "Diya Menon", AttendanceStatus.ABSENT),
        AttendanceStudentItem("4", "RP", "Rohan Patel", AttendanceStatus.PRESENT),
        AttendanceStudentItem("5", "SN", "Sana Nair", AttendanceStatus.EXCUSED),
        AttendanceStudentItem("6", "AV", "Arjun Varma", AttendanceStatus.PRESENT)
    ),
    val viewRemainingText: String = "View remaining 14 learners",
    val noticeText: String = "All 20 learners have a selection. Review absence reasons before saving. Changes are not saved yet.",
    val isSaved: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: MarkAttendanceNavigationEvent = MarkAttendanceNavigationEvent.None
) {
    val presentCount: Int get() = students.count { it.status == AttendanceStatus.PRESENT }
    val absentCount: Int get() = students.count { it.status == AttendanceStatus.ABSENT }
    val excusedCount: Int get() = students.count { it.status == AttendanceStatus.EXCUSED }
}
