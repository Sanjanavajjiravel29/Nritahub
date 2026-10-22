package com.simats.nritahub.ui.attendance

enum class AttendanceMonth { SEPTEMBER, OCTOBER }

enum class AttendanceStatus { PRESENT, ABSENT, EXCUSED }

data class ClassRecord(
    val dateAndTime: String,
    val instructorAndStudio: String,
    val status: AttendanceStatus
)

sealed interface AttendanceHistoryNavigationEvent {
    data object None : AttendanceHistoryNavigationEvent
    data object NavigateBack : AttendanceHistoryNavigationEvent
    data object ViewAllRecords : AttendanceHistoryNavigationEvent
}

data class AttendanceHistoryUiState(
    val headerNumber: String = "15",
    val title: String = "Showing up, together",
    val attendancePercentage: String = "92%",
    val attendanceSummary: String = "12 of 13 classes attended",
    val attendanceSubtext: String = "September 2026 · Bharatanatyam F1",
    val selectedMonth: AttendanceMonth = AttendanceMonth.SEPTEMBER,
    val presentCount: Int = 12,
    val absentCount: Int = 1,
    val excusedCount: Int = 0,
    val recordsTitle: String = "Recent class records",
    val classRecords: List<ClassRecord> = listOf(
        ClassRecord("29 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("25 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("24 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("22 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.ABSENT),
        ClassRecord("19 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("17 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT)
    ),
    val viewAllText: String = "View all 13 September records",
    val correctionNotice: String = "Need a correction or an excused absence? Contact the academy office with the class date.",
    val footerNote: String = "Attendance helps us support your learning. It is not a ranking.",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: AttendanceHistoryNavigationEvent = AttendanceHistoryNavigationEvent.None
)
