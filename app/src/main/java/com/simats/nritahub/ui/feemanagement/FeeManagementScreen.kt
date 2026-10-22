package com.simats.nritahub.ui.feemanagement

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
fun FeeManagementScreen(
    viewModel: FeeManagementViewModel = remember { FeeManagementViewModel() },
    onNavigateToOverview: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            FeeManagementNavigationEvent.NavigateToOverview -> {
                onNavigateToOverview()
                viewModel.onNavigationHandled()
            }
            FeeManagementNavigationEvent.NavigateBack -> {
                onNavigateToOverview()
                viewModel.onNavigationHandled()
            }
            is FeeManagementNavigationEvent.SendReminderClicked -> {
                viewModel.onNavigationHandled()
            }
            FeeManagementNavigationEvent.RemindOverdueFamiliesClicked -> {
                viewModel.onNavigationHandled()
            }
            FeeManagementNavigationEvent.None -> {}
        }
    }

    FeeManagementContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChanged,
        onFilterSelect = viewModel::onFilterSelected,
        onSendReminderClick = viewModel::onSendReminderClicked,
        onRemindOverdueFamiliesClick = viewModel::onRemindOverdueFamiliesClicked,
        onTabSelect = viewModel::onTabSelected,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun FeeManagementContent(
    uiState: FeeManagementUiState,
    onSearchQueryChange: (String) -> Unit,
    onFilterSelect: (FeeFilter) -> Unit,
    onSendReminderClick: (AdminInvoiceItem) -> Unit,
    onRemindOverdueFamiliesClick: () -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · FEE MANAGEMENT",
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

            // Collection Banner Card
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "October collection",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )

                        Text(
                            text = uiState.paidCountText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DeepPlum
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = uiState.totalCollectedAmount,
                        fontFamily = FontFamily.Serif,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = uiState.totalBilledText,
                        fontSize = 12.sp,
                        color = MutedBodyText
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Status Breakdown Cards Row (2 Cards)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = uiState.pendingAmountText,
                            fontFamily = FontFamily.Serif,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = uiState.pendingCountText,
                            fontSize = 11.sp,
                            color = MutedBodyText
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = uiState.overdueAmountText,
                            fontFamily = FontFamily.Serif,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF944242)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = uiState.overdueCountText,
                            fontSize = 11.sp,
                            color = MutedBodyText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Filter Chips Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FeeFilter.entries.forEach { filter ->
                    val isSelected = uiState.selectedFilter == filter
                    val chipBg = if (isSelected) DeepPlum else Color(0xFFF3ECE5)
                    val chipTextColor = if (isSelected) Color.White else DarkMaroonText
                    val label = when (filter) {
                        FeeFilter.ALL -> "All · 78"
                        FeeFilter.PAID -> "Paid · 55"
                        FeeFilter.PENDING -> "Pending · 18"
                        FeeFilter.OVERDUE -> "Overdue · 5"
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(chipBg)
                            .clickable { onFilterSelect(filter) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = chipTextColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Input Box
            Text(
                text = "Find an invoice",
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (uiState.searchQuery.isEmpty()) {
                            Text(
                                text = "Search student or invoice number",
                                fontSize = 12.sp,
                                color = MutedBodyText
                            )
                        }
                        BasicTextField(
                            value = uiState.searchQuery,
                            onValueChange = onSearchQueryChange,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        drawCircle(color = DeepPlum, radius = w * 0.35f, center = Offset(w * 0.4f, h * 0.4f), style = Stroke(width = 1.5.dp.toPx()))
                        drawLine(color = DeepPlum, start = Offset(w * 0.65f, h * 0.65f), end = Offset(w, h), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // "Student invoices" Section Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Student invoices",
                    fontFamily = FontFamily.Serif,
                    fontSize = 22.sp,
                    color = DarkMaroonText
                )

                Box(
                    modifier = Modifier
                        .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "₹ INR",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Student Invoices List
            val filteredInvoices = if (uiState.searchQuery.isBlank()) {
                uiState.invoices
            } else {
                uiState.invoices.filter {
                    it.studentName.contains(uiState.searchQuery, ignoreCase = true) ||
                            it.invoiceNumber.contains(uiState.searchQuery, ignoreCase = true)
                }
            }

            filteredInvoices.forEach { invoice ->
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(SoftLavenderBadge),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = invoice.studentInitials,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepPlum
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = invoice.studentName,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                            }

                            val (statusBg, statusTextColor, statusLabel) = when (invoice.status) {
                                InvoiceStatus.PENDING -> Triple(Color(0xFFFDF3E5), Color(0xFFA05C1C), "Pending")
                                InvoiceStatus.PAID -> Triple(Color(0xFFE8F5E9), Color(0xFF2E7D32), "PAID")
                                InvoiceStatus.OVERDUE -> Triple(Color(0xFFFDE8E8), Color(0xFF944242), "Overdue")
                            }

                            Box(
                                modifier = Modifier
                                    .background(statusBg, shape = RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = statusLabel,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = statusTextColor
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = invoice.invoiceNumber,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = invoice.batchAndDueInfo,
                                    fontSize = 12.sp,
                                    color = MutedBodyText
                                )
                            }

                            Text(
                                text = invoice.amountText,
                                fontFamily = FontFamily.Serif,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )
                        }

                        if (invoice.status != InvoiceStatus.PAID) {
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { onSendReminderClick(invoice) }
                            ) {
                                Text(
                                    text = "Send fee reminder",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepPlum
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Canvas(modifier = Modifier.size(10.dp)) {
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

                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(4.dp))

            // View all invoices link
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = uiState.viewAllInvoicesText,
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

            Spacer(modifier = Modifier.height(16.dp))

            // Remind overdue families button
            OutlinedButton(
                onClick = onRemindOverdueFamiliesClick,
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
                        text = uiState.remindOverdueButtonText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPlum
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Canvas(modifier = Modifier.size(14.dp)) {
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
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Privacy Notice Box
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
                        text = uiState.privacyNotice,
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
fun FeeManagementScreenPreview() {
    NritahubTheme {
        FeeManagementContent(
            uiState = FeeManagementUiState(),
            onSearchQueryChange = {},
            onFilterSelect = {},
            onSendReminderClick = {},
            onRemindOverdueFamiliesClick = {},
            onTabSelect = {},
            onBackClick = {}
        )
    }
}
