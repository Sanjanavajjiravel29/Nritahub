package com.simats.nritahub.ui.instructor

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
import androidx.compose.ui.geometry.Size
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
fun InstructorDashboardScreen(
    viewModel: InstructorDashboardViewModel = remember { InstructorDashboardViewModel() },
    onNavigateToNotifications: () -> Unit = {},
    onMarkAttendanceClicked: () -> Unit = {},
    onReviewClicked: () -> Unit = {},
    onNavigateToBatches: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            InstructorDashboardNavigationEvent.MarkAttendance -> {
                onMarkAttendanceClicked()
                viewModel.onNavigationHandled()
            }
            InstructorDashboardNavigationEvent.OpenReviewDetail -> {
                onReviewClicked()
                viewModel.onNavigationHandled()
            }
            InstructorDashboardNavigationEvent.NotificationsClicked -> {
                onNavigateToNotifications()
                viewModel.onNavigationHandled()
            }
            InstructorDashboardNavigationEvent.NavigateBack -> {
                viewModel.onNavigationHandled()
            }
            InstructorDashboardNavigationEvent.None -> {}
        }
    }

    InstructorDashboardContent(
        uiState = uiState,
        onTabSelect = { tab ->
            when (tab) {
                InstructorTab.BATCHES -> onNavigateToBatches()
                InstructorTab.REVIEWS -> onReviewClicked()
                InstructorTab.PROFILE -> onNavigateToProfile()
                else -> viewModel.onTabSelected(tab)
            }
        },
        onMarkAttendanceClick = viewModel::onMarkAttendanceClicked,
        onReviewClick = viewModel::onReviewClicked,
        onNotificationsClick = viewModel::onNotificationsClicked
    )
}

@Composable
fun InstructorDashboardContent(
    uiState: InstructorDashboardUiState,
    onTabSelect: (InstructorTab) -> Unit,
    onMarkAttendanceClick: () -> Unit,
    onReviewClick: () -> Unit,
    onNotificationsClick: () -> Unit
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
                        text = "${uiState.headerNumber} / NRITYAHUB · INSTRUCTOR DASHBOARD",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        color = MediumMaroonText
                    )
                }

                // Bell Notification Icon
                IconButton(
                    onClick = onNotificationsClick,
                    modifier = Modifier.size(28.dp)
                ) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        val w = size.width
                        val h = size.height
                        drawPath(
                            path = Path().apply {
                                moveTo(w * 0.5f, 0f)
                                cubicTo(w * 0.2f, h * 0.1f, w * 0.1f, h * 0.5f, w * 0.1f, h * 0.75f)
                                lineTo(w * 0.9f, h * 0.75f)
                                cubicTo(w * 0.9f, h * 0.5f, w * 0.8f, h * 0.1f, w * 0.5f, 0f)
                            },
                            color = DeepPlum,
                            style = Stroke(width = 1.5.dp.toPx())
                        )
                        drawLine(color = DeepPlum, start = Offset(0f, h * 0.75f), end = Offset(w, h * 0.75f), strokeWidth = 1.5.dp.toPx())
                        drawArc(
                            color = DeepPlum,
                            startAngle = 0f,
                            sweepAngle = 180f,
                            useCenter = false,
                            topLeft = Offset(w * 0.35f, h * 0.75f),
                            size = Size(w * 0.3f, h * 0.25f),
                            style = Stroke(width = 1.5.dp.toPx())
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Greeting Title
            Text(
                text = uiState.title,
                fontFamily = FontFamily.Serif,
                fontSize = 32.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Instructor Info Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(SoftLavenderBadge),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.instructorInitials,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPlum
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = uiState.dateAndAcademyText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MediumMaroonText
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Summary Stats Grid Row (3 Cards)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Card 1
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = uiState.classesTodayCount,
                            fontFamily = FontFamily.Serif,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Classes today",
                            fontSize = 11.sp,
                            color = MutedBodyText
                        )
                    }
                }

                // Card 2
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = uiState.toReviewCount,
                            fontFamily = FontFamily.Serif,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "To review",
                            fontSize = 11.sp,
                            color = MutedBodyText
                        )
                    }
                }

                // Card 3
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = uiState.totalStudentsCount,
                            fontFamily = FontFamily.Serif,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Your students",
                            fontSize = 11.sp,
                            color = MutedBodyText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Your teaching day" Section
            Text(
                text = "Your teaching day",
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Class Card 1
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
                    Box(
                        modifier = Modifier
                            .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = uiState.class1Time,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepPlum
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = uiState.class1Title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = uiState.class1Detail,
                        fontSize = 12.sp,
                        color = MutedBodyText
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.class1Status,
                            fontSize = 12.sp,
                            color = MutedBodyText
                        )

                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFDF3E5), shape = RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = uiState.class1Badge,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFA05C1C)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Class Card 2 (Active Lavender Card)
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SoftLavenderBadge),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(CardBackground, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = uiState.class2Time,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepPlum
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = uiState.class2Title,
                        fontFamily = FontFamily.Serif,
                        fontSize = 22.sp,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = uiState.class2Detail,
                        fontSize = 13.sp,
                        color = MutedBodyText
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onMarkAttendanceClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DeepPlum,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mark attendance",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Canvas(modifier = Modifier.size(16.dp)) {
                                val w = size.width
                                val h = size.height
                                drawPath(
                                    path = Path().apply {
                                        addRoundRect(
                                            RoundRect(
                                                rect = Rect(0f, h * 0.15f, w, h * 0.85f),
                                                cornerRadius = CornerRadius(4f, 4f)
                                            )
                                        )
                                    },
                                    color = Color.White,
                                    style = Stroke(width = 1.5.dp.toPx())
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Practice waiting for you" Section
            Text(
                text = "Practice waiting for you",
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
                    uiState.reviewItems.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onReviewClick() }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.studentNameAndTopic,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.submissionDetail,
                                    fontSize = 12.sp,
                                    color = MutedBodyText
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (item.isHighlighted) {
                                    Text(
                                        text = "Review",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepPlum
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                }

                                Canvas(modifier = Modifier.size(12.dp)) {
                                    val w = size.width
                                    val h = size.height
                                    val stroke = 1.5.dp.toPx()
                                    drawLine(color = DeepPlum, start = Offset(0f, 0f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                                    drawLine(color = DeepPlum, start = Offset(w, h / 2), end = Offset(0f, h), strokeWidth = stroke, cap = StrokeCap.Round)
                                }
                            }
                        }

                        if (index < uiState.reviewItems.size - 1) {
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

            // Encouragement Notice Box
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
                        // Heart Icon
                        Canvas(modifier = Modifier.size(14.dp)) {
                            val w = size.width
                            val h = size.height
                            drawPath(
                                path = Path().apply {
                                    moveTo(w * 0.5f, h * 0.85f)
                                    cubicTo(0f, h * 0.5f, 0f, h * 0.15f, w * 0.5f, h * 0.35f)
                                    cubicTo(w, h * 0.15f, w, h * 0.5f, w * 0.5f, h * 0.85f)
                                },
                                color = DeepPlum,
                                style = Stroke(width = 1.5.dp.toPx())
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = uiState.encouragementNotice,
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

@Composable
fun InstructorTabIcon(tab: InstructorTab, color: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height

        when (tab) {
            InstructorTab.TODAY -> {
                drawPath(
                    path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(0f, h * 0.2f, w, h),
                                cornerRadius = CornerRadius(4f, 4f)
                            )
                        )
                    },
                    color = color,
                    style = Stroke(width = 1.5.dp.toPx())
                )
            }
            InstructorTab.BATCHES -> {
                drawCircle(color = color, radius = w * 0.2f, center = Offset(w * 0.35f, h * 0.3f), style = Stroke(width = 1.5.dp.toPx()))
                drawCircle(color = color, radius = w * 0.2f, center = Offset(w * 0.7f, h * 0.3f), style = Stroke(width = 1.5.dp.toPx()))
            }
            InstructorTab.REVIEWS -> {
                drawPath(
                    path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(0f, 0f, w, h * 0.8f),
                                cornerRadius = CornerRadius(4f, 4f)
                            )
                        )
                    },
                    color = color,
                    style = Stroke(width = 1.5.dp.toPx())
                )
            }
            InstructorTab.PROFILE -> {
                drawCircle(color = color, radius = w * 0.25f, center = Offset(w * 0.5f, h * 0.3f), style = Stroke(width = 1.5.dp.toPx()))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InstructorDashboardScreenPreview() {
    NritahubTheme {
        InstructorDashboardContent(
            uiState = InstructorDashboardUiState(),
            onTabSelect = {},
            onMarkAttendanceClick = {},
            onReviewClick = {},
            onNotificationsClick = {}
        )
    }
}
