package com.simats.nritahub.ui.batches

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
import com.simats.nritahub.ui.admin.AdminTab
import com.simats.nritahub.ui.admin.AdminTabIcon
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
fun ManageBatchesScreen(
    viewModel: ManageBatchesViewModel = remember { ManageBatchesViewModel() },
    onNavigateToOverview: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            ManageBatchesNavigationEvent.NavigateToOverview -> {
                onNavigateToOverview()
                viewModel.onNavigationHandled()
            }
            ManageBatchesNavigationEvent.NavigateBack -> {
                onNavigateToOverview()
                viewModel.onNavigationHandled()
            }
            ManageBatchesNavigationEvent.SaveBatchSuccess -> {
                onNavigateToOverview()
                viewModel.onNavigationHandled()
            }
            ManageBatchesNavigationEvent.None -> {}
        }
    }

    ManageBatchesContent(
        uiState = uiState,
        onModeSelect = viewModel::onBatchModeSelected,
        onBatchNameChange = viewModel::onBatchNameChanged,
        onTuitionChange = viewModel::onTuitionChanged,
        onToggleNotify = viewModel::toggleNotifyStudents,
        onToggleDaySelect = viewModel::toggleDay,
        onSaveBatchClick = viewModel::onSaveBatchClicked,
        onTabSelect = viewModel::onTabSelected,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun ManageBatchesContent(
    uiState: ManageBatchesUiState,
    onModeSelect: (BatchMode) -> Unit,
    onBatchNameChange: (String) -> Unit,
    onTuitionChange: (String) -> Unit,
    onToggleNotify: () -> Unit,
    onToggleDaySelect: (String) -> Unit,
    onSaveBatchClick: () -> Unit,
    onTabSelect: (AdminTab) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = CreamBackground,
        bottomBar = {
            // Bottom 4-Tab Navigation Bar (Admin)
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
                    AdminTab.entries.forEach { tab ->
                        val isSelected = uiState.selectedTab == tab
                        val contentColor = if (isSelected) DeepPlum else MutedBodyText

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onTabSelect(tab) }
                        ) {
                            AdminTabIcon(tab = tab, color = contentColor)
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
                        text = "${uiState.headerNumber} / NRITYAHUB · MANAGE BATCHES",
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

            // Mode Toggle Segmented Control Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BatchMode.entries.forEach { mode ->
                    val isSelected = uiState.activeMode == mode
                    val chipBg = if (isSelected) DeepPlum else Color(0xFFF3ECE5)
                    val chipTextColor = if (isSelected) Color.White else DarkMaroonText
                    val label = if (mode == BatchMode.DETAILS) "Batch details" else "Schedule"

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(chipBg)
                            .clickable { onModeSelect(mode) }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = chipTextColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Selected Batch Selector Dropdown Card
            Text(
                text = "Selected batch",
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
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = uiState.selectedBatchName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )

                    Canvas(modifier = Modifier.size(12.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 1.8.dp.toPx()
                        drawLine(color = DeepPlum, start = Offset(0f, h * 0.3f), end = Offset(w * 0.5f, h * 0.8f), strokeWidth = stroke, cap = StrokeCap.Round)
                        drawLine(color = DeepPlum, start = Offset(w * 0.5f, h * 0.8f), end = Offset(w, h * 0.3f), strokeWidth = stroke, cap = StrokeCap.Round)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Badges Row
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = uiState.statusBadge,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                }

                Box(
                    modifier = Modifier
                        .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = uiState.enrollmentBadge,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Batch Name Input
            Text(
                text = "Batch name",
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
                Box(modifier = Modifier.padding(14.dp)) {
                    BasicTextField(
                        value = uiState.batchNameInput,
                        onValueChange = onBatchNameChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Dance Style & Level Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Dance style",
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
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = uiState.danceStyle, fontSize = 13.sp, color = DarkMaroonText)
                            Canvas(modifier = Modifier.size(10.dp)) {
                                val stroke = 1.5.dp.toPx()
                                drawLine(color = DeepPlum, start = Offset(0f, size.height * 0.3f), end = Offset(size.width * 0.5f, size.height * 0.8f), strokeWidth = stroke)
                                drawLine(color = DeepPlum, start = Offset(size.width * 0.5f, size.height * 0.8f), end = Offset(size.width, size.height * 0.3f), strokeWidth = stroke)
                            }
                        }
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Level",
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
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = uiState.level, fontSize = 13.sp, color = DarkMaroonText)
                            Canvas(modifier = Modifier.size(10.dp)) {
                                val stroke = 1.5.dp.toPx()
                                drawLine(color = DeepPlum, start = Offset(0f, size.height * 0.3f), end = Offset(size.width * 0.5f, size.height * 0.8f), strokeWidth = stroke)
                                drawLine(color = DeepPlum, start = Offset(size.width * 0.5f, size.height * 0.8f), end = Offset(size.width, size.height * 0.3f), strokeWidth = stroke)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Assigned Instructor Field
            Text(
                text = "Assigned instructor",
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = uiState.instructor, fontSize = 13.sp, color = DarkMaroonText)
                    Canvas(modifier = Modifier.size(10.dp)) {
                        val stroke = 1.5.dp.toPx()
                        drawLine(color = DeepPlum, start = Offset(0f, size.height * 0.3f), end = Offset(size.width * 0.5f, size.height * 0.8f), strokeWidth = stroke)
                        drawLine(color = DeepPlum, start = Offset(size.width * 0.5f, size.height * 0.8f), end = Offset(size.width, size.height * 0.3f), strokeWidth = stroke)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Capacity & Studio Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Capacity", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DarkMaroonText)
                    Spacer(modifier = Modifier.height(6.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        border = BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.padding(14.dp)) {
                            Text(text = uiState.capacity, fontSize = 13.sp, color = DarkMaroonText)
                        }
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Studio", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DarkMaroonText)
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
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = uiState.studio, fontSize = 13.sp, color = DarkMaroonText)
                            Canvas(modifier = Modifier.size(10.dp)) {
                                val stroke = 1.5.dp.toPx()
                                drawLine(color = DeepPlum, start = Offset(0f, size.height * 0.3f), end = Offset(size.width * 0.5f, size.height * 0.8f), strokeWidth = stroke)
                                drawLine(color = DeepPlum, start = Offset(size.width * 0.5f, size.height * 0.8f), end = Offset(size.width, size.height * 0.3f), strokeWidth = stroke)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Weekly meeting schedule" Section
            Text(
                text = "Weekly meeting schedule",
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Day Selection Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Tue", "Thu", "Sat").forEach { day ->
                    val isSelected = uiState.selectedDays.contains(day)
                    val chipBg = if (isSelected) DeepPlum else Color(0xFFF3ECE5)
                    val chipTextColor = if (isSelected) Color.White else DarkMaroonText

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(chipBg)
                            .clickable { onToggleDaySelect(day) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isSelected) "$day ✓" else day,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = chipTextColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Time Inputs Card
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
                    Text(
                        text = "Same time on all selected days",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Start time", fontSize = 11.sp, color = MutedBodyText)
                            Spacer(modifier = Modifier.height(4.dp))
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = CardBackground),
                                border = BorderStroke(1.dp, CardBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = uiState.startTime, fontSize = 13.sp, color = DarkMaroonText)
                                    Canvas(modifier = Modifier.size(14.dp)) {
                                        drawCircle(color = DeepPlum, radius = size.width / 2, style = Stroke(width = 1.5.dp.toPx()))
                                        drawLine(color = DeepPlum, start = Offset(size.width / 2, size.height / 2), end = Offset(size.width / 2, size.height * 0.2f), strokeWidth = 1.5.dp.toPx())
                                        drawLine(color = DeepPlum, start = Offset(size.width / 2, size.height / 2), end = Offset(size.width * 0.75f, size.height / 2), strokeWidth = 1.5.dp.toPx())
                                    }
                                }
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "End time", fontSize = 11.sp, color = MutedBodyText)
                            Spacer(modifier = Modifier.height(4.dp))
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = CardBackground),
                                border = BorderStroke(1.dp, CardBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = uiState.endTime, fontSize = 13.sp, color = DarkMaroonText)
                                    Canvas(modifier = Modifier.size(14.dp)) {
                                        drawCircle(color = DeepPlum, radius = size.width / 2, style = Stroke(width = 1.5.dp.toPx()))
                                        drawLine(color = DeepPlum, start = Offset(size.width / 2, size.height / 2), end = Offset(size.width / 2, size.height * 0.2f), strokeWidth = 1.5.dp.toPx())
                                        drawLine(color = DeepPlum, start = Offset(size.width / 2, size.height / 2), end = Offset(size.width * 0.75f, size.height / 2), strokeWidth = 1.5.dp.toPx())
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = uiState.timezoneNote,
                        fontSize = 11.sp,
                        color = MutedBodyText
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Monthly Tuition Input
            Text(
                text = "Monthly tuition",
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
                Box(modifier = Modifier.padding(14.dp)) {
                    BasicTextField(
                        value = uiState.monthlyTuition,
                        onValueChange = onTuitionChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Checkbox Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleNotify() }
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(if (uiState.notifyStudentsChecked) Color(0xFF2E7D32) else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.notifyStudentsChecked) {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            val w = size.width
                            val h = size.height
                            drawLine(color = Color.White, start = Offset(0f, h * 0.55f), end = Offset(w * 0.38f, h * 0.9f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
                            drawLine(color = Color.White, start = Offset(w * 0.38f, h * 0.9f), end = Offset(w, h * 0.15f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Notify students and linked parents of changes",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkMaroonText
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Conflict Notice Box
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
                                            cornerRadius = CornerRadius(3f, 3f)
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
                        text = if (uiState.isSaved) "Batch and schedule saved successfully!" else uiState.conflictNotice,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Button ("Save batch & schedule")
            Button(
                onClick = onSaveBatchClick,
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
                        text = if (uiState.isSaved) "Saved ✓" else "Save batch & schedule",
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
fun ManageBatchesScreenPreview() {
    NritahubTheme {
        ManageBatchesContent(
            uiState = ManageBatchesUiState(),
            onModeSelect = {},
            onBatchNameChange = {},
            onTuitionChange = {},
            onToggleNotify = {},
            onToggleDaySelect = {},
            onSaveBatchClick = {},
            onTabSelect = {},
            onBackClick = {}
        )
    }
}
