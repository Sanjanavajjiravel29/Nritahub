package com.simats.nritahub.ui.markattendance

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.nritahub.ui.theme.CardBackground
import com.simats.nritahub.ui.theme.CardBorder
import com.simats.nritahub.ui.theme.CreamBackground
import com.simats.nritahub.ui.theme.DarkMaroonText
import com.simats.nritahub.ui.theme.DeepPlum
import com.simats.nritahub.ui.theme.MediumMaroonText
import com.simats.nritahub.ui.theme.MutedBodyText
import com.simats.nritahub.ui.theme.NritahubTheme
import com.simats.nritahub.ui.theme.SoftLavenderBadge

@Composable
fun MarkAttendanceScreen(
    viewModel: MarkAttendanceViewModel = remember { MarkAttendanceViewModel() },
    onBackToPrevious: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            MarkAttendanceNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            MarkAttendanceNavigationEvent.SaveAttendanceSuccess -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            MarkAttendanceNavigationEvent.None -> {}
        }
    }

    MarkAttendanceContent(
        uiState = uiState,
        onBatchSelect = viewModel::onBatchSelected,
        onSetStatus = viewModel::setStudentStatus,
        onSaveAttendanceClick = viewModel::onSaveAttendanceClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun MarkAttendanceContent(
    uiState: MarkAttendanceUiState,
    onBatchSelect: (String) -> Unit = {},
    onSetStatus: (id: String, status: AttendanceStatus) -> Unit = { _, _ -> },
    onSaveAttendanceClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = CreamBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 2.dp.toPx()
                        drawLine(
                            color = DeepPlum,
                            start = Offset(w, h / 2),
                            end = Offset(0f, h / 2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = DeepPlum,
                            start = Offset(w * 0.4f, h * 0.2f),
                            end = Offset(0f, h / 2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = DeepPlum,
                            start = Offset(w * 0.4f, h * 0.8f),
                            end = Offset(0f, h / 2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                    }
                }

                // Three dots trailing icon
                Canvas(modifier = Modifier.size(16.dp)) {
                    val r = 1.8.dp.toPx()
                    val centerY = size.height / 2
                    val w = size.width
                    drawCircle(color = MediumMaroonText, radius = r, center = Offset(w * 0.2f, centerY))
                    drawCircle(color = MediumMaroonText, radius = r, center = Offset(w * 0.5f, centerY))
                    drawCircle(color = MediumMaroonText, radius = r, center = Offset(w * 0.8f, centerY))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Title
            Text(
                text = uiState.title,
                fontFamily = FontFamily.Serif,
                fontSize = 32.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2 Batches Selection Cards
            Text(
                text = "Select batch",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                uiState.batches.forEach { batch ->
                    val isSelected = uiState.selectedBatchId == batch.id
                    val cardBg = if (isSelected) SoftLavenderBadge else CardBackground
                    val cardBorder = if (isSelected) BorderStroke(1.5.dp, DeepPlum) else BorderStroke(1.dp, CardBorder)

                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBg),
                        border = cardBorder,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onBatchSelect(batch.id) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = batch.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )

                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .background(DeepPlum, shape = RoundedCornerShape(10.dp))
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = "Active",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = batch.scheduleAndStudio,
                                fontSize = 12.sp,
                                color = MutedBodyText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Summary Stats Grid Row (3 Cards)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "${uiState.presentCount}",
                            fontFamily = FontFamily.Serif,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Present",
                            fontSize = 11.sp,
                            color = MutedBodyText
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "${uiState.absentCount}",
                            fontFamily = FontFamily.Serif,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Absent",
                            fontSize = 11.sp,
                            color = MutedBodyText
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "${uiState.excusedCount}",
                            fontFamily = FontFamily.Serif,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Excused",
                            fontSize = 11.sp,
                            color = MutedBodyText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Active Batch Students List Header
            val activeBatch = uiState.batches.find { it.id == uiState.selectedBatchId } ?: uiState.batches.first()

            Text(
                text = "${activeBatch.title} Roster",
                fontFamily = FontFamily.Serif,
                fontSize = 18.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Students List Cards
            uiState.currentStudents.forEach { student ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(SoftLavenderBadge),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = student.initials,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepPlum
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = student.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // 3 Attendance Toggle Buttons Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Present Button
                            val isPresent = student.status == AttendanceStatus.PRESENT
                            val presentBg = if (isPresent) Color(0xFF2E5B44) else Color(0xFFF3ECE5)
                            val presentTextColor = if (isPresent) Color.White else DarkMaroonText

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(presentBg)
                                    .clickable { onSetStatus(student.id, AttendanceStatus.PRESENT) }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Present",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = presentTextColor
                                )
                            }

                            // Absent Button
                            val isAbsent = student.status == AttendanceStatus.ABSENT
                            val absentBg = if (isAbsent) Color(0xFF944242) else Color(0xFFF3ECE5)
                            val absentTextColor = if (isAbsent) Color.White else DarkMaroonText

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(absentBg)
                                    .clickable { onSetStatus(student.id, AttendanceStatus.ABSENT) }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Absent",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = absentTextColor
                                )
                            }

                            // Excused Button
                            val isExcused = student.status == AttendanceStatus.EXCUSED
                            val excusedBg = if (isExcused) Color(0xFF8B5E2B) else Color(0xFFF3ECE5)
                            val excusedTextColor = if (isExcused) Color.White else DarkMaroonText

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(excusedBg)
                                    .clickable { onSetStatus(student.id, AttendanceStatus.EXCUSED) }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Excused",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = excusedTextColor
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Review Guidance Notice Box
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SoftLavenderBadge),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(CardBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(14.dp)) {
                            val w = size.width
                            val h = size.height
                            drawPath(
                                path = Path().apply {
                                    addRoundRect(
                                        RoundRect(
                                            rect = Rect(0f, h * 0.2f, w, h),
                                            cornerRadius = CornerRadius(4f, 4f)
                                        )
                                    )
                                },
                                color = DeepPlum,
                                style = Stroke(width = 1.5.dp.toPx())
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = if (uiState.isSaved) "Attendance saved successfully!" else uiState.noticeText,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action Button ("Save attendance")
            Button(
                onClick = onSaveAttendanceClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepPlum,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (uiState.isSaved) "Saved ✓" else "Save attendance",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 2.dp.toPx()
                        drawLine(color = Color.White, start = Offset(0f, h * 0.55f), end = Offset(w * 0.38f, h * 0.9f), strokeWidth = stroke, cap = StrokeCap.Round)
                        drawLine(color = Color.White, start = Offset(w * 0.38f, h * 0.9f), end = Offset(w, h * 0.15f), strokeWidth = stroke, cap = StrokeCap.Round)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarkAttendanceScreenPreview() {
    NritahubTheme {
        MarkAttendanceContent(
            uiState = MarkAttendanceUiState(),
            onBatchSelect = {},
            onSetStatus = { _, _ -> },
            onSaveAttendanceClick = {},
            onBackClick = {}
        )
    }
}
