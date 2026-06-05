package com.msenetho.winnie_app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.msenetho.winnie_app.ui.customspeech.CustomSpeechScreen
import com.msenetho.winnie_app.ui.library.ClipLibraryScreen
import com.msenetho.winnie_app.domain.model.VoiceClip

enum class AppScreen(val route: String, val label: String) {
    Quotes("quotes", "Quotes"),
    TTS("tts", "Text-to-Speech")
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == AppScreen.Quotes.route,
                    onClick = {
                        if (currentRoute != AppScreen.Quotes.route) {
                            navController.navigate(AppScreen.Quotes.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    label = { Text(AppScreen.Quotes.label) },
                    icon = { Text("Q") } // placeholder for icon image
                )

                NavigationBarItem(
                    selected = currentRoute == AppScreen.TTS.route,
                    onClick = {
                        if (currentRoute != AppScreen.TTS.route) {
                            navController.navigate(AppScreen.TTS.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    label = { Text(AppScreen.TTS.label) },
                    icon = { Text("T") } // placeholder for icon image
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Quotes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppScreen.Quotes.route) {
                ClipLibraryScreen(
                    clips = listOf(
                        VoiceClip(
                            title = "think think think",
                            assetPath = "audio/think_think_think.mp3"
                        ),
                        VoiceClip(
                            title = "winnie the pooh",
                            assetPath = "audio/winnie_the_pooh.mp3"
                        )
                    )
                )
            }
            composable(AppScreen.TTS.route) {
                CustomSpeechScreen()
            }
        }
    }
}