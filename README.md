# DigitalTrainer

DigitalTrainer is a Kotlin + Jetpack Compose Android app focused on workouts, nutrition, and reminders, with multi-language support and a demo map screen.

## Features
- Onboarding flow: language, gender, goal, focus area, and user info
- Workout categories and exercise detail pages
- Nutrition page with meal reminders
- Demo map screen (static image)
- Per-app language switching (English, Amharic, Oromo)

## Tech stack
- Kotlin, Jetpack Compose, Material 3
- MVVM, Hilt
- Room, DataStore

## Getting started
1. Open the project in Android Studio.
2. Sync Gradle.
3. Run the `app` configuration on an emulator or device.

## Notes
- Meal reminders use alarms and notifications. On Android 13+, allow notification permission when prompted.
- The map screen is a static placeholder image for demo purposes.
- If you plan to add a real Google Map, set a valid API key in `app/src/main/AndroidManifest.xml`.
