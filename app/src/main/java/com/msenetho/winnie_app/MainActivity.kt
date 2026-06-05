package com.msenetho.winnie_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.msenetho.winnie_app.ui.navigation.AppNavHost
import com.msenetho.winnie_app.ui.theme.WinnieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            WinnieAppTheme {
                AppNavHost()
            }
        }
    }
}