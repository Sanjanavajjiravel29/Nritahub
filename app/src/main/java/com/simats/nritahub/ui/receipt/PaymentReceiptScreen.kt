package com.simats.nritahub.ui.receipt

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
fun PaymentReceiptScreen(
    viewModel: PaymentReceiptViewModel = remember { PaymentReceiptViewModel() },
    onBackToPrevious: () -> Unit,
    onBackToFees: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            PaymentReceiptNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            PaymentReceiptNavigationEvent.NavigateToFees -> {
                onBackToFees()
                viewModel.onNavigationHandled()
            }
            PaymentReceiptNavigationEvent.DownloadReceipt -> {
                viewModel.onNavigationHandled()
            }
            PaymentReceiptNavigationEvent.ShareReceipt -> {
                viewModel.onNavigationHandled()
            }
            PaymentReceiptNavigationEvent.None -> {}
        }
    }

    PaymentReceiptContent(
        uiState = uiState,
        onDownloadReceiptClick = viewModel::onDownloadReceiptClicked,
        onShareReceiptClick = viewModel::onShareReceiptClicked,
        onBackToFeesClick = viewModel::onBackToFeesClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun PaymentReceiptContent(
    uiState: PaymentReceiptUiState,
    onDownloadReceiptClick: () -> Unit,
    onShareReceiptClick: () -> Unit,
    onBackToFeesClick: () -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · PAYMENT RECEIPT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        color = MediumMaroonText
                    )
                }

                // Share Trailing Icon
                IconButton(
                    onClick = onShareReceiptClick,
                    modifier = Modifier.size(28.dp)
                ) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 1.8.dp.toPx()
                        drawCircle(color = MediumMaroonText, radius = 2.5.dp.toPx(), center = Offset(w * 0.8f, h * 0.2f))
                        drawCircle(color = MediumMaroonText, radius = 2.5.dp.toPx(), center = Offset(w * 0.2f, h * 0.5f))
                        drawCircle(color = MediumMaroonText, radius = 2.5.dp.toPx(), center = Offset(w * 0.8f, h * 0.8f))
                        drawLine(color = MediumMaroonText, start = Offset(w * 0.2f, h * 0.5f), end = Offset(w * 0.8f, h * 0.2f), strokeWidth = stroke)
                        drawLine(color = MediumMaroonText, start = Offset(w * 0.2f, h * 0.5f), end = Offset(w * 0.8f, h * 0.8f), strokeWidth = stroke)
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

            Spacer(modifier = Modifier.height(16.dp))

            // Large Green Checkmark Circle
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8F5E9)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(28.dp)) {
                    val w = size.width
                    val h = size.height
                    drawPath(
                        path = Path().apply {
                            moveTo(0f, h * 0.55f)
                            lineTo(w * 0.38f, h * 0.9f)
                            lineTo(w, h * 0.15f)
                        },
                        color = Color(0xFF2E7D32),
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirmation Greeting
            Text(
                text = uiState.greetingTitle,
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = uiState.greetingSubtitle,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Receipt Details Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // PAID Badge
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "PAID",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp,
                            color = Color(0xFF2E7D32)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = uiState.amountText,
                        fontFamily = FontFamily.Serif,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = uiState.academyName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(color = CardBorder, thickness = 1.dp)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Detail Rows
                    ReceiptRow(label = "Invoice", value = uiState.invoiceNumber)
                    Spacer(modifier = Modifier.height(12.dp))
                    ReceiptRow(label = "Student", value = uiState.studentName)
                    Spacer(modifier = Modifier.height(12.dp))
                    ReceiptRow(label = "For", value = uiState.forDescription)
                    Spacer(modifier = Modifier.height(12.dp))
                    ReceiptRow(label = "Paid on", value = uiState.paidOnDate)
                    Spacer(modifier = Modifier.height(12.dp))
                    ReceiptRow(label = "Payment method", value = uiState.paymentMethod)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = uiState.transactionRef,
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        color = MutedBodyText
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action Button ("Download receipt")
            Button(
                onClick = onDownloadReceiptClick,
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
                        text = "Download receipt",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        drawLine(color = Color.White, start = Offset(w * 0.5f, 0f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.8.dp.toPx())
                        drawLine(color = Color.White, start = Offset(w * 0.25f, h * 0.45f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.8.dp.toPx())
                        drawLine(color = Color.White, start = Offset(w * 0.75f, h * 0.45f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.8.dp.toPx())
                        drawLine(color = Color.White, start = Offset(0f, h), end = Offset(w, h), strokeWidth = 1.8.dp.toPx())
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Secondary Action Button ("Share receipt")
            OutlinedButton(
                onClick = onShareReceiptClick,
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
                        text = "Share receipt",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPlum
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Canvas(modifier = Modifier.size(14.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 1.5.dp.toPx()
                        drawCircle(color = DeepPlum, radius = 2.dp.toPx(), center = Offset(w * 0.8f, h * 0.2f))
                        drawCircle(color = DeepPlum, radius = 2.dp.toPx(), center = Offset(w * 0.2f, h * 0.5f))
                        drawCircle(color = DeepPlum, radius = 2.dp.toPx(), center = Offset(w * 0.8f, h * 0.8f))
                        drawLine(color = DeepPlum, start = Offset(w * 0.2f, h * 0.5f), end = Offset(w * 0.8f, h * 0.2f), strokeWidth = stroke)
                        drawLine(color = DeepPlum, start = Offset(w * 0.2f, h * 0.5f), end = Offset(w * 0.8f, h * 0.8f), strokeWidth = stroke)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tertiary Action Button ("Back to fees")
            OutlinedButton(
                onClick = onBackToFeesClick,
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
                    text = "Back to fees",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepPlum
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer Balance Note
            Text(
                text = uiState.balanceStatusText,
                fontSize = 12.sp,
                color = MutedBodyText,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = DarkMaroonText
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = DeepPlum
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentReceiptScreenPreview() {
    NritahubTheme {
        PaymentReceiptContent(
            uiState = PaymentReceiptUiState(),
            onDownloadReceiptClick = {},
            onShareReceiptClick = {},
            onBackToFeesClick = {},
            onBackClick = {}
        )
    }
}
