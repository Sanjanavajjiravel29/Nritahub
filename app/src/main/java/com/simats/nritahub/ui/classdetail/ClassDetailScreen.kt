package com.simats.nritahub.ui.classdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.nritahub.R
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
fun ClassDetailScreen(
    viewModel: ClassDetailViewModel = remember { ClassDetailViewModel() },
    onBackToPrevious: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            ClassDetailNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            ClassDetailNavigationEvent.AddedToCalendar -> {
                viewModel.onNavigationHandled()
            }
            ClassDetailNavigationEvent.ViewPreparationResources -> {
                viewModel.onNavigationHandled()
            }
            ClassDetailNavigationEvent.None -> {}
        }
    }

    ClassDetailContent(
        uiState = uiState,
        onToggleItem = viewModel::togglePreparationItem,
        onAddToCalendarClick = viewModel::onAddToCalendarClicked,
        onViewPreparationResourcesClick = viewModel::onViewPreparationResourcesClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun ClassDetailContent(
    uiState: ClassDetailUiState,
    onToggleItem: (Int) -> Unit,
    onAddToCalendarClick: () -> Unit,
    onViewPreparationResourcesClick: () -> Unit,
    onBackClick: () -> Unit
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
                        text = "${uiState.headerNumber} / NRITYAHUB · CLASS DETAIL",
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

            Spacer(modifier = Modifier.height(16.dp))

            // Main Title
            Text(
                text = uiState.title,
                fontFamily = FontFamily.Serif,
                fontSize = 32.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Class Image Banner Card
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.splash),
                    contentDescription = "Class Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Status & Batch Badges Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Confirmed Badge (Green)
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = uiState.statusBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = Color(0xFF2E7D32)
                    )
                }

                // Foundation Badge (Soft Pink/Plum)
                Box(
                    modifier = Modifier
                        .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = uiState.batchBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Class Details & Schedule Card
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
                    // Date & Time Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = uiState.dateText,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.timeText,
                                fontSize = 13.sp,
                                color = MutedBodyText
                            )
                        }

                        // Calendar Icon
                        Canvas(modifier = Modifier.size(18.dp)) {
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Location Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = uiState.studioName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.studioLocation,
                                fontSize = 13.sp,
                                color = MutedBodyText
                            )
                        }

                        // Location Pin Icon
                        Canvas(modifier = Modifier.size(18.dp)) {
                            val w = size.width
                            val h = size.height
                            drawPath(
                                path = Path().apply {
                                    moveTo(w * 0.5f, h)
                                    cubicTo(0f, h * 0.6f, 0f, h * 0.2f, w * 0.5f, 0f)
                                    cubicTo(w, h * 0.2f, w, h * 0.6f, w * 0.5f, h)
                                },
                                color = DeepPlum,
                                style = Stroke(width = 1.5.dp.toPx())
                            )
                            drawCircle(color = DeepPlum, radius = 2.5f, center = Offset(w * 0.5f, h * 0.35f))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Instructor Row
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

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = uiState.instructorNameAndRole,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Today's focus" Section
            Text(
                text = uiState.todaysFocusTitle,
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = uiState.todaysFocusDescription,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // "Come prepared" Card Box
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3ECE5)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Text(
                        text = "Come prepared",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Checklist Items
                    uiState.preparationItems.forEachIndexed { index, item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onToggleItem(index) }
                                .padding(vertical = 4.dp)
                        ) {
                            Canvas(modifier = Modifier.size(18.dp)) {
                                val r = size.width / 2
                                if (item.isCompleted) {
                                    drawCircle(color = Color(0xFF2E7D32), radius = r)
                                    drawPath(
                                        path = Path().apply {
                                            moveTo(r * 0.5f, r)
                                            lineTo(r * 0.85f, r * 1.35f)
                                            lineTo(r * 1.5f, r * 0.65f)
                                        },
                                        color = Color.White,
                                        style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
                                    )
                                } else {
                                    drawCircle(color = CardBorder, radius = r, style = Stroke(width = 1.5.dp.toPx()))
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = item.title,
                                fontSize = 13.sp,
                                color = DarkMaroonText
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = uiState.arrivalNotice,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = MutedBodyText
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action Button ("Add to calendar")
            Button(
                onClick = onAddToCalendarClick,
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
                        text = "Add to calendar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        drawPath(
                            path = Path().apply {
                                addRoundRect(
                                    androidx.compose.ui.geometry.RoundRect(
                                        rect = androidx.compose.ui.geometry.Rect(0f, h * 0.2f, w, h),
                                        cornerRadius = CornerRadius(4f, 4f)
                                    )
                                )
                            },
                            color = Color.White,
                            style = Stroke(width = 1.5.dp.toPx())
                        )
                        drawLine(color = Color.White, start = Offset(w * 0.3f, 0f), end = Offset(w * 0.3f, h * 0.3f), strokeWidth = 1.5.dp.toPx())
                        drawLine(color = Color.White, start = Offset(w * 0.7f, 0f), end = Offset(w * 0.7f, h * 0.3f), strokeWidth = 1.5.dp.toPx())
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Secondary Action Button ("View preparation resources")
            OutlinedButton(
                onClick = onViewPreparationResourcesClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = CardBackground,
                    contentColor = DeepPlum
                ),
                border = BorderStroke(1.dp, CardBorder),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "View preparation resources",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DeepPlum
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClassDetailScreenPreview() {
    NritahubTheme {
        ClassDetailContent(
            uiState = ClassDetailUiState(),
            onToggleItem = {},
            onAddToCalendarClick = {},
            onViewPreparationResourcesClick = {},
            onBackClick = {}
        )
    }
}
