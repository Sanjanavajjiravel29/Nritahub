package com.simats.nritahub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.simats.nritahub.ui.academy.JoinAcademyScreen
import com.simats.nritahub.ui.auth.SignInScreen
import com.simats.nritahub.ui.home.HomeScreen
import com.simats.nritahub.ui.register.CreateAccountScreen
import com.simats.nritahub.ui.splash.SplashScreen
import com.simats.nritahub.ui.theme.NritahubTheme

enum class Screen {
    Splash,
    JoinAcademy,
    SignIn,
    CreateAccount,
    Home
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NritahubTheme {
                NrityaHubApp()
            }
        }
    }
}

@Composable
fun NrityaHubApp() {
    // Set to Screen.CreateAccount so it launches immediately on device
    var currentScreen by remember { mutableStateOf(Screen.CreateAccount) }

    when (currentScreen) {
        Screen.Splash -> {
            SplashScreen(
                onNavigateToHome = { currentScreen = Screen.JoinAcademy },
                onNavigateToSignIn = { currentScreen = Screen.SignIn }
            )
        }
        Screen.JoinAcademy -> {
            JoinAcademyScreen(
                onBackToSplash = { currentScreen = Screen.Splash },
                onContinueToHome = { _, _, _ ->
                    currentScreen = Screen.CreateAccount
                }
            )
        }
        Screen.SignIn -> {
            SignInScreen(
                onBackToPrevious = { currentScreen = Screen.Splash },
                onSignInSuccess = { currentScreen = Screen.Home },
                onCreateAccountClicked = { currentScreen = Screen.CreateAccount }
            )
        }
        Screen.CreateAccount -> {
            CreateAccountScreen(
                onBackToPrevious = { currentScreen = Screen.JoinAcademy },
                onSignInClicked = { currentScreen = Screen.SignIn },
                onCreateAccountSuccess = { currentScreen = Screen.Home }
            )
        }
        Screen.Home -> {
            HomeScreen(
                onBackToSplash = { currentScreen = Screen.Splash }
            )
        }
    }
}
