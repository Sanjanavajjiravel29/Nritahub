package com.simats.nritahub.ui.settings

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.nritahub.ui.home.HomeTab
import com.simats.nritahub.ui.home.TabIcon
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
fun AccountSettingsScreen(
    viewModel: AccountSettingsViewModel = remember { AccountSettingsViewModel() },
    onNavigateToHome: () -> Unit,
    onNavigateToEditProfile: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            AccountSettingsNavigationEvent.NavigateToHome -> {
                onNavigateToHome()
                viewModel.onNavigationHandled()
            }
            AccountSettingsNavigationEvent.EditProfileClicked -> {
                onNavigateToEditProfile()
                viewModel.onNavigationHandled()
            }
            AccountSettingsNavigationEvent.SignOutClicked -> {
                onSignOut()
                viewModel.onNavigationHandled()
            }
            AccountSettingsNavigationEvent.ChangePasswordClicked -> {
                viewModel.onNavigationHandled()
            }
            AccountSettingsNavigationEvent.DeleteAccountClicked -> {
                viewModel.onNavigationHandled()
            }
            AccountSettingsNavigationEvent.None -> {}
        }
    }

    AccountSettingsContent(
        uiState = uiState,
        onOptionClick = viewModel::onOptionClicked,
        onSignOutClick = viewModel::onSignOutClicked,
        onDeleteAccountClick = viewModel::onDeleteAccountClicked,
        onTabSelect = viewModel::onTabSelected
    )
}

@Composable
fun AccountSettingsContent(
    uiState: AccountSettingsUiState,
    onOptionClick: (String) -> Unit,
    onSignOutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    onTabSelect: (HomeTab) -> Unit
) {
    Scaffold(
        containerColor = CreamBackground,
        bottomBar = {
            // Bottom 5-Tab Navigation Bar
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
                    HomeTab.entries.forEach { tab ->
                        val isSelected = uiState.selectedTab == tab
                        val contentColor = if (isSelected) DeepPlum else MutedBodyText

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onTabSelect(tab) }
                        ) {
                            TabIcon(tab = tab, color = contentColor)
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
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(SoftLavenderBadge),
                        contentAlignment = Alignment.Center
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
                                color = DeepPlum
                            )
                            drawLine(color = DeepPlum, start = Offset(w * 0.25f, h * 0.45f), end = Offset(w * 0.25f, h * 0.9f), strokeWidth = 2f)
                            drawLine(color = DeepPlum, start = Offset(w * 0.75f, h * 0.45f), end = Offset(w * 0.75f, h * 0.9f), strokeWidth = 2f)
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${uiState.headerNumber} / NRITYAHUB · ACCOUNT & SETTINGS",
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

            // User Profile Card
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
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(SoftLavenderBadge),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = uiState.userInitials,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepPlum
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = uiState.userName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkMaroonText
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = uiState.roleBadge,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DeepPlum
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = uiState.userDetails,
                        fontSize = 13.sp,
                        color = MutedBodyText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = uiState.academyName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Make it yours" Section
            Text(
                text = uiState.makeItYoursTitle,
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    uiState.makeItYoursOptions.forEachIndexed { index, option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOptionClick(option.actionType) }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = option.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                if (option.subtitle != null) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = option.subtitle,
                                        fontSize = 12.sp,
                                        color = MutedBodyText
                                    )
                                }
                            }

                            Canvas(modifier = Modifier.size(12.dp)) {
                                val w = size.width
                                val h = size.height
                                val stroke = 1.5.dp.toPx()
                                drawLine(color = DeepPlum, start = Offset(0f, 0f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                                drawLine(color = DeepPlum, start = Offset(w, h / 2), end = Offset(0f, h), strokeWidth = stroke, cap = StrokeCap.Round)
                            }
                        }

                        if (index < uiState.makeItYoursOptions.size - 1) {
                            HorizontalDivider(
                                color = CardBorder,
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Privacy & support" Section
            Text(
                text = uiState.privacyTitle,
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    uiState.privacyOptions.forEachIndexed { index, option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOptionClick(option.actionType) }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = option.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                if (option.subtitle != null) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = option.subtitle,
                                        fontSize = 12.sp,
                                        color = MutedBodyText
                                    )
                                }
                            }

                            Canvas(modifier = Modifier.size(12.dp)) {
                                val w = size.width
                                val h = size.height
                                val stroke = 1.5.dp.toPx()
                                drawLine(color = DeepPlum, start = Offset(0f, 0f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                                drawLine(color = DeepPlum, start = Offset(w, h / 2), end = Offset(0f, h), strokeWidth = stroke, cap = StrokeCap.Round)
                            }
                        }

                        if (index < uiState.privacyOptions.size - 1) {
                            HorizontalDivider(
                                color = CardBorder,
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sign Out Button
            OutlinedButton(
                onClick = onSignOutClick,
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
                Text(
                    text = "Sign out",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepPlum
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Delete Account Box
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F1F3)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Text(
                        text = uiState.deleteTitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF880E4F)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = uiState.deleteDescription,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = MutedBodyText
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onDeleteAccountClick() }
                    ) {
                        Text(
                            text = uiState.deleteLinkText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF880E4F)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Canvas(modifier = Modifier.size(10.dp)) {
                            val w = size.width
                            val h = size.height
                            val stroke = 1.5.dp.toPx()
                            drawLine(color = Color(0xFF880E4F), start = Offset(0f, h / 2), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                            drawLine(color = Color(0xFF880E4F), start = Offset(w * 0.6f, h * 0.2f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                            drawLine(color = Color(0xFF880E4F), start = Offset(w * 0.6f, h * 0.8f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer App Version
            Text(
                text = uiState.appVersionFooter,
                fontSize = 11.sp,
                color = MutedBodyText,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountSettingsScreenPreview() {
    NritahubTheme {
        AccountSettingsContent(
            uiState = AccountSettingsUiState(),
            onOptionClick = {},
            onSignOutClick = {},
            onDeleteAccountClick = {},
            onTabSelect = {}
        )
    }
}
