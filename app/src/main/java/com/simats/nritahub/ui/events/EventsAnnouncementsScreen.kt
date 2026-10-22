package com.simats.nritahub.ui.events

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
fun EventsAnnouncementsScreen(
    viewModel: EventsAnnouncementsViewModel = remember { EventsAnnouncementsViewModel() },
    onBackToPrevious: () -> Unit,
    onNavigateToEventDetail: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            EventsAnnouncementsNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            EventsAnnouncementsNavigationEvent.RegisterForRecital -> {
                onNavigateToEventDetail()
                viewModel.onNavigationHandled()
            }
            EventsAnnouncementsNavigationEvent.OpenEventDetail -> {
                onNavigateToEventDetail()
                viewModel.onNavigationHandled()
            }
            EventsAnnouncementsNavigationEvent.None -> {}
        }
    }

    EventsAnnouncementsContent(
        uiState = uiState,
        onFilterSelect = viewModel::onFilterSelected,
        onRegisterRecitalClick = viewModel::onRegisterRecitalClicked,
        onEventClick = viewModel::onEventClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun EventsAnnouncementsContent(
    uiState: EventsAnnouncementsUiState,
    onFilterSelect: (EventFilter) -> Unit,
    onRegisterRecitalClick: () -> Unit,
    onEventClick: () -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · EVENTS & ANNOUNCEMENTS",
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

            // Category Filter Chips Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EventFilter.entries.forEach { filter ->
                    val isSelected = uiState.selectedFilter == filter
                    val chipBg = if (isSelected) DeepPlum else Color(0xFFF3ECE5)
                    val chipTextColor = if (isSelected) Color.White else DarkMaroonText
                    val label = when (filter) {
                        EventFilter.ALL -> "All"
                        EventFilter.EVENTS -> "Events"
                        EventFilter.WORKSHOPS -> "Workshops"
                        EventFilter.UPDATES -> "Updates"
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

            // Featured Event Card ("Autumn recital")
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRegisterRecitalClick() }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Event Image Thumbnail
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.splash),
                            contentDescription = "Autumn Recital",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = uiState.featuredBadge,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp,
                                    color = DeepPlum
                                )
                            }

                            Text(
                                text = uiState.featuredDate,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = uiState.featuredTitle,
                            fontFamily = FontFamily.Serif,
                            fontSize = 24.sp,
                            color = DarkMaroonText
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = uiState.featuredSubtitle,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            color = MutedBodyText
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { onRegisterRecitalClick() }
                        ) {
                            Text(
                                text = "See details & register",
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
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Coming together" Section
            Text(
                text = uiState.comingTogetherTitle,
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Upcoming Events List Cards
            uiState.upcomingEvents.forEach { event ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEventClick() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            val badgeBg = if (event.isExamination) Color(0xFFFDF3E5) else SoftLavenderBadge
                            val badgeColor = if (event.isExamination) Color(0xFFA05C1C) else DeepPlum

                            Box(
                                modifier = Modifier
                                    .background(badgeBg, shape = RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = event.badge,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp,
                                    color = badgeColor
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = event.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = event.subtitle,
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
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Academy Update Notice Box
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
                        // Megaphone / Announcement Icon
                        Canvas(modifier = Modifier.size(14.dp)) {
                            val w = size.width
                            val h = size.height
                            drawPath(
                                path = Path().apply {
                                    moveTo(0f, h * 0.35f)
                                    lineTo(w * 0.4f, h * 0.35f)
                                    lineTo(w * 0.85f, 0f)
                                    lineTo(w * 0.85f, h)
                                    lineTo(w * 0.4f, h * 0.65f)
                                    lineTo(0f, h * 0.65f)
                                    close()
                                },
                                color = DeepPlum
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = uiState.academyUpdateNotice,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timezone Footer Note
            Text(
                text = uiState.timeZoneFooter,
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
fun EventsAnnouncementsScreenPreview() {
    NritahubTheme {
        EventsAnnouncementsContent(
            uiState = EventsAnnouncementsUiState(),
            onFilterSelect = {},
            onRegisterRecitalClick = {},
            onEventClick = {},
            onBackClick = {}
        )
    }
}
