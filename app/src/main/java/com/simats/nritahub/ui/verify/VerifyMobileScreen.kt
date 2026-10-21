package com.simats.nritahub.ui.verify

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import com.simats.nritahub.ui.theme.MutedFooterText
import com.simats.nritahub.ui.theme.NritahubTheme
import com.simats.nritahub.ui.theme.SoftLavenderBadge

@Composable
fun VerifyMobileScreen(
    viewModel: VerifyMobileViewModel = remember { VerifyMobileViewModel() },
    onBackToPrevious: () -> Unit,
    onChangeMobileClicked: () -> Unit,
    onVerifySuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            VerifyMobileNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            VerifyMobileNavigationEvent.NavigateToChangeMobile -> {
                onChangeMobileClicked()
                viewModel.onNavigationHandled()
            }
            VerifyMobileNavigationEvent.NavigateToHome -> {
                onVerifySuccess()
                viewModel.onNavigationHandled()
            }
            VerifyMobileNavigationEvent.None -> {}
        }
    }

    VerifyMobileContent(
        uiState = uiState,
        onOtpDigitChange = viewModel::onOtpDigitEntered,
        onChangeMobileClick = viewModel::onChangeMobileClicked,
        onVerifyClick = viewModel::onVerifyClicked,
        onResendClick = viewModel::onResendClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun VerifyMobileContent(
    uiState: VerifyMobileUiState,
    onOtpDigitChange: (Int, String) -> Unit,
    onChangeMobileClick: () -> Unit,
    onVerifyClick: () -> Unit,
    onResendClick: () -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · VERIFY MOBILE",
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

            // Title with Red Accent Dot above/left
            Column {
                Canvas(modifier = Modifier.size(8.dp)) {
                    drawCircle(color = Color(0xFFD32F2F), radius = size.width / 2)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = uiState.title,
                    fontFamily = FontFamily.Serif,
                    fontSize = 32.sp,
                    color = DarkMaroonText
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Inspirational Banner Card
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
                    // Chat/Message Bubble Icon
                    Canvas(modifier = Modifier.size(24.dp)) {
                        val w = size.width
                        val h = size.height
                        drawPath(
                            path = Path().apply {
                                addRoundRect(
                                    RoundRect(
                                        rect = Rect(0f, 0f, w, h * 0.75f),
                                        cornerRadius = CornerRadius(12f, 12f)
                                    )
                                )
                                moveTo(w * 0.2f, h * 0.75f)
                                lineTo(w * 0.1f, h)
                                lineTo(w * 0.4f, h * 0.75f)
                            },
                            color = DeepPlum,
                            style = Stroke(width = 1.8.dp.toPx())
                        )
                        // Message lines inside
                        drawLine(color = DeepPlum, start = Offset(w * 0.25f, h * 0.28f), end = Offset(w * 0.75f, h * 0.28f), strokeWidth = 1.5.dp.toPx())
                        drawLine(color = DeepPlum, start = Offset(w * 0.25f, h * 0.48f), end = Offset(w * 0.55f, h * 0.48f), strokeWidth = 1.5.dp.toPx())
                    }

                    Spacer(modifier = Modifier.height(12.dp))

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
                        color = MutedBodyText
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Mobile Number Display
            Text(
                text = uiState.mobileNumber,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Change Mobile Number Action Link
            Text(
                text = "Change mobile number",
                fontSize = 13.sp,
                color = DeepPlum,
                modifier = Modifier.clickable { onChangeMobileClick() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 6-Digit OTP Boxes Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0 until 6) {
                    val digit = uiState.otpDigits.getOrElse(i) { "" }
                    val isFocused = i == uiState.focusedDigitIndex
                    val borderColor = if (isFocused) DeepPlum else CardBorder
                    val borderWidth = if (isFocused) 2.dp else 1.dp

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        border = BorderStroke(borderWidth, borderColor),
                        modifier = Modifier
                            .size(46.dp, 52.dp)
                            .clickable { onOtpDigitChange(i, digit) }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                value = digit,
                                onValueChange = { newValue ->
                                    val filtered = newValue.takeLast(1)
                                    onOtpDigitChange(i, filtered)
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                textStyle = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Timer Expiry Notice
            Text(
                text = "Code expires in ${uiState.expiryTimerText}",
                fontSize = 13.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action Button ("Verify & continue")
            Button(
                onClick = onVerifyClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepPlum,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Verify & continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resend Code Section
            Text(
                text = "Didn't receive it? Resend code in ${uiState.resendTimerText}",
                fontSize = 13.sp,
                color = MutedBodyText,
                modifier = Modifier.clickable { if (uiState.canResend) onResendClick() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Security Notice Box
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
                        text = "Never share this code. Academy staff will not ask you for your verification code.",
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Footer Help Text
            Text(
                text = "Check that your phone has network coverage. If needed, request a new code once the timer ends.",
                fontSize = 12.sp,
                color = MutedFooterText,
                lineHeight = 17.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyMobileScreenPreview() {
    NritahubTheme {
        VerifyMobileContent(
            uiState = VerifyMobileUiState(),
            onOtpDigitChange = { _, _ -> },
            onChangeMobileClick = {},
            onVerifyClick = {},
            onResendClick = {},
            onBackClick = {}
        )
    }
}
