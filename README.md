# DigitalTrainer

DigitalTrainer is a Kotlin + Jetpack Compose Android app focused on workouts, nutrition, and reminders, with multi-language support and a demo map screen.

## Screenshots
Add your images under `docs/screenshots/` and update the paths if needed.

![Workout screen](docs/screenshots/workout.png)
![Nutrition screen (localized)](docs/screenshots/nutrition_localized.png)
![Map screen](docs/screenshots/map.png)

## Features
- Onboarding flow: language, gender, goal, focus area, and user info
- Workout categories and exercise detail pages
- Nutrition page with meal reminders
- Demo map screen (static image)
- Per-app language switching (English, Amharic, Oromo)

## Architecture
- MVVM with Hilt for dependency injection
- Room for local database storage (workouts, exercises, nutrition data)
- DataStore for preferences (theme and language)

## Project notes
- Workout and localized nutrition features are implemented in the app.
- Built by a team of 5 members as a mobile app project.
- Taught by Mr. Sidrak.

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

## Android components overview
- Notifications: Meal reminder notifications scheduled from the Nutrition page.
- Menus: App navigation is handled with Compose Navigation and top-bar icon actions.
- Dialogs: Time picker dialog is used for setting meal reminder times.
- Location (GPS): Not implemented yet; planned for a future live map feature.
- Google Map: Demo-only static map image (no live GPS or maps SDK).
- Shared preferences: Theme preference stored using SharedPreferences.
- Files: Not used directly; assets are bundled in `res/`.
- SQLite: Accessed via Room (local database for workouts, exercises, nutrition data).
- Content provider: Not implemented yet.
- Web service (network communication): Not implemented yet (offline-first data).
- Background and foreground services: Not used; reminders are scheduled with alarms/receivers.
