package com.example.digitaltrainer.domain.repository

import com.example.digitaltrainer.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val themeMode: Flow<ThemeMode>
    val appLanguage: Flow<String?>
    val breakfastTime: Flow<String?>
    val lunchTime: Flow<String?>
    val dinnerTime: Flow<String?>

    suspend fun updateThemeMode(themeMode: ThemeMode)
    suspend fun updateAppLanguage(languageTag: String)
    suspend fun updateBreakfastTime(time: String)
    suspend fun updateLunchTime(time: String)
    suspend fun updateDinnerTime(time: String)
}
