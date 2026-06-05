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
import com.msenetho.winnie_app.ui.library.ClipLibraryRoute

enum class AppScreen(val route: String, val label: String) {
    Quotes("quotes", "Quotes"),
    Tts("tts", "Text-to-Speech")
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
                    selected = currentRoute == AppScreen.Tts.route,
                    onClick = {
                        if (currentRoute != AppScreen.Tts.route) {
                            navController.navigate(AppScreen.Tts.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    label = { Text(AppScreen.Tts.label) },
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
                ClipLibraryRoute()
            }
            composable(AppScreen.Tts.route) {
                CustomSpeechScreen()
            }
        }
    }
}