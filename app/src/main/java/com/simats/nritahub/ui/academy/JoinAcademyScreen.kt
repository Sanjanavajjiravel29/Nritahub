package com.simats.nritahub.ui.academy

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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun JoinAcademyScreen(
    viewModel: JoinAcademyViewModel = remember { JoinAcademyViewModel() },
    onBackToSplash: () -> Unit,
    onContinueToHome: (role: RoleType, academy: String, code: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (val event = uiState.navigationEvent) {
            is JoinAcademyNavigationEvent.NavigateBack -> {
                onBackToSplash()
                viewModel.onNavigationHandled()
            }
            is JoinAcademyNavigationEvent.NavigateToHome -> {
                onContinueToHome(event.role, event.academy, event.code)
                viewModel.onNavigationHandled()
            }
            JoinAcademyNavigationEvent.None -> {}
        }
    }

    JoinAcademyContent(
        uiState = uiState,
        onRoleSelect = viewModel::onRoleSelected,
        onAcademySelect = viewModel::onAcademySelected,
        onInvitationCodeChange = viewModel::onInvitationCodeChanged,
        onContinueClick = viewModel::onContinueClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun JoinAcademyContent(
    uiState: JoinAcademyUiState,
    onRoleSelect: (RoleType) -> Unit,
    onAcademySelect: (String) -> Unit,
    onInvitationCodeChange: (String) -> Unit,
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var academyDropdownExpanded by remember { mutableStateOf(false) }

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
                        text = "${uiState.headerNumber} / NRITYAHUB · JOIN YOUR ACADEMY",
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

            // Title: "Find your place"
            Text(
                text = "${uiState.titlePrefix} ${uiState.titleSuffix}",
                fontFamily = FontFamily.Serif,
                fontSize = 32.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = uiState.subtitle,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Role Selection Cards
            RoleType.entries.forEach { role ->
                val isSelected = uiState.selectedRole == role
                RoleCard(
                    role = role,
                    isSelected = isSelected,
                    onClick = { onRoleSelect(role) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // "Your academy" Label & Dropdown
            Text(
                text = "Your academy",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { academyDropdownExpanded = true }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = uiState.selectedAcademy,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkMaroonText
                        )
                        // Dropdown Arrow
                        Canvas(modifier = Modifier.size(12.dp)) {
                            val w = size.width
                            val h = size.height
                            drawPath(
                                path = Path().apply {
                                    moveTo(0f, h * 0.3f)
                                    lineTo(w / 2, h * 0.8f)
                                    lineTo(w, h * 0.3f)
                                },
                                color = DeepPlum,
                                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }
                    }
                }

                DropdownMenu(
                    expanded = academyDropdownExpanded,
                    onDismissRequest = { academyDropdownExpanded = false },
                    modifier = Modifier.background(CardBackground)
                ) {
                    uiState.availableAcademies.forEach { academy ->
                        DropdownMenuItem(
                            text = { Text(text = academy, fontSize = 13.sp, color = DarkMaroonText) },
                            onClick = {
                                onAcademySelect(academy)
                                academyDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Invitation Code Notice Box
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
                                    moveTo(w * 0.5f, 0f)
                                    lineTo(w, h * 0.4f)
                                    lineTo(0f, h * 0.4f)
                                    close()
                                },
                                color = DeepPlum
                            )
                            drawLine(
                                color = DeepPlum,
                                start = Offset(w * 0.3f, h * 0.5f),
                                end = Offset(w * 0.3f, h),
                                strokeWidth = 2f
                            )
                            drawLine(
                                color = DeepPlum,
                                start = Offset(w * 0.7f, h * 0.5f),
                                end = Offset(w * 0.7f, h),
                                strokeWidth = 2f
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Joining another academy? Enter the Invitation code shared by your academy office.",
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // "Academy invitation code (optional)"
            Text(
                text = "Academy invitation code (optional)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = uiState.invitationCode,
                onValueChange = onInvitationCodeChange,
                placeholder = { Text("e.g. SKSKM26", fontSize = 13.sp, color = MutedBodyText) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedBorderColor = DeepPlum,
                    unfocusedBorderColor = CardBorder
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Primary Action Button ("Continue as Student")
            Button(
                onClick = onContinueClick,
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
                        text = "Continue as ${uiState.selectedRole.title}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
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

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RoleCard(
    role: RoleType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (isSelected) SoftLavenderBadge else CardBackground
    val border = if (isSelected) null else BorderStroke(1.dp, CardBorder)

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = border,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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
                // Role Custom Icon
                Box(
                    modifier = Modifier.size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    RoleIcon(role = role, color = DeepPlum)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = role.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkMaroonText
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = role.description,
                        fontSize = 12.sp,
                        color = MutedBodyText
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Right Selection Control
            if (isSelected) {
                Text(
                    text = "Selected",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepPlum
                )
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
}

@Composable
fun RoleIcon(role: RoleType, color: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height

        when (role) {
            RoleType.STUDENT -> {
                // Graduation Cap Icon
                drawPath(
                    path = Path().apply {
                        moveTo(w * 0.5f, 0f)
                        lineTo(w, h * 0.35f)
                        lineTo(w * 0.5f, h * 0.7f)
                        lineTo(0f, h * 0.35f)
                        close()
                    },
                    color = color
                )
                drawPath(
                    path = Path().apply {
                        moveTo(w * 0.25f, h * 0.55f)
                        lineTo(w * 0.25f, h * 0.85f)
                        lineTo(w * 0.75f, h * 0.85f)
                        lineTo(w * 0.75f, h * 0.55f)
                    },
                    color = color,
                    style = Stroke(width = 2f)
                )
            }
            RoleType.INSTRUCTOR -> {
                // Musical Note Icon
                drawCircle(color = color, radius = 3.5f, center = Offset(w * 0.35f, h * 0.75f))
                drawLine(color = color, start = Offset(w * 0.45f, h * 0.75f), end = Offset(w * 0.45f, h * 0.15f), strokeWidth = 2.5f)
                drawPath(
                    path = Path().apply {
                        moveTo(w * 0.45f, h * 0.15f)
                        lineTo(w * 0.85f, h * 0.3f)
                        lineTo(w * 0.85f, h * 0.45f)
                        lineTo(w * 0.45f, h * 0.3f)
                    },
                    color = color
                )
            }
            RoleType.ADMINISTRATOR -> {
                // Building Icon
                drawPath(
                    path = Path().apply {
                        moveTo(0f, h)
                        lineTo(0f, h * 0.2f)
                        lineTo(w, h * 0.2f)
                        lineTo(w, h)
                        close()
                    },
                    color = color,
                    style = Stroke(width = 2f)
                )
                drawLine(color = color, start = Offset(w * 0.35f, h * 0.5f), end = Offset(w * 0.35f, h * 0.8f), strokeWidth = 2f)
                drawLine(color = color, start = Offset(w * 0.65f, h * 0.5f), end = Offset(w * 0.65f, h * 0.8f), strokeWidth = 2f)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JoinAcademyScreenPreview() {
    NritahubTheme {
        JoinAcademyContent(
            uiState = JoinAcademyUiState(),
            onRoleSelect = {},
            onAcademySelect = {},
            onInvitationCodeChange = {},
            onContinueClick = {},
            onBackClick = {}
        )
    }
}
