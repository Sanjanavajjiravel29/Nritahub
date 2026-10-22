package com.simats.nritahub.ui.submit

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
fun SubmitAssignmentScreen(
    viewModel: SubmitAssignmentViewModel = remember { SubmitAssignmentViewModel() },
    onBackToPrevious: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            SubmitAssignmentNavigationEvent.NavigateBack -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            SubmitAssignmentNavigationEvent.ChooseFileClicked -> {
                viewModel.onNavigationHandled()
            }
            SubmitAssignmentNavigationEvent.RemoveFileClicked -> {
                viewModel.onNavigationHandled()
            }
            SubmitAssignmentNavigationEvent.DraftSaved -> {
                onBackToPrevious()
                viewModel.onNavigationHandled()
            }
            SubmitAssignmentNavigationEvent.SubmittedToInstructor -> {
                onSubmitSuccess()
                viewModel.onNavigationHandled()
            }
            SubmitAssignmentNavigationEvent.None -> {}
        }
    }

    SubmitAssignmentContent(
        uiState = uiState,
        onInstructorNoteChange = viewModel::onInstructorNoteChanged,
        onToggleConsentClick = viewModel::toggleConsent,
        onChooseFileClick = viewModel::onChooseFileClicked,
        onRemoveFileClick = viewModel::onRemoveFileClicked,
        onSubmitClick = viewModel::onSubmitClicked,
        onSaveDraftClick = viewModel::onSaveDraftClicked,
        onBackClick = viewModel::onBackClicked
    )
}

@Composable
fun SubmitAssignmentContent(
    uiState: SubmitAssignmentUiState,
    onInstructorNoteChange: (String) -> Unit,
    onToggleConsentClick: () -> Unit,
    onChooseFileClick: () -> Unit,
    onRemoveFileClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onSaveDraftClick: () -> Unit,
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
                        text = "${uiState.headerNumber} / NRITYAHUB · SUBMIT ASSIGNMENT",
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

            // Assignment Badge
            Box(
                modifier = Modifier
                    .background(SoftLavenderBadge, shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = uiState.badgeText,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = DeepPlum
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle
            Text(
                text = uiState.subtitle,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Upload Box Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SoftLavenderBadge),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Cloud Upload Icon
                    Canvas(modifier = Modifier.size(24.dp)) {
                        val w = size.width
                        val h = size.height
                        drawPath(
                            path = Path().apply {
                                moveTo(w * 0.5f, h * 0.6f)
                                lineTo(w * 0.5f, h * 0.15f)
                                moveTo(w * 0.3f, h * 0.35f)
                                lineTo(w * 0.5f, h * 0.15f)
                                lineTo(w * 0.7f, h * 0.35f)
                            },
                            color = DeepPlum,
                            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
                        )
                        drawPath(
                            path = Path().apply {
                                moveTo(w * 0.15f, h * 0.7f)
                                cubicTo(0f, h * 0.7f, 0f, h, w * 0.5f, h)
                                cubicTo(w, h, w, h * 0.7f, w * 0.85f, h * 0.7f)
                            },
                            color = DeepPlum,
                            style = Stroke(width = 1.8.dp.toPx())
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Add a practice recording",
                        fontFamily = FontFamily.Serif,
                        fontSize = 22.sp,
                        color = DarkMaroonText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Choose a video or document from your device.",
                        fontSize = 13.sp,
                        color = MutedBodyText
                    )

                    Text(
                        text = "MP4, MOV or PDF · up to 100 MB",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MediumMaroonText
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = onChooseFileClick,
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = CardBackground,
                            contentColor = DarkMaroonText
                        ),
                        border = BorderStroke(1.dp, CardBorder),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Choose another file",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Uploaded File Card (if attached)
            if (uiState.isFileAttached) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.fillMaxWidth()
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
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = uiState.attachedFileName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = uiState.attachedFileDetails,
                                    fontSize = 12.sp,
                                    color = MutedBodyText
                                )
                            }

                            // Checkmark Icon
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE8F5E9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.size(12.dp)) {
                                    val w = size.width
                                    val h = size.height
                                    drawPath(
                                        path = Path().apply {
                                            moveTo(0f, h * 0.5f)
                                            lineTo(w * 0.35f, h * 0.85f)
                                            lineTo(w, h * 0.2f)
                                        },
                                        color = Color(0xFF2E7D32),
                                        style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Ready to submit · Remove file",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32),
                            modifier = Modifier.clickable { onRemoveFileClick() }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // "A note for your instructor" Field
            Text(
                text = "A note for your instructor",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = uiState.instructorNote,
                onValueChange = onInstructorNoteChange,
                minLines = 3,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedBorderColor = DeepPlum,
                    unfocusedBorderColor = CardBorder
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Consent Checkbox Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleConsentClick() }
            ) {
                Canvas(modifier = Modifier.size(20.dp)) {
                    val r = size.width / 2
                    if (uiState.isConsentChecked) {
                        drawCircle(color = Color(0xFF2E7D32), radius = r)
                        drawPath(
                            path = Path().apply {
                                moveTo(r * 0.5f, r)
                                lineTo(r * 0.85f, r * 1.35f)
                                lineTo(r * 1.5f, r * 0.65f)
                            },
                            color = Color.White,
                            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                        )
                    } else {
                        drawCircle(color = CardBorder, radius = r, style = Stroke(width = 1.5.dp.toPx()))
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "I consent to sharing this recording with my instructor for learning feedback.",
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = DarkMaroonText,
                    modifier = Modifier.weight(1f)
                )
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
                        // Shield Icon
                        Canvas(modifier = Modifier.size(14.dp)) {
                            val w = size.width
                            val h = size.height
                            drawPath(
                                path = Path().apply {
                                    moveTo(w * 0.5f, 0f)
                                    lineTo(w, h * 0.25f)
                                    lineTo(w, h * 0.65f)
                                    cubicTo(w, h, w * 0.5f, h, w * 0.5f, h)
                                    cubicTo(w * 0.5f, h, 0f, h, 0f, h * 0.65f)
                                    lineTo(0f, h * 0.25f)
                                    close()
                                },
                                color = DeepPlum,
                                style = Stroke(width = 1.5.dp.toPx())
                            )
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

            // Primary Action Button ("Submit to Meera")
            Button(
                onClick = onSubmitClick,
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
                        text = uiState.submitButtonText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Send Paperplane Icon
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        drawPath(
                            path = Path().apply {
                                moveTo(0f, 0f)
                                lineTo(w, h * 0.5f)
                                lineTo(0f, h)
                                lineTo(w * 0.3f, h * 0.5f)
                                close()
                            },
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Secondary Action Button ("Save draft")
            OutlinedButton(
                onClick = onSaveDraftClick,
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
                    text = "Save draft",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepPlum
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubmitAssignmentScreenPreview() {
    NritahubTheme {
        SubmitAssignmentContent(
            uiState = SubmitAssignmentUiState(),
            onInstructorNoteChange = {},
            onToggleConsentClick = {},
            onChooseFileClick = {},
            onRemoveFileClick = {},
            onSubmitClick = {},
            onSaveDraftClick = {},
            onBackClick = {}
        )
    }
}
