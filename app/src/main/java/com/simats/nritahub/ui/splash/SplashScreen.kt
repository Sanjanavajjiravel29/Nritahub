package com.simats.nritahub.ui.splash

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.style.TextAlign
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
import com.simats.nritahub.ui.theme.MutedFooterText
import com.simats.nritahub.ui.theme.NritahubTheme
import com.simats.nritahub.ui.theme.SoftLavenderBadge
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = remember { SplashViewModel() },
    onNavigateToHome: () -> Unit,
    onNavigateToSignIn: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            SplashNavigationEvent.NavigateToHome -> {
                onNavigateToHome()
                viewModel.onNavigationHandled()
            }
            SplashNavigationEvent.NavigateToSignIn -> {
                onNavigateToSignIn()
                viewModel.onNavigationHandled()
            }
            SplashNavigationEvent.None -> {}
        }
    }

    SplashContent(
        uiState = uiState,
        onGetStartedClick = viewModel::onGetStartedClicked,
        onSignInClick = viewModel::onSignInClicked,
    )
}

@Composable
fun SplashContent(
    uiState: SplashUiState,
    onGetStartedClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    Scaffold(
        containerColor = CreamBackground,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
        ) {
            // Top Bar / Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(SoftLavenderBadge),
                    contentAlignment = Alignment.Center,
                ) {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        val w = size.width
                        val h = size.height
                        drawPath(
                            path = Path().apply {
                                moveTo(w * 0.5f, 0f)
                                lineTo(w, h * 0.35f)
                                lineTo(0f, h * 0.35f)
                                close()
                            },
                            color = DeepPlum,
                        )
                        drawLine(
                            color = DeepPlum,
                            start = Offset(w * 0.25f, h * 0.45f),
                            end = Offset(w * 0.25f, h * 0.9f),
                            strokeWidth = 2f,
                        )
                        drawLine(
                            color = DeepPlum,
                            start = Offset(w * 0.75f, h * 0.45f),
                            end = Offset(w * 0.75f, h * 0.9f),
                            strokeWidth = 2f,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${uiState.headerBadgeNumber} / ${uiState.appTitle.uppercase()} · ${uiState.tagLine}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    color = MediumMaroonText,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main App Title
            Text(
                text = uiState.appTitle,
                fontFamily = FontFamily.Serif,
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                color = DarkMaroonText,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Image Card
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.splash),
                    contentDescription = "NrityaHub Splash Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pill / Badge Tag
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(SoftLavenderBadge, shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp),
            ) {
                Text(
                    text = uiState.academyName,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = DeepPlum,
                )
                Spacer(modifier = Modifier.width(6.dp))
                Canvas(modifier = Modifier.size(14.dp)) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val r = size.width / 4
                    drawCircle(color = DeepPlum, radius = 2.5f, center = center)
                    for (i in 0 until 4) {
                        val angle = Math.toRadians((i * 90).toDouble())
                        val x = center.x + (r * cos(angle)).toFloat()
                        val y = center.y + (r * sin(angle)).toFloat()
                        drawCircle(
                            color = DeepPlum,
                            radius = 2f,
                            center = Offset(x, y),
                            style = Stroke(width = 1.5f),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Headline
            Text(
                text = uiState.headline,
                fontFamily = FontFamily.Serif,
                fontSize = 36.sp,
                lineHeight = 42.sp,
                fontWeight = FontWeight.Normal,
                color = DarkMaroonText,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle
            Text(
                text = uiState.subtitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DeepPlum,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = uiState.description,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MutedBodyText,
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Primary Action Button (Get Started)
            Button(
                onClick = onGetStartedClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepPlum,
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Get Started",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val stroke = 2.dp.toPx()
                        drawLine(
                            color = Color.White,
                            start = Offset(0f, h / 2),
                            end = Offset(w, h / 2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round,
                        )
                        drawLine(
                            color = Color.White,
                            start = Offset(w * 0.6f, h * 0.2f),
                            end = Offset(w, h / 2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round,
                        )
                        drawLine(
                            color = Color.White,
                            start = Offset(w * 0.6f, h * 0.8f),
                            end = Offset(w, h / 2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Secondary Action Button (Sign in)
            OutlinedButton(
                onClick = onSignInClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = CardBackground,
                    contentColor = DeepPlum,
                ),
                border = BorderStroke(1.dp, CardBorder),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            ) {
                Text(
                    text = "I already have an account · Sign in",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = DeepPlum,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Footer Text
            Text(
                text = uiState.footerText,
                fontSize = 12.sp,
                color = MutedFooterText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    NritahubTheme {
        SplashContent(
            uiState = SplashUiState(),
            onGetStartedClick = {},
            onSignInClick = {},
        )
    }
}
