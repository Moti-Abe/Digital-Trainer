package com.example.digitaltrainer.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val PREFERENCES_NAME = "digital_trainer_preferences"

val Context.appPreferencesDataStore by preferencesDataStore(name = PREFERENCES_NAME)

