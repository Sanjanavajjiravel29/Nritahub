package com.simats.nritahub.ui.eventdetail

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
fun EventDetailScreen(
    viewModel: EventDetailViewModel = remember { EventDetailViewModel() },
    onBackToPrevious: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            EventDetailNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            EventDetailNavigationEvent.RegisterInterest -> {
                viewModel.onNavigationHandled()
            }
            EventDetailNavigationEvent.None -> {}
        }
    }

    EventDetailContent(
        uiState = uiState,
        onToggleItem = viewModel::toggleCheckItem,
        onRegisterInterestClick = viewModel::onRegisterInterestClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun EventDetailContent(
    uiState: EventDetailUiState,
    onToggleItem: (Int) -> Unit,
    onRegisterInterestClick: () -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · EVENT DETAIL",
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

            // Event Image Banner Card
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.splash),
                    contentDescription = "Event Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Badges Row
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
                        text = uiState.performanceBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = DeepPlum
                    )
                }

                Box(
                    modifier = Modifier
                        .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = uiState.statusBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Event Description Subtitle
            Text(
                text = uiState.description,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Schedule, Venue & Organizer Card
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
                                text = uiState.venueName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.venueAddress,
                                fontSize = 13.sp,
                                color = MutedBodyText
                            )
                        }

                        // Pin Icon
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

                    // Organizer Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = uiState.organizerText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.coordinatorText,
                                fontSize = 13.sp,
                                color = MutedBodyText
                            )
                        }

                        // Person Icon
                        Canvas(modifier = Modifier.size(18.dp)) {
                            val w = size.width
                            val h = size.height
                            drawCircle(color = DeepPlum, radius = w * 0.25f, center = Offset(w * 0.5f, h * 0.3f), style = Stroke(width = 1.5.dp.toPx()))
                            drawPath(
                                path = Path().apply {
                                    arcTo(
                                        rect = androidx.compose.ui.geometry.Rect(0f, h * 0.45f, w, h * 1.15f),
                                        startAngleDegrees = 180f,
                                        sweepAngleDegrees = 180f,
                                        forceMoveTo = false
                                    )
                                },
                                color = DeepPlum,
                                style = Stroke(width = 1.5.dp.toPx())
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Who can participate?" Section
            Text(
                text = uiState.whoCanParticipateTitle,
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = uiState.whoCanParticipateText,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // "Prepare with confidence" Checklist Card
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
                        text = "Prepare with confidence",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(12.dp))

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
                                if (item.isChecked) {
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
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Registration Notice Box
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
                            val r = size.width / 2
                            drawCircle(color = DeepPlum, radius = r, style = Stroke(width = 1.5.dp.toPx()))
                            drawLine(color = DeepPlum, start = Offset(r, r * 0.5f), end = Offset(r, r * 0.8f), strokeWidth = 2f)
                            drawCircle(color = DeepPlum, radius = 1.5f, center = Offset(r, r * 1.35f))
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = if (uiState.isRegistered) "Registered successfully! Your instructor will contact you." else uiState.registrationNotice,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action Button ("Register my interest")
            Button(
                onClick = onRegisterInterestClick,
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
                        text = if (uiState.isRegistered) "Registered ✓" else "Register my interest",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Ticket / Envelope Icon
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        drawPath(
                            path = Path().apply {
                                addRoundRect(
                                    androidx.compose.ui.geometry.RoundRect(
                                        rect = androidx.compose.ui.geometry.Rect(0f, h * 0.15f, w, h * 0.85f),
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

            Spacer(modifier = Modifier.height(16.dp))

            // Footer Note
            Text(
                text = uiState.footerText,
                fontSize = 12.sp,
                color = MutedBodyText,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailScreenPreview() {
    NritahubTheme {
        EventDetailContent(
            uiState = EventDetailUiState(),
            onToggleItem = {},
            onRegisterInterestClick = {},
            onBackClick = {}
        )
    }
}
