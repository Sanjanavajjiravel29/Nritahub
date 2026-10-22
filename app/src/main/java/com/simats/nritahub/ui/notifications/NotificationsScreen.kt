package com.simats.nritahub.ui.notifications

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
fun NotificationsScreen(
    viewModel: NotificationsViewModel = remember { NotificationsViewModel() },
    onBackToPrevious: () -> Unit,
    onNavigateToDetail: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            NotificationsNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            is NotificationsNavigationEvent.NotificationClicked -> {
                onNavigateToDetail()
                viewModel.onNavigationHandled()
            }
            NotificationsNavigationEvent.None -> {}
        }
    }

    NotificationsContent(
        uiState = uiState,
        onCategorySelect = viewModel::onCategorySelected,
        onMarkAllAsReadClick = viewModel::markAllAsRead,
        onNotificationClick = viewModel::onNotificationClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun NotificationsContent(
    uiState: NotificationsUiState,
    onCategorySelect: (NotificationCategory) -> Unit,
    onMarkAllAsReadClick: () -> Unit,
    onNotificationClick: (NotificationItem) -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · NOTIFICATIONS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        color = MediumMaroonText
                    )
                }

                // Checkmarks Mark All Read Icon
                IconButton(
                    onClick = onMarkAllAsReadClick,
                    modifier = Modifier.size(28.dp)
                ) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 1.8.dp.toPx()
                        // Double checkmark
                        drawPath(
                            path = Path().apply {
                                moveTo(0f, h * 0.5f)
                                lineTo(w * 0.35f, h * 0.85f)
                                lineTo(w * 0.85f, h * 0.15f)
                            },
                            color = DeepPlum,
                            style = Stroke(width = stroke, cap = StrokeCap.Round)
                        )
                    }
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

            // Sub-header Row (Unread Count & Mark All Read Link)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${uiState.unreadCount} unread",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPlum
                    )
                }

                Text(
                    text = "Mark all as read",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepPlum,
                    modifier = Modifier.clickable { onMarkAllAsReadClick() }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Category Filter Chips Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NotificationCategory.entries.forEach { category ->
                    val isSelected = uiState.selectedCategory == category
                    val chipBg = if (isSelected) DeepPlum else Color(0xFFF3ECE5)
                    val chipTextColor = if (isSelected) Color.White else DarkMaroonText
                    val label = when (category) {
                        NotificationCategory.ALL -> "All"
                        NotificationCategory.CLASSES -> "Classes"
                        NotificationCategory.PRACTICE -> "Practice"
                        NotificationCategory.FEES -> "Fees"
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(chipBg)
                            .clickable { onCategorySelect(category) }
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

            // Notifications List
            val filteredList = if (uiState.selectedCategory == NotificationCategory.ALL) {
                uiState.notifications
            } else {
                uiState.notifications.filter { it.category == uiState.selectedCategory }
            }

            filteredList.forEach { item ->
                val cardBg = if (item.isUnread) SoftLavenderBadge else CardBackground
                val border = if (item.isUnread) null else BorderStroke(1.dp, CardBorder)

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    border = border,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNotificationClick(item) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Canvas(modifier = Modifier.size(16.dp)) {
                                    val w = size.width
                                    val h = size.height
                                    val stroke = 1.5.dp.toPx()
                                    when (item.categoryBadge) {
                                        "CLASS" -> {
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
                                                style = Stroke(width = stroke)
                                            )
                                        }
                                        "PRACTICE" -> {
                                            drawCircle(color = DeepPlum, radius = size.width / 2, style = Stroke(width = stroke))
                                            drawPath(
                                                path = Path().apply {
                                                    moveTo(w * 0.4f, h * 0.3f)
                                                    lineTo(w * 0.7f, h * 0.5f)
                                                    lineTo(w * 0.4f, h * 0.7f)
                                                    close()
                                                },
                                                color = DeepPlum
                                            )
                                        }
                                        "FEES" -> {
                                            drawPath(
                                                path = Path().apply {
                                                    addRoundRect(
                                                        RoundRect(
                                                            rect = Rect(0f, h * 0.15f, w, h * 0.85f),
                                                            cornerRadius = CornerRadius(4f, 4f)
                                                        )
                                                    )
                                                },
                                                color = DeepPlum,
                                                style = Stroke(width = stroke)
                                            )
                                        }
                                        else -> {
                                            drawCircle(color = DeepPlum, radius = size.width / 2, style = Stroke(width = stroke))
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = item.categoryBadge,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp,
                                    color = DeepPlum
                                )
                            }

                            if (item.isUnread) {
                                Canvas(modifier = Modifier.size(8.dp)) {
                                    drawCircle(color = Color(0xFF880E4F), radius = size.width / 2)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = item.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.body,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            color = MutedBodyText
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = item.timestampText,
                            fontSize = 11.sp,
                            color = MutedBodyText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    NritahubTheme {
        NotificationsContent(
            uiState = NotificationsUiState(),
            onCategorySelect = {},
            onMarkAllAsReadClick = {},
            onNotificationClick = {},
            onBackClick = {}
        )
    }
}
