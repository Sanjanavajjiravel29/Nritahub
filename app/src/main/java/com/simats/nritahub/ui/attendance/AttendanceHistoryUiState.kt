package com.simats.nritahub.ui.attendance

enum class AttendanceStatus { PRESENT, ABSENT, EXCUSED }

data class ClassRecord(
    val dateAndTime: String,
    val instructorAndStudio: String,
    val status: AttendanceStatus
)

sealed interface AttendanceHistoryNavigationEvent {
    data object None : AttendanceHistoryNavigationEvent
    data object NavigateBack : AttendanceHistoryNavigationEvent
}

data class AttendanceHistoryUiState(
    val headerNumber: String = "15",
    val title: String = "Showing up, together",
    val attendancePercentage: String = "92%",
    val attendanceSummary: String = "12 of 13 classes attended",
    val attendanceSubtext: String = "September 2026 · Bharatanatyam F1",
    val presentCount: Int = 12,
    val absentCount: Int = 1,
    val excusedCount: Int = 0,
    val recordsTitle: String = "September class records (13 total)",
    val classRecords: List<ClassRecord> = listOf(
        ClassRecord("29 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("27 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("25 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("24 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("22 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.ABSENT),
        ClassRecord("20 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("18 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("15 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("13 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("11 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("08 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("06 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT),
        ClassRecord("04 Sep · 6:00–7:00 PM", "Meera Iyer · Studio 2", AttendanceStatus.PRESENT)
    ),
    val correctionNotice: String = "Need a correction or an excused absence? Contact the academy office with the class date.",
    val footerNote: String = "Attendance helps us support your learning. It is not a ranking.",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: AttendanceHistoryNavigationEvent = AttendanceHistoryNavigationEvent.None
)
