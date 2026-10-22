package com.simats.nritahub.ui.assignment

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
fun AssignmentDetailScreen(
    viewModel: AssignmentDetailViewModel = remember { AssignmentDetailViewModel() },
    onBackToPrevious: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            AssignmentDetailNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            AssignmentDetailNavigationEvent.OpenResource -> {
                viewModel.onNavigationHandled()
            }
            AssignmentDetailNavigationEvent.PracticeSubmitted -> {
                onSubmitSuccess()
                viewModel.onNavigationHandled()
            }
            AssignmentDetailNavigationEvent.None -> {}
        }
    }

    AssignmentDetailContent(
        uiState = uiState,
        onToggleTask = viewModel::toggleChecklistTask,
        onResourceClick = viewModel::onResourceClicked,
        onSubmitPracticeClick = viewModel::onSubmitPracticeClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun AssignmentDetailContent(
    uiState: AssignmentDetailUiState,
    onToggleTask: (Int) -> Unit,
    onResourceClick: (AttachedResource) -> Unit,
    onSubmitPracticeClick: () -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · ASSIGNMENT DETAIL",
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

            Spacer(modifier = Modifier.height(14.dp))

            // Status Badges Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // In Progress Badge
                Box(
                    modifier = Modifier
                        .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = uiState.statusBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = DeepPlum
                    )
                }

                // Due Date Badge (Warm Yellow/Orange)
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFDF3E5), shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = uiState.dueDateBadge,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFA05C1C)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Overview Banner Card
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
                    Text(
                        text = uiState.bannerTitle,
                        fontFamily = FontFamily.Serif,
                        fontSize = 22.sp,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = uiState.bannerSubtitle,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MutedBodyText
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
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
                            text = uiState.instructorName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Guidance Subtitle
            Text(
                text = uiState.guidanceText,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(20.dp))

            // "Your daily checklist" Section
            Text(
                text = "Your daily checklist",
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    uiState.checklistTasks.forEachIndexed { index, task ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onToggleTask(index) }
                                .padding(vertical = 8.dp)
                        ) {
                            Canvas(modifier = Modifier.size(20.dp)) {
                                val r = size.width / 2
                                if (task.isCompleted) {
                                    drawCircle(color = Color(0xFF2E7D32), radius = r)
                                    drawPath(
                                        path = Path().apply {
                                            moveTo(r * 0.5f, r)
                                            lineTo(r * 0.85f, r * 1.35f)
                                            lineTo(r * 1.5f, r * 0.65f)
                                        },
                                        color = Color.White,
                                        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                                    )
                                } else {
                                    drawCircle(color = CardBorder, radius = r, style = Stroke(width = 1.5.dp.toPx()))
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = task.title,
                                fontSize = 13.sp,
                                fontWeight = if (task.isCompleted) FontWeight.SemiBold else FontWeight.Normal,
                                color = DarkMaroonText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Attached resources" Section
            Text(
                text = "Attached resources",
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
                    uiState.attachedResources.forEachIndexed { index, resource ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onResourceClick(resource) }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = resource.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = resource.subtitle,
                                    fontSize = 12.sp,
                                    color = MutedBodyText
                                )
                            }

                            // Resource Type Icon
                            Canvas(modifier = Modifier.size(20.dp)) {
                                val w = size.width
                                val h = size.height
                                if (resource.resourceType == "video") {
                                    drawCircle(color = SoftLavenderBadge, radius = w / 2)
                                    drawPath(
                                        path = Path().apply {
                                            moveTo(w * 0.4f, h * 0.3f)
                                            lineTo(w * 0.7f, h * 0.5f)
                                            lineTo(w * 0.4f, h * 0.7f)
                                            close()
                                        },
                                        color = DeepPlum
                                    )
                                } else { // "pdf" or document
                                    drawPath(
                                        path = Path().apply {
                                            moveTo(0f, 0f)
                                            lineTo(w * 0.7f, 0f)
                                            lineTo(w, h * 0.3f)
                                            lineTo(w, h)
                                            lineTo(0f, h)
                                            close()
                                        },
                                        color = DeepPlum,
                                        style = Stroke(width = 1.5.dp.toPx())
                                    )
                                }
                            }
                        }

                        if (index < uiState.attachedResources.size - 1) {
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

            // Mindful Practice Notice Box
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
                        text = uiState.mindfulNotice,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action Button ("Submit practice")
            Button(
                onClick = onSubmitPracticeClick,
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
                        text = "Submit practice",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Upload Icon
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 1.8.dp.toPx()
                        drawLine(color = Color.White, start = Offset(w * 0.5f, h), end = Offset(w * 0.5f, h * 0.2f), strokeWidth = stroke, cap = StrokeCap.Round)
                        drawLine(color = Color.White, start = Offset(w * 0.2f, h * 0.5f), end = Offset(w * 0.5f, h * 0.2f), strokeWidth = stroke, cap = StrokeCap.Round)
                        drawLine(color = Color.White, start = Offset(w * 0.8f, h * 0.5f), end = Offset(w * 0.5f, h * 0.2f), strokeWidth = stroke, cap = StrokeCap.Round)
                        drawLine(color = Color.White, start = Offset(0f, h), end = Offset(w, h), strokeWidth = stroke, cap = StrokeCap.Round)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssignmentDetailScreenPreview() {
    NritahubTheme {
        AssignmentDetailContent(
            uiState = AssignmentDetailUiState(),
            onToggleTask = {},
            onResourceClick = {},
            onSubmitPracticeClick = {},
            onBackClick = {}
        )
    }
}
