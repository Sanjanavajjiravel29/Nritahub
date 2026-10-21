package com.simats.nritahub.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.nritahub.ui.theme.CreamBackground
import com.simats.nritahub.ui.theme.DarkMaroonText
import com.simats.nritahub.ui.theme.DeepPlum
import com.simats.nritahub.ui.theme.MutedBodyText

@Composable
fun HomeScreen(
    onBackToSplash: () -> Unit
) {
    Scaffold(
        containerColor = CreamBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NrityaHub Home",
                fontFamily = FontFamily.Serif,
                fontSize = 32.sp,
                color = DarkMaroonText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your dance classes, daily practice & schedules.",
                fontSize = 15.sp,
                color = MutedBodyText
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(onClick = onBackToSplash) {
                Text(
                    text = "Back to Splash Screen",
                    color = DeepPlum
                )
            }
        }
    }
}
