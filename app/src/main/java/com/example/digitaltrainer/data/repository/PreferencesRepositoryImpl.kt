package com.example.digitaltrainer.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.digitaltrainer.domain.model.ThemeMode
import com.example.digitaltrainer.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    override val themeMode: Flow<ThemeMode> = dataStore.data.map { preferences ->
        ThemeMode.fromName(preferences[Keys.THEME_MODE])
    }

    override val appLanguage: Flow<String?> = dataStore.data.map { preferences ->
        preferences[Keys.APP_LANGUAGE]
    }

    override val breakfastTime: Flow<String?> = dataStore.data.map { preferences ->
        preferences[Keys.BREAKFAST_TIME]
    }

    override val lunchTime: Flow<String?> = dataStore.data.map { preferences ->
        preferences[Keys.LUNCH_TIME]
    }

    override val dinnerTime: Flow<String?> = dataStore.data.map { preferences ->
        preferences[Keys.DINNER_TIME]
    }

    override suspend fun updateThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[Keys.THEME_MODE] = themeMode.name
        }
    }

    override suspend fun updateAppLanguage(languageTag: String) {
        dataStore.edit { preferences ->
            preferences[Keys.APP_LANGUAGE] = languageTag
        }
    }

    override suspend fun updateBreakfastTime(time: String) {
        dataStore.edit { preferences ->
            preferences[Keys.BREAKFAST_TIME] = time
        }
    }

    override suspend fun updateLunchTime(time: String) {
        dataStore.edit { preferences ->
            preferences[Keys.LUNCH_TIME] = time
        }
    }

    override suspend fun updateDinnerTime(time: String) {
        dataStore.edit { preferences ->
            preferences[Keys.DINNER_TIME] = time
        }
    }

    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
        val BREAKFAST_TIME = stringPreferencesKey("breakfast_time")
        val LUNCH_TIME = stringPreferencesKey("lunch_time")
        val DINNER_TIME = stringPreferencesKey("dinner_time")
    }
}
