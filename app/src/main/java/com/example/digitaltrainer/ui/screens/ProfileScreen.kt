package com.example.digitaltrainer.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitaltrainer.R
import com.example.digitaltrainer.ui.util.focusAreaLabelResId
import com.example.digitaltrainer.ui.util.goalLabelResId

@Composable
fun ProfileScreen(
    gender: String,
    goal: String,
    focusArea: String,
    birthYear: String,
    weight: String,
    height: String,
    languageTag: String,
    onBack: () -> Unit,
    onResetProfile: () -> Unit
) {
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isDarkTheme = readThemePreference(context)
    }

    val backgroundColor = if (isDarkTheme) Color(0xFF0F0F1F) else Color.White
    val headerColor = Color(0xFF161925)
    val cardColor = if (isDarkTheme) Color(0xFF1A1A2E) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black

    val languageLabel = when (languageTag) {
        "am" -> stringResource(id = R.string.language_amharic)
        "om" -> stringResource(id = R.string.language_oromo)
        else -> stringResource(id = R.string.language_english)
    }

    val genderLabel = when (gender.trim().lowercase()) {
        "male" -> stringResource(id = R.string.gender_male)
        "female" -> stringResource(id = R.string.gender_female)
        else -> gender
    }
    val goalLabel = stringResource(id = goalLabelResId(goal))
    val focusLabel = stringResource(id = focusAreaLabelResId(focusArea))

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.size(44.dp),
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.action_back),
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                    Text(
                        text = stringResource(id = R.string.profile_title),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(44.dp))
                }
            }

            // Theme toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.profile_dark_mode),
                    fontSize = 16.sp,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { enabled ->
                        isDarkTheme = enabled
                        writeThemePreference(context, enabled)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF8B5CF6),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFF1A1A2E)
                    )
                )
            }

            // Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    ProfileField(
                        label = stringResource(id = R.string.profile_label_gender),
                        value = genderLabel,
                        cardColor = cardColor,
                        textColor = textColor
                    )
                }
                item {
                    ProfileField(
                        label = stringResource(id = R.string.profile_label_goal),
                        value = goalLabel,
                        cardColor = cardColor,
                        textColor = textColor
                    )
                }
                item {
                    ProfileField(
                        label = stringResource(id = R.string.profile_label_focus_area),
                        value = focusLabel,
                        cardColor = cardColor,
                        textColor = textColor
                    )
                }
                item {
                    ProfileField(
                        label = stringResource(id = R.string.profile_label_birth_year),
                        value = birthYear,
                        cardColor = cardColor,
                        textColor = textColor
                    )
                }
                item {
                    ProfileField(
                        label = stringResource(id = R.string.profile_label_weight),
                        value = stringResource(id = R.string.profile_weight_value_kg, weight),
                        cardColor = cardColor,
                        textColor = textColor
                    )
                }
                item {
                    ProfileField(
                        label = stringResource(id = R.string.profile_label_height),
                        value = stringResource(id = R.string.profile_height_value_cm, height),
                        cardColor = cardColor,
                        textColor = textColor
                    )
                }
                item {
                    ProfileField(
                        label = stringResource(id = R.string.profile_label_language),
                        value = languageLabel,
                        cardColor = cardColor,
                        textColor = textColor
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Reset Profile Button
            Button(
                onClick = onResetProfile,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B5CF6),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(id = R.string.profile_reset_profile),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String,
    cardColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        border = CardDefaults.outlinedCardBorder(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = label,
                    fontSize = 16.sp,
                    color = textColor,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF8B5CF6), shape = CircleShape),
                content = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.action_edit),
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        }
    }
}

private fun readThemePreference(context: Context): Boolean {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getBoolean(KEY_DARK_THEME, false)
}

private fun writeThemePreference(context: Context, isDark: Boolean) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putBoolean(KEY_DARK_THEME, isDark).apply()
}

private const val PREFS_NAME = "digital_trainer_prefs"
private const val KEY_DARK_THEME = "dark_theme_enabled"
