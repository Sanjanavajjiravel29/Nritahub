package com.simats.nritahub.ui.auth

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = remember { SignInViewModel() },
    onBackToPrevious: () -> Unit,
    onSignInSuccess: () -> Unit,
    onCreateAccountClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            SignInNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            SignInNavigationEvent.NavigateToHome -> {
                onSignInSuccess()
                viewModel.onNavigationHandled()
            }
            SignInNavigationEvent.NavigateToCreateAccount -> {
                onCreateAccountClicked()
                viewModel.onNavigationHandled()
            }
            SignInNavigationEvent.ForgotPasswordClicked -> {
                viewModel.onNavigationHandled()
            }
            SignInNavigationEvent.None -> {}
        }
    }

    SignInContent(
        uiState = uiState,
        onMobileNumberChange = viewModel::onMobileNumberChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
        onSignInClick = viewModel::onSignInClicked,
        onCreateAccountClick = viewModel::onCreateAccountClicked,
        onForgotPasswordClick = viewModel::onForgotPasswordClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun SignInContent(
    uiState: SignInUiState,
    onMobileNumberChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · SIGN IN",
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
                    // Decorative Flower Icon
                    Canvas(modifier = Modifier.size(24.dp)) {
                        val center = Offset(size.width / 2, size.height * 0.4f)
                        val r = size.width / 5
                        drawCircle(color = DeepPlum, radius = 3f, center = center)
                        for (i in 0 until 5) {
                            val angle = Math.toRadians((i * 72).toDouble())
                            val x = center.x + (r * cos(angle)).toFloat()
                            val y = center.y + (r * sin(angle)).toFloat()
                            drawCircle(
                                color = DeepPlum,
                                radius = 3f,
                                center = Offset(x, y),
                                style = Stroke(width = 1.5.dp.toPx())
                            )
                        }
                        // Stem
                        drawLine(
                            color = DeepPlum,
                            start = Offset(center.x, center.y + r),
                            end = Offset(center.x, size.height),
                            strokeWidth = 1.8.dp.toPx()
                        )
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

            // Academy & Role Context Label
            Text(
                text = uiState.academyAndRole,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DeepPlum
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mobile Number Input Field
            Text(
                text = "Mobile number",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = uiState.mobileNumber,
                onValueChange = onMobileNumberChange,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedBorderColor = DeepPlum,
                    unfocusedBorderColor = CardBorder
                ),
                trailingIcon = {
                    // Phone Icon
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        drawPath(
                            path = Path().apply {
                                moveTo(w * 0.2f, h * 0.1f)
                                lineTo(w * 0.4f, h * 0.1f)
                                lineTo(w * 0.5f, h * 0.35f)
                                lineTo(w * 0.35f, h * 0.5f)
                                cubicTo(w * 0.45f, h * 0.7f, w * 0.6f, h * 0.85f, w * 0.8f, h * 0.85f)
                                lineTo(w * 0.9f, h * 0.7f)
                                lineTo(w, h * 0.8f)
                                lineTo(w * 0.85f, h)
                                cubicTo(w * 0.4f, h, 0f, h * 0.6f, 0f, h * 0.15f)
                                close()
                            },
                            color = DeepPlum,
                            style = Stroke(width = 1.5.dp.toPx())
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Password Input Field
            Text(
                text = "Password",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                singleLine = true,
                visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedBorderColor = DeepPlum,
                    unfocusedBorderColor = CardBorder
                ),
                trailingIcon = {
                    // Eye Visibility Toggle Icon
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onTogglePasswordVisibility() },
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(18.dp)) {
                            val w = size.width
                            val h = size.height
                            drawPath(
                                path = Path().apply {
                                    moveTo(0f, h * 0.5f)
                                    cubicTo(w * 0.25f, h * 0.1f, w * 0.75f, h * 0.1f, w, h * 0.5f)
                                    cubicTo(w * 0.75f, h * 0.9f, w * 0.25f, h * 0.9f, 0f, h * 0.5f)
                                },
                                color = DeepPlum,
                                style = Stroke(width = 1.5.dp.toPx())
                            )
                            drawCircle(color = DeepPlum, radius = 3.dp.toPx(), center = Offset(w * 0.5f, h * 0.5f))
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Forgot Password Link
            Text(
                text = "Forgot password?",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DeepPlum,
                modifier = Modifier.clickable { onForgotPasswordClick() }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action Button ("Sign in")
            Button(
                onClick = onSignInClick,
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
                        text = "Sign in",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Forward Arrow Canvas
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 2.dp.toPx()
                        drawLine(
                            color = Color.White,
                            start = Offset(0f, h / 2),
                            end = Offset(w, h / 2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color.White,
                            start = Offset(w * 0.6f, h * 0.2f),
                            end = Offset(w, h / 2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color.White,
                            start = Offset(w * 0.6f, h * 0.8f),
                            end = Offset(w, h / 2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Security / Privacy Notice Box
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
                            // Shackle
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
                            // Body
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
                        text = "Your account keeps your learning and academy information in one private place.",
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "New to NrityaHub?" Label
            Text(
                text = "New to NrityaHub?",
                fontSize = 13.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Secondary Action Button ("Create an account")
            OutlinedButton(
                onClick = onCreateAccountClick,
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
                    text = "Create an account",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepPlum
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Footer Text
            Text(
                text = "Need access? Contact your academy office.",
                fontSize = 12.sp,
                color = MutedFooterText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    NritahubTheme {
        SignInContent(
            uiState = SignInUiState(),
            onMobileNumberChange = {},
            onPasswordChange = {},
            onTogglePasswordVisibility = {},
            onSignInClick = {},
            onCreateAccountClick = {},
            onForgotPasswordClick = {},
            onBackClick = {}
        )
    }
}
