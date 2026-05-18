package com.example.digitaltrainer.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LanguageSelectionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Choose your language",
            style = MaterialTheme.typography.headlineSmall
        )
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "English")
        }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Amharic")
        }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Oromo")
        }
    }
}

