package com.simats.nritahub.ui.progress

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.nritahub.ui.home.HomeTab
import com.simats.nritahub.ui.home.TabIcon
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
fun MyProgressScreen(
    viewModel: MyProgressViewModel = remember { MyProgressViewModel() },
    onNavigateToHome: () -> Unit,
    onNavigateToSchedule: () -> Unit = {},
    onNavigateToPractice: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToAttendanceHistory: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            MyProgressNavigationEvent.NavigateToHome -> {
                onNavigateToHome()
                viewModel.onNavigationHandled()
            }
            MyProgressNavigationEvent.ExportReport -> {
                onNavigateToAttendanceHistory()
                viewModel.onNavigationHandled()
            }
            MyProgressNavigationEvent.None -> {}
        }
    }

    MyProgressContent(
        uiState = uiState,
        onTimeframeChange = viewModel::onTimeframeChanged,
        onTabSelect = { tab ->
            when (tab) {
                HomeTab.HOME -> onNavigateToHome()
                HomeTab.SCHEDULE -> onNavigateToSchedule()
                HomeTab.PRACTICE -> onNavigateToPractice()
                HomeTab.PROGRESS -> viewModel.onTabSelected(tab)
                HomeTab.PROFILE -> onNavigateToProfile()
            }
        },
        onExportReportClick = viewModel::onExportReportClicked
    )
}

@Composable
fun MyProgressContent(
    uiState: MyProgressUiState,
    onTimeframeChange: (ProgressTimeframe) -> Unit,
    onTabSelect: (HomeTab) -> Unit,
    onExportReportClick: () -> Unit
) {
    Scaffold(
        containerColor = CreamBackground,
        bottomBar = {
            // Bottom 5-Tab Navigation Bar
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
                    HomeTab.entries.forEach { tab ->
                        val isSelected = uiState.selectedTab == tab
                        val contentColor = if (isSelected) DeepPlum else MutedBodyText

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onTabSelect(tab) }
                        ) {
                            TabIcon(tab = tab, color = contentColor)
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
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(SoftLavenderBadge),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            val w = size.width
                            val h = size.height
                            drawPath(
                                path = Path().apply {
                                    moveTo(w * 0.5f, 0f)
                                    lineTo(w, h * 0.35f)
                                    lineTo(0f, h * 0.35f)
                                    close()
                                },
                                color = DeepPlum
                            )
                            drawLine(color = DeepPlum, start = Offset(w * 0.25f, h * 0.45f), end = Offset(w * 0.25f, h * 0.9f), strokeWidth = 2f)
                            drawLine(color = DeepPlum, start = Offset(w * 0.75f, h * 0.45f), end = Offset(w * 0.75f, h * 0.9f), strokeWidth = 2f)
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${uiState.headerNumber} / NRITYAHUB · MY PROGRESS",
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

            // Timeframe Segmented Switch
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3ECE5)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (uiState.timeframe == ProgressTimeframe.THIS_WEEK) DeepPlum else Color.Transparent)
                            .clickable { onTimeframeChange(ProgressTimeframe.THIS_WEEK) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "This week",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (uiState.timeframe == ProgressTimeframe.THIS_WEEK) Color.White else DarkMaroonText
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (uiState.timeframe == ProgressTimeframe.THIS_MONTH) DeepPlum else Color.Transparent)
                            .clickable { onTimeframeChange(ProgressTimeframe.THIS_MONTH) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "This month",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (uiState.timeframe == ProgressTimeframe.THIS_MONTH) Color.White else DarkMaroonText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Overview Subtitle
            Text(
                text = uiState.overviewSubtitle,
                fontSize = 12.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Stats Grid Row (Attendance & Weekly Practice)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = uiState.attendancePercentage,
                            fontFamily = FontFamily.Serif,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = uiState.attendanceLabel,
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
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = uiState.practiceWeeklyScore,
                            fontFamily = FontFamily.Serif,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = uiState.practiceWeeklyLabel,
                            fontSize = 11.sp,
                            color = MutedBodyText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Practice Consistency Bar Chart Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.consistencyTitle,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )

                        Box(
                            modifier = Modifier
                                .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = uiState.habitBadge,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = uiState.consistencySubtitle,
                        fontSize = 12.sp,
                        color = MutedBodyText
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 4-Bar Chart
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        uiState.weeklyBars.forEach { bar ->
                            val barHeightDp = (bar.sessionCount * 22).dp
                            val barBg = if (bar.isHighlighted) DeepPlum else SoftLavenderBadge

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${bar.sessionCount}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Box(
                                    modifier = Modifier
                                        .width(48.dp)
                                        .height(barHeightDp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(barBg)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = bar.weekLabel,
                                    fontSize = 11.sp,
                                    color = MutedBodyText
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Skills taking shape" Section
            Text(
                text = uiState.skillsTitle,
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    uiState.skills.forEachIndexed { index, skill ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = skill.skillName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = skill.status,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepPlum
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Canvas(modifier = Modifier.size(16.dp)) {
                                    val w = size.width
                                    val h = size.height
                                    when (skill.iconType) {
                                        "note" -> {
                                            drawCircle(color = DeepPlum, radius = 3f, center = Offset(w * 0.35f, h * 0.75f))
                                            drawLine(color = DeepPlum, start = Offset(w * 0.45f, h * 0.75f), end = Offset(w * 0.45f, h * 0.2f), strokeWidth = 1.5.dp.toPx())
                                        }
                                        "plant" -> {
                                            drawLine(color = DeepPlum, start = Offset(w * 0.5f, h), end = Offset(w * 0.5f, h * 0.2f), strokeWidth = 1.5.dp.toPx())
                                            drawCircle(color = DeepPlum, radius = 3.5f, center = Offset(w * 0.3f, h * 0.4f))
                                            drawCircle(color = DeepPlum, radius = 3.5f, center = Offset(w * 0.7f, h * 0.3f))
                                        }
                                        "hand" -> {
                                            drawCircle(color = DeepPlum, radius = size.width * 0.35f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(width = 1.5.dp.toPx()))
                                        }
                                    }
                                }
                            }
                        }

                        if (index < uiState.skills.size - 1) {
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

            // Instructor Feedback Note Box
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(CardBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = uiState.instructorInitials,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepPlum
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = uiState.instructorNoteTitle,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = uiState.instructorNoteQuote,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MutedBodyText
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = uiState.nextFocusText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Export Progress Report Button
            OutlinedButton(
                onClick = onExportReportClick,
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
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Export progress report",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPlum
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        drawLine(color = DeepPlum, start = Offset(w * 0.5f, 0f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.5.dp.toPx())
                        drawLine(color = DeepPlum, start = Offset(w * 0.25f, h * 0.45f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.5.dp.toPx())
                        drawLine(color = DeepPlum, start = Offset(w * 0.75f, h * 0.45f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.5.dp.toPx())
                        drawLine(color = DeepPlum, start = Offset(0f, h), end = Offset(w, h), strokeWidth = 1.5.dp.toPx())
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyProgressScreenPreview() {
    NritahubTheme {
        MyProgressContent(
            uiState = MyProgressUiState(),
            onTimeframeChange = {},
            onTabSelect = {},
            onExportReportClick = {}
        )
    }
}
