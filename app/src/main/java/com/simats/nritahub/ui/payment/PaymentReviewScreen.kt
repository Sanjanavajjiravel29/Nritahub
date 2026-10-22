package com.simats.nritahub.ui.payment

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
fun PaymentReviewScreen(
    viewModel: PaymentReviewViewModel = remember { PaymentReviewViewModel() },
    onBackToPrevious: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            PaymentReviewNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            PaymentReviewNavigationEvent.PaymentConfirmed -> {
                onPaymentSuccess()
                viewModel.onNavigationHandled()
            }
            PaymentReviewNavigationEvent.None -> {}
        }
    }

    PaymentReviewContent(
        uiState = uiState,
        onPaymentMethodSelect = viewModel::onPaymentMethodSelected,
        onContinuePaymentClick = viewModel::onContinuePaymentClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun PaymentReviewContent(
    uiState: PaymentReviewUiState,
    onPaymentMethodSelect: (PaymentMethod) -> Unit,
    onContinuePaymentClick: () -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · PAYMENT REVIEW",
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

            Spacer(modifier = Modifier.height(12.dp))

            // Invoice Badge Pill
            Box(
                modifier = Modifier
                    .background(SoftLavenderBadge, shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = uiState.invoiceBadge,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = DeepPlum
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle
            Text(
                text = uiState.academyAndStudent,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Payment Summary Breakdown Card
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
                    // Item Row 1
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = uiState.itemTitle,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.itemSubtitle,
                                fontSize = 12.sp,
                                color = MutedBodyText
                            )
                        }

                        Text(
                            text = uiState.itemAmount,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Item Row 2 (Additional Charges)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Additional charges",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )

                        Text(
                            text = uiState.additionalChargesText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    HorizontalDivider(color = CardBorder, thickness = 1.dp)

                    Spacer(modifier = Modifier.height(14.dp))

                    // Item Row 3 (Total Payable)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total payable",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )

                        Text(
                            text = uiState.totalPayableText,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = uiState.dueDateAndCurrencyText,
                        fontSize = 12.sp,
                        color = MutedBodyText
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Choose a payment method" Section
            Text(
                text = "Choose a payment method",
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Payment Methods List
            PaymentMethod.entries.forEach { method ->
                val isSelected = uiState.selectedMethod == method
                val containerBg = if (isSelected) SoftLavenderBadge else CardBackground
                val border = if (isSelected) null else BorderStroke(1.dp, CardBorder)

                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = containerBg),
                    border = border,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPaymentMethodSelect(method) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            // Method Icon
                            Canvas(modifier = Modifier.size(20.dp)) {
                                val w = size.width
                                val h = size.height
                                when (method) {
                                    PaymentMethod.UPI -> {
                                        // Smartphone / UPI icon
                                        drawPath(
                                            path = Path().apply {
                                                addRoundRect(
                                                    RoundRect(
                                                        rect = Rect(w * 0.2f, 0f, w * 0.8f, h),
                                                        cornerRadius = CornerRadius(4f, 4f)
                                                    )
                                                )
                                            },
                                            color = DeepPlum,
                                            style = Stroke(width = 1.5.dp.toPx())
                                        )
                                    }
                                    PaymentMethod.CARD -> {
                                        // Credit Card icon
                                        drawPath(
                                            path = Path().apply {
                                                addRoundRect(
                                                    androidx.compose.ui.geometry.RoundRect(
                                                        rect = androidx.compose.ui.geometry.Rect(0f, h * 0.2f, w, h * 0.8f),
                                                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                                                    )
                                                )
                                            },
                                            color = DeepPlum,
                                            style = Stroke(width = 1.5.dp.toPx())
                                        )
                                        drawLine(color = DeepPlum, start = Offset(0f, h * 0.4f), end = Offset(w, h * 0.4f), strokeWidth = 2.dp.toPx())
                                    }
                                    PaymentMethod.NET_BANKING -> {
                                        // Bank Icon
                                        drawPath(
                                            path = Path().apply {
                                                moveTo(0f, h * 0.3f)
                                                lineTo(w * 0.5f, 0f)
                                                lineTo(w, h * 0.3f)
                                                close()
                                            },
                                            color = DeepPlum
                                        )
                                        drawLine(color = DeepPlum, start = Offset(0f, h), end = Offset(w, h), strokeWidth = 2.dp.toPx())
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = method.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                if (isSelected && method.subtitle.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = method.subtitle,
                                        fontSize = 12.sp,
                                        color = MutedBodyText
                                    )
                                }
                            }
                        }

                        // Right Control
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(DeepPlum),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.size(10.dp)) {
                                    val w = size.width
                                    val h = size.height
                                    drawPath(
                                        path = Path().apply {
                                            moveTo(0f, h * 0.5f)
                                            lineTo(w * 0.35f, h * 0.85f)
                                            lineTo(w, h * 0.2f)
                                        },
                                        color = Color.White,
                                        style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
                                    )
                                }
                            }
                        } else {
                            Canvas(modifier = Modifier.size(18.dp)) {
                                drawCircle(
                                    color = CardBorder,
                                    radius = size.width / 2,
                                    style = Stroke(width = 1.5.dp.toPx())
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Payment Security Notice Box
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
                        // Lock Icon
                        Canvas(modifier = Modifier.size(14.dp)) {
                            val w = size.width
                            val h = size.height
                            drawPath(
                                path = Path().apply {
                                    moveTo(w * 0.3f, h * 0.5f)
                                    lineTo(w * 0.3f, h * 0.25f)
                                    cubicTo(w * 0.3f, 0f, w * 0.7f, 0f, w * 0.7f, h * 0.25f)
                                    lineTo(w * 0.7f, h * 0.5f)
                                },
                                color = DeepPlum,
                                style = Stroke(width = 1.5.dp.toPx())
                            )
                            drawPath(
                                path = Path().apply {
                                    moveTo(w * 0.15f, h * 0.45f)
                                    lineTo(w * 0.85f, h * 0.45f)
                                    lineTo(w * 0.85f, h)
                                    lineTo(w * 0.15f, h)
                                    close()
                                },
                                color = DeepPlum
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = uiState.securityNotice,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action Button ("Continue to pay ₹2,400")
            Button(
                onClick = onContinuePaymentClick,
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
                        text = uiState.payButtonText,
                        fontSize = 16.sp,
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

            // Footer Text
            Text(
                text = uiState.footerNote,
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
fun PaymentReviewScreenPreview() {
    NritahubTheme {
        PaymentReviewContent(
            uiState = PaymentReviewUiState(),
            onPaymentMethodSelect = {},
            onContinuePaymentClick = {},
            onBackClick = {}
        )
    }
}
