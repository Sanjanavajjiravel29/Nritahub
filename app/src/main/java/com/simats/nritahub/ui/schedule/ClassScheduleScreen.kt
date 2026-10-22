package com.simats.nritahub.ui.schedule

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
fun ClassScheduleScreen(
    viewModel: ClassScheduleViewModel = remember { ClassScheduleViewModel() },
    onNavigateToHome: () -> Unit,
    onNavigateToClassDetail: () -> Unit,
    onNavigateToPractice: () -> Unit = {},
    onNavigateToProgress: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            ClassScheduleNavigationEvent.NavigateToHome -> {
                onNavigateToHome()
                viewModel.onNavigationHandled()
            }
            ClassScheduleNavigationEvent.NavigateToClassDetail -> {
                onNavigateToClassDetail()
                viewModel.onNavigationHandled()
            }
            ClassScheduleNavigationEvent.None -> {}
        }
    }

    ClassScheduleContent(
        uiState = uiState,
        onViewModeChange = viewModel::onViewModeChanged,
        onDaySelect = viewModel::onDaySelected,
        onTabSelect = { tab ->
            when (tab) {
                HomeTab.HOME -> onNavigateToHome()
                HomeTab.SCHEDULE -> viewModel.onTabSelected(tab)
                HomeTab.PRACTICE -> onNavigateToPractice()
                HomeTab.PROGRESS -> onNavigateToProgress()
                HomeTab.PROFILE -> onNavigateToProfile()
            }
        },
        onClassClick = viewModel::onClassClicked
    )
}

@Composable
fun ClassScheduleContent(
    uiState: ClassScheduleUiState,
    onViewModeChange: (ScheduleViewMode) -> Unit,
    onDaySelect: (String) -> Unit,
    onTabSelect: (HomeTab) -> Unit,
    onClassClick: () -> Unit
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
            // Top Bar / Header
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
                        text = "${uiState.headerNumber} / NRITYAHUB · CLASS SCHEDULE",
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

            // Date Range Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 1.8.dp.toPx()
                        drawLine(color = DarkMaroonText, start = Offset(w, 0f), end = Offset(0f, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                        drawLine(color = DarkMaroonText, start = Offset(0f, h / 2), end = Offset(w, h), strokeWidth = stroke, cap = StrokeCap.Round)
                    }
                }

                Text(
                    text = uiState.dateRangeText,
                    fontFamily = FontFamily.Serif,
                    fontSize = 22.sp,
                    color = DarkMaroonText
                )

                IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 1.8.dp.toPx()
                        drawLine(color = DarkMaroonText, start = Offset(0f, 0f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                        drawLine(color = DarkMaroonText, start = Offset(w, h / 2), end = Offset(0f, h), strokeWidth = stroke, cap = StrokeCap.Round)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Week / Day Toggle Switch
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
                            .background(if (uiState.viewMode == ScheduleViewMode.WEEK) DeepPlum else Color.Transparent)
                            .clickable { onViewModeChange(ScheduleViewMode.WEEK) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Week",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (uiState.viewMode == ScheduleViewMode.WEEK) Color.White else DarkMaroonText
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (uiState.viewMode == ScheduleViewMode.DAY) DeepPlum else Color.Transparent)
                            .clickable { onViewModeChange(ScheduleViewMode.DAY) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Day",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (uiState.viewMode == ScheduleViewMode.DAY) Color.White else DarkMaroonText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 7-Day Calendar Strip
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EFE8)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    uiState.days.forEach { day ->
                        val isSelected = day.isSelected
                        val containerBg = if (isSelected) DeepPlum else Color.Transparent
                        val textColor = if (isSelected) Color.White else DarkMaroonText

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(containerBg)
                                .clickable { onDaySelect(day.dayNumber) }
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = day.dayName,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = textColor
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = day.dayNumber,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Selected Day Banner
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = uiState.selectedDayText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkMaroonText
                )

                Box(
                    modifier = Modifier
                        .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = uiState.classCountBadge,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Today's Confirmed Class Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SoftLavenderBadge),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClassClick() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = uiState.todayBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = DeepPlum
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = uiState.className,
                        fontFamily = FontFamily.Serif,
                        fontSize = 24.sp,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = uiState.classTime,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = uiState.classStudioAndInstructor,
                        fontSize = 13.sp,
                        color = MutedBodyText
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = uiState.classTopic,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.classNotebookNote,
                                fontSize = 12.sp,
                                color = MutedBodyText
                            )
                        }

                        // Chevron Arrow
                        Canvas(modifier = Modifier.size(14.dp)) {
                            val w = size.width
                            val h = size.height
                            val stroke = 1.8.dp.toPx()
                            drawLine(color = DeepPlum, start = Offset(0f, 0f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                            drawLine(color = DeepPlum, start = Offset(w, h / 2), end = Offset(0f, h), strokeWidth = stroke, cap = StrokeCap.Round)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Coming up" Section
            Text(
                text = "Coming up",
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
                    uiState.upcomingClasses.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onClassClick() }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = item.dateAndTime,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.description,
                                    fontSize = 12.sp,
                                    color = MutedBodyText
                                )
                            }

                            Canvas(modifier = Modifier.size(12.dp)) {
                                val w = size.width
                                val h = size.height
                                val stroke = 1.5.dp.toPx()
                                drawLine(color = DeepPlum, start = Offset(0f, 0f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                                drawLine(color = DeepPlum, start = Offset(w, h / 2), end = Offset(0f, h), strokeWidth = stroke, cap = StrokeCap.Round)
                            }
                        }

                        if (index < uiState.upcomingClasses.size - 1) {
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

            // Schedule Notice Box
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
                        // Calendar Icon
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
                            drawLine(color = DeepPlum, start = Offset(w * 0.3f, 0f), end = Offset(w * 0.3f, h * 0.3f), strokeWidth = 1.5.dp.toPx())
                            drawLine(color = DeepPlum, start = Offset(w * 0.7f, 0f), end = Offset(w * 0.7f, h * 0.3f), strokeWidth = 1.5.dp.toPx())
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = uiState.scheduleNotice,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClassScheduleScreenPreview() {
    NritahubTheme {
        ClassScheduleContent(
            uiState = ClassScheduleUiState(),
            onViewModeChange = {},
            onDaySelect = {},
            onTabSelect = {},
            onClassClick = {}
        )
    }
}
