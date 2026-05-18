package com.example.digitaltrainer.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.digitaltrainer.R
import com.example.digitaltrainer.ui.screens.onboarding.components.OptionCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.res.stringResource

data class LanguageOption(
    val title: String,
    val languageTag: String
)

@Composable
fun LanguageScreen(
    selectedLanguageTag: String,
    onLanguageSelected: (LanguageOption) -> Unit
) {
    val options = listOf(
        LanguageOption(stringResource(id = R.string.language_english), "en"),
        LanguageOption(stringResource(id = R.string.language_amharic), "am"),
        LanguageOption(stringResource(id = R.string.language_oromo), "om")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.language_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Confirm",
                    tint = Color.Black
                )
            }
        }
        items(options.size) { index ->
            val option = options[index]
            OptionCard(
                title = option.title,
                isSelected = selectedLanguageTag == option.languageTag,
                onClick = { onLanguageSelected(option) }
            )
        }
    }
}
