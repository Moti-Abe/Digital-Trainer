package com.example.digitaltrainer.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitaltrainer.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    val appLanguage: StateFlow<String?> = preferencesRepository.appLanguage
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun applyLanguage(languageTag: String?) {
        if (!languageTag.isNullOrBlank()) {
            val currentLocales = AppCompatDelegate.getApplicationLocales().toLanguageTags()
            if (currentLocales != languageTag) {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(languageTag)
                )
            }
        }
    }

    fun updateLanguage(languageTag: String) {
        val currentLocales = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        if (currentLocales != languageTag) {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(languageTag)
            )
        }
        viewModelScope.launch {
            preferencesRepository.updateAppLanguage(languageTag)
        }
    }
}
