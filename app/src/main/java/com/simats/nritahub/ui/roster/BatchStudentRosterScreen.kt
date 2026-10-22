package com.simats.nritahub.ui.roster

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.nritahub.ui.instructor.InstructorTab
import com.simats.nritahub.ui.instructor.InstructorTabIcon
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
fun BatchStudentRosterScreen(
    viewModel: BatchStudentRosterViewModel = remember { BatchStudentRosterViewModel() },
    onNavigateToToday: () -> Unit,
    onNavigateToAttendance: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            BatchStudentRosterNavigationEvent.NavigateToToday -> {
                onNavigateToToday()
                viewModel.onNavigationHandled()
            }
            BatchStudentRosterNavigationEvent.NavigateBack -> {
                onNavigateToToday()
                viewModel.onNavigationHandled()
            }
            BatchStudentRosterNavigationEvent.OpenBatchAttendanceClicked -> {
                onNavigateToAttendance()
                viewModel.onNavigationHandled()
            }
            is BatchStudentRosterNavigationEvent.StudentClicked -> {
                viewModel.onNavigationHandled()
            }
            BatchStudentRosterNavigationEvent.None -> {}
        }
    }

    BatchStudentRosterContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChanged,
        onFilterSelect = viewModel::onFilterSelected,
        onStudentClick = viewModel::onStudentClicked,
        onOpenBatchAttendanceClick = viewModel::onOpenBatchAttendanceClicked,
        onTabSelect = viewModel::onTabSelected,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun BatchStudentRosterContent(
    uiState: BatchStudentRosterUiState,
    onSearchQueryChange: (String) -> Unit,
    onFilterSelect: (RosterFilter) -> Unit,
    onStudentClick: (StudentRosterItem) -> Unit,
    onOpenBatchAttendanceClick: () -> Unit,
    onTabSelect: (InstructorTab) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = CreamBackground,
        bottomBar = {
            // Bottom 4-Tab Navigation Bar (Instructor)
            Card(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InstructorTab.entries.forEach { tab ->
                        val isSelected = uiState.selectedTab == tab
                        val contentColor = if (isSelected) DeepPlum else MutedBodyText

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onTabSelect(tab) }
                        ) {
                            InstructorTabIcon(tab = tab, color = contentColor)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tab.name.lowercase().replaceFirstChar { it.uppercase() },
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = contentColor
                            )
                        }
                    }
                }
            }
        }
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
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${uiState.headerNumber} / NRITYAHUB · BATCH STUDENT ROSTER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        color = MediumMaroonText
                    )
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

            // Active Batch Header Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SoftLavenderBadge),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Text(
                        text = uiState.batchTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = uiState.batchSchedule,
                        fontSize = 13.sp,
                        color = MutedBodyText
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .background(CardBackground, shape = RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = uiState.studentCountBadge,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepPlum
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(CardBackground, shape = RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = uiState.studioBadge,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepPlum
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Batch Section
            Text(
                text = "Search batch",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(6.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (uiState.searchQuery.isEmpty()) {
                            Text(
                                text = "Search student name",
                                fontSize = 13.sp,
                                color = MutedBodyText
                            )
                        }
                        BasicTextField(
                            value = uiState.searchQuery,
                            onValueChange = onSearchQueryChange,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Search Icon
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        drawCircle(color = DeepPlum, radius = w * 0.35f, center = Offset(w * 0.4f, h * 0.4f), style = Stroke(width = 1.5.dp.toPx()))
                        drawLine(color = DeepPlum, start = Offset(w * 0.65f, h * 0.65f), end = Offset(w, h), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Category Filter Chips Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RosterFilter.entries.forEach { filter ->
                    val isSelected = uiState.selectedFilter == filter
                    val chipBg = if (isSelected) DeepPlum else Color(0xFFF3ECE5)
                    val chipTextColor = if (isSelected) Color.White else DarkMaroonText
                    val label = when (filter) {
                        RosterFilter.ALL -> "All"
                        RosterFilter.NEEDS_FOLLOW_UP -> "Needs follow-up"
                        RosterFilter.NEW -> "New"
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(chipBg)
                            .clickable { onFilterSelect(filter) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = chipTextColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // "September attendance" Section Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = uiState.selectedMonthTitle,
                    fontFamily = FontFamily.Serif,
                    fontSize = 22.sp,
                    color = DarkMaroonText
                )

                Text(
                    text = "Name A–Z ⇅",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepPlum
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Roster Students Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    val filteredStudents = if (uiState.searchQuery.isBlank()) {
                        uiState.students
                    } else {
                        uiState.students.filter { it.name.contains(uiState.searchQuery, ignoreCase = true) }
                    }

                    filteredStudents.forEachIndexed { index, student ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onStudentClick(student) }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
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

                                Column {
                                    Text(
                                        text = student.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DarkMaroonText
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = student.levelAndAttendance,
                                        fontSize = 12.sp,
                                        color = MutedBodyText
                                    )
                                }
                            }

                            Canvas(modifier = Modifier.size(12.dp)) {
                                val w = size.width
                                val h = size.height
                                val stroke = 1.5.dp.toPx()
                                drawLine(color = DeepPlum, start = Offset(0f, 0f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                                drawLine(color = DeepPlum, start = Offset(w, h / 2), end = Offset(0f, h), strokeWidth = stroke, cap = StrokeCap.Round)
                            }
                        }

                        if (index < filteredStudents.size - 1) {
                            HorizontalDivider(
                                color = CardBorder,
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // View all link
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = uiState.viewAllText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepPlum
                )
                Spacer(modifier = Modifier.width(6.dp))
                Canvas(modifier = Modifier.size(12.dp)) {
                    val w = size.width
                    val h = size.height
                    val stroke = 1.5.dp.toPx()
                    drawLine(color = DeepPlum, start = Offset(0f, h / 2), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                    drawLine(color = DeepPlum, start = Offset(w * 0.6f, h * 0.2f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                    drawLine(color = DeepPlum, start = Offset(w * 0.6f, h * 0.8f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Guidance Note
            Text(
                text = uiState.guidanceNotice,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = MutedBodyText,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Open batch attendance button
            OutlinedButton(
                onClick = onOpenBatchAttendanceClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = CardBackground,
                    contentColor = DeepPlum
                ),
                border = BorderStroke(1.dp, CardBorder),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Open batch attendance",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepPlum
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BatchStudentRosterScreenPreview() {
    NritahubTheme {
        BatchStudentRosterContent(
            uiState = BatchStudentRosterUiState(),
            onSearchQueryChange = {},
            onFilterSelect = {},
            onStudentClick = {},
            onOpenBatchAttendanceClick = {},
            onTabSelect = {},
            onBackClick = {}
        )
    }
}
