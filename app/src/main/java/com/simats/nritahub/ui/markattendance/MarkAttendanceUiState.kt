package com.simats.nritahub.ui.markattendance

enum class AttendanceStatus { PRESENT, ABSENT, EXCUSED }

data class AttendanceStudentItem(
    val id: String,
    val initials: String,
    val name: String,
    val status: AttendanceStatus
)

data class AttendanceBatchInfo(
    val id: String,
    val title: String,
    val scheduleAndStudio: String
)

sealed interface MarkAttendanceNavigationEvent {
    data object None : MarkAttendanceNavigationEvent
    data object NavigateBack : MarkAttendanceNavigationEvent
    data object SaveAttendanceSuccess : MarkAttendanceNavigationEvent
}

data class MarkAttendanceUiState(
    val headerNumber: String = "26",
    val title: String = "Mark attendance",
    val selectedBatchId: String = "f2",
    val batches: List<AttendanceBatchInfo> = listOf(
        AttendanceBatchInfo("f2", "Bharatanatyam Foundation F2", "Tue, 6 Oct 2026 · 4:30–5:30 PM · Studio 1"),
        AttendanceBatchInfo("f1", "Bharatanatyam Foundation F1", "Tue, 6 Oct 2026 · 6:00–7:00 PM · Studio 2")
    ),
    val draftBadge: String = "Draft · Not saved",
    val batch1Students: List<AttendanceStudentItem> = listOf(
        AttendanceStudentItem("f2_1", "AR", "Ananya Rao", AttendanceStatus.PRESENT),
        AttendanceStudentItem("f2_2", "AV", "Arjun Varma", AttendanceStatus.PRESENT),
        AttendanceStudentItem("f2_3", "DM", "Diya Menon", AttendanceStatus.ABSENT),
        AttendanceStudentItem("f2_4", "KS", "Kavya Shah", AttendanceStatus.PRESENT),
        AttendanceStudentItem("f2_5", "RP", "Rohan Patel", AttendanceStatus.PRESENT),
        AttendanceStudentItem("f2_6", "SN", "Sana Nair", AttendanceStatus.EXCUSED),
        AttendanceStudentItem("f2_7", "MP", "Meera Patel", AttendanceStatus.PRESENT),
        AttendanceStudentItem("f2_8", "PS", "Priya Sundaram", AttendanceStatus.PRESENT)
    ),
    val batch2Students: List<AttendanceStudentItem> = listOf(
        AttendanceStudentItem("f1_1", "AK", "Aditi Kulkarni", AttendanceStatus.PRESENT),
        AttendanceStudentItem("f1_2", "DB", "Dev Bhasin", AttendanceStatus.ABSENT),
        AttendanceStudentItem("f1_3", "II", "Ishaan Iyer", AttendanceStatus.PRESENT),
        AttendanceStudentItem("f1_4", "MD", "Maya Deshmukh", AttendanceStatus.PRESENT),
        AttendanceStudentItem("f1_5", "NS", "Niharika Sen", AttendanceStatus.EXCUSED),
        AttendanceStudentItem("f1_6", "PJ", "Pranav Joshi", AttendanceStatus.PRESENT),
        AttendanceStudentItem("f1_7", "RV", "Riya Verma", AttendanceStatus.PRESENT),
        AttendanceStudentItem("f1_8", "TH", "Tanvi Hegde", AttendanceStatus.PRESENT)
    ),
    val noticeText: String = "Review attendance selections before saving. Changes will update student records.",
    val isSaved: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigationEvent: MarkAttendanceNavigationEvent = MarkAttendanceNavigationEvent.None
) {
    val currentStudents: List<AttendanceStudentItem>
        get() = if (selectedBatchId == "f2") batch1Students else batch2Students

    val presentCount: Int get() = currentStudents.count { it.status == AttendanceStatus.PRESENT }
    val absentCount: Int get() = currentStudents.count { it.status == AttendanceStatus.ABSENT }
    val excusedCount: Int get() = currentStudents.count { it.status == AttendanceStatus.EXCUSED }
}
