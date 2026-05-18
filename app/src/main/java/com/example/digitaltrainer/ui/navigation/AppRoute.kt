package com.example.digitaltrainer.ui.navigation

import android.net.Uri

object AppRoute {
    const val LANGUAGE = "onboarding_language"
    const val GENDER = "onboarding_gender"
    const val GOAL = "onboarding_goal"
    const val FOCUS = "onboarding_focus"
    const val PLAN_COMPARE = "onboarding_plan_compare"
    const val USER_INFO = "onboarding_user_info"
    const val LOADING = "onboarding_loading"
    const val SUMMARY = "onboarding_summary"
    const val WORKOUT = "workout_main"
    const val WORKOUT_DETAIL = "workout_detail/{category}"
    const val NUTRITION = "nutrition_plan"
    const val PROFILE = "profile"
    const val EXERCISE_DETAIL = "exercise_detail?category={category}&exercise={exercise}"
    const val MEAL_TIMES = "meal_times"
    const val MAPS = "maps"

    fun exerciseDetailRoute(category: String, exercise: String): String {
        val encodedCategory = Uri.encode(category)
        val encodedExercise = Uri.encode(exercise)
        return "exercise_detail?category=$encodedCategory&exercise=$encodedExercise"
    }
}
