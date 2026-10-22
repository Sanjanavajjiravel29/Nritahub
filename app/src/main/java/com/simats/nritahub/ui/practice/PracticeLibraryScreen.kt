package com.simats.nritahub.ui.practice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.nritahub.R
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
fun PracticeLibraryScreen(
    viewModel: PracticeLibraryViewModel = remember { PracticeLibraryViewModel() },
    onNavigateToHome: () -> Unit,
    onNavigateToResourceDetail: () -> Unit,
    onNavigateToSchedule: () -> Unit = {},
    onNavigateToProgress: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            PracticeLibraryNavigationEvent.NavigateToHome -> {
                onNavigateToHome()
                viewModel.onNavigationHandled()
            }
            PracticeLibraryNavigationEvent.PlayFeaturedVideo -> {
                onNavigateToResourceDetail()
                viewModel.onNavigationHandled()
            }
            PracticeLibraryNavigationEvent.OpenResource -> {
                onNavigateToResourceDetail()
                viewModel.onNavigationHandled()
            }
            PracticeLibraryNavigationEvent.None -> {}
        }
    }

    PracticeLibraryContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChanged,
        onCategorySelect = viewModel::onCategorySelected,
        onTabSelect = { tab ->
            when (tab) {
                HomeTab.HOME -> onNavigateToHome()
                HomeTab.SCHEDULE -> onNavigateToSchedule()
                HomeTab.PRACTICE -> viewModel.onTabSelected(tab)
                HomeTab.PROGRESS -> onNavigateToProgress()
                HomeTab.PROFILE -> onNavigateToProfile()
            }
        },
        onPlayFeaturedClick = viewModel::onPlayFeaturedClicked,
        onResourceClick = viewModel::onResourceClicked
    )
}

@Composable
fun PracticeLibraryContent(
    uiState: PracticeLibraryUiState,
    onSearchQueryChange: (String) -> Unit,
    onCategorySelect: (CategoryFilter) -> Unit,
    onTabSelect: (HomeTab) -> Unit,
    onPlayFeaturedClick: () -> Unit,
    onResourceClick: () -> Unit
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
                        text = "${uiState.headerNumber} / NRITYAHUB · PRACTICE LIBRARY",
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

            // Find a resource Search Field
            Text(
                text = "Find a resource",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("Search videos, notes or exercises", fontSize = 13.sp, color = MutedBodyText) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedBorderColor = DeepPlum,
                    unfocusedBorderColor = CardBorder
                ),
                trailingIcon = {
                    // Search Glass Icon
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val r = w * 0.35f
                        drawCircle(color = DeepPlum, radius = r, center = Offset(r, r), style = Stroke(width = 1.5.dp.toPx()))
                        drawLine(color = DeepPlum, start = Offset(r * 1.7f, r * 1.7f), end = Offset(w, h), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Category Filter Chips Row 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryFilter.entries.forEach { category ->
                    val isSelected = uiState.selectedCategory == category
                    val chipBg = if (isSelected) DeepPlum else Color(0xFFF3ECE5)
                    val chipTextColor = if (isSelected) Color.White else DarkMaroonText
                    val label = when (category) {
                        CategoryFilter.FOR_YOU -> "For you"
                        CategoryFilter.VIDEOS -> "Videos"
                        CategoryFilter.NOTES -> "Notes"
                        CategoryFilter.SAVED -> "Saved"
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

            Spacer(modifier = Modifier.height(10.dp))

            // Style / Level Filter Chips Row 2
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .background(SoftLavenderBadge, shape = RoundedCornerShape(16.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = uiState.danceStyleFilter,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepPlum
                    )
                }

                Box(
                    modifier = Modifier
                        .background(SoftLavenderBadge, shape = RoundedCornerShape(16.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = uiState.levelFilter,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Featured Practice Video Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPlayFeaturedClick() }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Video Thumbnail
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.splash),
                            contentDescription = "Practice Video",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(SoftLavenderBadge, shape = RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = uiState.featuredBadge,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp,
                                    color = DeepPlum
                                )
                            }

                            Text(
                                text = uiState.featuredDuration,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DarkMaroonText
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = uiState.featuredTitle,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkMaroonText
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = uiState.featuredSubtitle,
                            fontSize = 13.sp,
                            color = MutedBodyText
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Continue watching",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = uiState.watchProgressText,
                                    fontSize = 12.sp,
                                    color = MutedBodyText
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Play",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepPlum
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Canvas(modifier = Modifier.size(12.dp)) {
                                    val w = size.width
                                    val h = size.height
                                    val stroke = 1.5.dp.toPx()
                                    drawLine(color = DeepPlum, start = Offset(0f, 0f), end = Offset(w, h / 2), strokeWidth = stroke, cap = StrokeCap.Round)
                                    drawLine(color = DeepPlum, start = Offset(w, h / 2), end = Offset(0f, h), strokeWidth = stroke, cap = StrokeCap.Round)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Build your foundation" Section
            Text(
                text = "Build your foundation",
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
                    uiState.foundationResources.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onResourceClick() }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = item.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkMaroonText
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.subtitle,
                                    fontSize = 12.sp,
                                    color = MutedBodyText
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (item.type.isNotEmpty()) {
                                    Text(
                                        text = item.type,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepPlum
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                }

                                Canvas(modifier = Modifier.size(16.dp)) {
                                    val w = size.width
                                    val h = size.height
                                    when (item.iconType) {
                                        "download" -> {
                                            drawLine(color = DeepPlum, start = Offset(w * 0.5f, 0f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.5.dp.toPx())
                                            drawLine(color = DeepPlum, start = Offset(w * 0.25f, h * 0.45f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.5.dp.toPx())
                                            drawLine(color = DeepPlum, start = Offset(w * 0.75f, h * 0.45f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.5.dp.toPx())
                                            drawLine(color = DeepPlum, start = Offset(0f, h), end = Offset(w, h), strokeWidth = 1.5.dp.toPx())
                                        }
                                        "check" -> {
                                            drawPath(
                                                path = Path().apply {
                                                    moveTo(0f, h * 0.5f)
                                                    lineTo(w * 0.35f, h * 0.85f)
                                                    lineTo(w, h * 0.2f)
                                                },
                                                color = DeepPlum,
                                                style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
                                            )
                                        }
                                        "audio" -> {
                                            drawPath(
                                                path = Path().apply {
                                                    moveTo(0f, h * 0.6f)
                                                    lineTo(w * 0.3f, h * 0.6f)
                                                    lineTo(w * 0.6f, h)
                                                    lineTo(w * 0.6f, 0f)
                                                    lineTo(w * 0.3f, h * 0.4f)
                                                    lineTo(0f, h * 0.4f)
                                                    close()
                                                },
                                                color = DeepPlum
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        if (index < uiState.foundationResources.size - 1) {
                            HorizontalDivider(
                                color = CardBorder,
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Offline Resources Notice Box
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
                            drawLine(color = DeepPlum, start = Offset(w * 0.5f, 0f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.5.dp.toPx())
                            drawLine(color = DeepPlum, start = Offset(w * 0.25f, h * 0.45f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.5.dp.toPx())
                            drawLine(color = DeepPlum, start = Offset(w * 0.75f, h * 0.45f), end = Offset(w * 0.5f, h * 0.7f), strokeWidth = 1.5.dp.toPx())
                            drawLine(color = DeepPlum, start = Offset(0f, h), end = Offset(w, h), strokeWidth = 1.5.dp.toPx())
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = uiState.offlineNotice,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = DeepPlum
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer Link
            Text(
                text = uiState.exploreStylesText,
                fontSize = 12.sp,
                color = MutedBodyText,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PracticeLibraryScreenPreview() {
    NritahubTheme {
        PracticeLibraryContent(
            uiState = PracticeLibraryUiState(),
            onSearchQueryChange = {},
            onCategorySelect = {},
            onTabSelect = {},
            onPlayFeaturedClick = {},
            onResourceClick = {}
        )
    }
}
