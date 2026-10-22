package com.simats.nritahub.ui.practicelesson

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
fun PracticeLessonScreen(
    viewModel: PracticeLessonViewModel = remember { PracticeLessonViewModel() },
    onBackToPrevious: () -> Unit,
    onOpenAssignmentSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            PracticeLessonNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            PracticeLessonNavigationEvent.DownloadOffline -> {
                viewModel.onNavigationHandled()
            }
            PracticeLessonNavigationEvent.OpenAssignment -> {
                onOpenAssignmentSuccess()
                viewModel.onNavigationHandled()
            }
            PracticeLessonNavigationEvent.None -> {}
        }
    }

    PracticeLessonContent(
        uiState = uiState,
        onTogglePlay = viewModel::togglePlayPause,
        onDownloadClick = viewModel::onDownloadClicked,
        onOpenAssignmentClick = viewModel::onOpenAssignmentClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun PracticeLessonContent(
    uiState: PracticeLessonUiState,
    onTogglePlay: () -> Unit,
    onDownloadClick: () -> Unit,
    onOpenAssignmentClick: () -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · PRACTICE LESSON",
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

            // Video Player Container Box
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clickable { onTogglePlay() }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.splash),
                        contentDescription = "Lesson Video Player",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Central Play Overlay Circle
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .background(DeepPlum.copy(alpha = 0.85f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(20.dp)) {
                            val w = size.width
                            val h = size.height
                            if (uiState.isPlaying) {
                                drawLine(color = Color.White, start = Offset(w * 0.35f, 0f), end = Offset(w * 0.35f, h), strokeWidth = 3.dp.toPx())
                                drawLine(color = Color.White, start = Offset(w * 0.65f, 0f), end = Offset(w * 0.65f, h), strokeWidth = 3.dp.toPx())
                            } else {
                                drawPath(
                                    path = Path().apply {
                                        moveTo(w * 0.25f, 0f)
                                        lineTo(w * 0.85f, h * 0.5f)
                                        lineTo(w * 0.25f, h)
                                        close()
                                    },
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Video Player Custom Controls Bar
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Skip Back / Rewind Icon
                        Canvas(modifier = Modifier.size(16.dp).clickable { onTogglePlay() }) {
                            val w = size.width
                            val h = size.height
                            drawLine(color = DeepPlum, start = Offset(0f, 0f), end = Offset(0f, h), strokeWidth = 2.dp.toPx())
                            drawPath(
                                path = Path().apply {
                                    moveTo(w, 0f)
                                    lineTo(w * 0.3f, h * 0.5f)
                                    lineTo(w, h)
                                    close()
                                },
                                color = DeepPlum
                            )
                        }

                        // Play/Pause Icon
                        Canvas(modifier = Modifier.size(16.dp).clickable { onTogglePlay() }) {
                            val w = size.width
                            val h = size.height
                            drawLine(color = DeepPlum, start = Offset(w * 0.25f, 0f), end = Offset(w * 0.25f, h), strokeWidth = 2.5.dp.toPx())
                            drawLine(color = DeepPlum, start = Offset(w * 0.75f, 0f), end = Offset(w * 0.75f, h), strokeWidth = 2.5.dp.toPx())
                        }

                        Text(
                            text = "${uiState.currentTimeText} / ${uiState.totalTimeText}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Volume Icon
                        Canvas(modifier = Modifier.size(16.dp)) {
                            val w = size.width
                            val h = size.height
                            drawPath(
                                path = Path().apply {
                                    moveTo(0f, h * 0.3f)
                                    lineTo(w * 0.35f, h * 0.3f)
                                    lineTo(w * 0.7f, 0f)
                                    lineTo(w * 0.7f, h)
                                    lineTo(w * 0.35f, h * 0.7f)
                                    lineTo(0f, h * 0.7f)
                                    close()
                                },
                                color = DeepPlum
                            )
                        }

                        // Fullscreen Icon
                        Canvas(modifier = Modifier.size(16.dp)) {
                            val w = size.width
                            val h = size.height
                            val stroke = 1.8.dp.toPx()
                            // Top left corner
                            drawLine(color = DeepPlum, start = Offset(0f, 0f), end = Offset(w * 0.35f, 0f), strokeWidth = stroke)
                            drawLine(color = DeepPlum, start = Offset(0f, 0f), end = Offset(0f, h * 0.35f), strokeWidth = stroke)
                            // Bottom right corner
                            drawLine(color = DeepPlum, start = Offset(w, h), end = Offset(w * 0.65f, h), strokeWidth = stroke)
                            drawLine(color = DeepPlum, start = Offset(w, h), end = Offset(w, h * 0.65f), strokeWidth = stroke)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Progress Bar Track
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(CardBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(uiState.progressFraction)
                            .height(4.dp)
                            .background(DeepPlum)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tag Badges Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = uiState.styleBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = DeepPlum
                    )
                }

                Box(
                    modifier = Modifier
                        .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = uiState.levelBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Instructor & Lesson Row
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
                    text = uiState.instructorAndLesson,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkMaroonText
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Lesson Quote Section
            Text(
                text = uiState.quoteTitle,
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = uiState.quoteSubtitle,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Chapters List Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    uiState.chapters.forEachIndexed { index, chapter ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = chapter.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = chapter.timestampAndDesc,
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

                        if (index < uiState.chapters.size - 1) {
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

            // Secondary Button ("Download for offline practice - 28 MB")
            OutlinedButton(
                onClick = onDownloadClick,
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
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (uiState.isDownloaded) "Downloaded for offline practice" else uiState.downloadButtonText,
                        fontSize = 13.sp,
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

            Spacer(modifier = Modifier.height(12.dp))

            // Primary Action Button ("Open your practice assignment")
            Button(
                onClick = onOpenAssignmentClick,
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
                        text = "Open your practice assignment",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 2.dp.toPx()
                        drawLine(color = Color.White, start = Offset(0f, h / 2), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                        drawLine(color = Color.White, start = Offset(w * 0.6f, h * 0.2f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                        drawLine(color = Color.White, start = Offset(w * 0.6f, h * 0.8f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PracticeLessonScreenPreview() {
    NritahubTheme {
        PracticeLessonContent(
            uiState = PracticeLessonUiState(),
            onTogglePlay = {},
            onDownloadClick = {},
            onOpenAssignmentClick = {},
            onBackClick = {}
        )
    }
}
