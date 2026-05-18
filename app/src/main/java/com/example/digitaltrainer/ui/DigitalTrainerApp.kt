package com.example.digitaltrainer.ui

import androidx.compose.runtime.Composable
import com.example.digitaltrainer.ui.navigation.AppNavHost
import com.example.digitaltrainer.ui.theme.DigitalTrainerTheme

@Composable
fun DigitalTrainerApp() {
    DigitalTrainerTheme {
        AppNavHost()
    }
}

