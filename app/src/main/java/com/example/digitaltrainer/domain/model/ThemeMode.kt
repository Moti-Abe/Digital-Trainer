package com.example.digitaltrainer.domain.model

enum class ThemeMode {
    System,
    Light,
    Dark;

    companion object {
        fun fromName(value: String?): ThemeMode =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: System
    }
}

