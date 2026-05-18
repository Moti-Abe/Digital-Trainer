package com.example.digitaltrainer.ui.util

import androidx.annotation.StringRes
import com.example.digitaltrainer.R
import java.util.Locale

/**
 * Stable IDs used in state/navigation/DB that should NOT be localized.
 * UI should map these to localized string resources.
 */
object GoalIds {
    const val LOSE_WEIGHT = "LOSE_WEIGHT"
    const val BUILD_MUSCLE = "BUILD_MUSCLE"
    const val KEEP_FIT = "KEEP_FIT"
}

object FocusAreaIds {
    const val FULL_BODY = "FULL_BODY"
    const val CHEST = "CHEST"
    const val BACK = "BACK"
    const val SHOULDERS = "SHOULDERS"
    const val BICEPS = "BICEPS"
    const val TRICEPS = "TRICEPS"
    const val LEGS = "LEGS"
    const val ABS = "ABS"
}

@StringRes
fun goalLabelResId(goalId: String?): Int {
    return when (goalId?.uppercase(Locale.US)) {
        GoalIds.LOSE_WEIGHT -> R.string.goal_lose_weight
        GoalIds.BUILD_MUSCLE -> R.string.goal_build_muscle
        GoalIds.KEEP_FIT -> R.string.goal_keep_fit
        else -> R.string.plan_summary_not_selected
    }
}

@StringRes
fun focusAreaLabelResId(focusAreaId: String?): Int {
    return when (focusAreaId?.uppercase(Locale.US)) {
        FocusAreaIds.FULL_BODY -> R.string.focus_full_body
        FocusAreaIds.CHEST -> R.string.workout_category_chest
        FocusAreaIds.BACK -> R.string.workout_category_back
        FocusAreaIds.SHOULDERS -> R.string.workout_category_shoulders
        FocusAreaIds.BICEPS -> R.string.workout_category_biceps
        FocusAreaIds.TRICEPS -> R.string.workout_category_triceps
        FocusAreaIds.LEGS -> R.string.workout_category_legs
        FocusAreaIds.ABS -> R.string.workout_category_abs
        else -> R.string.plan_summary_not_selected
    }
}

@StringRes
fun workoutCategoryLabelResId(categoryId: String?): Int {
    return when (categoryId?.uppercase(Locale.US)) {
        FocusAreaIds.CHEST -> R.string.workout_category_chest
        FocusAreaIds.BACK -> R.string.workout_category_back
        FocusAreaIds.SHOULDERS -> R.string.workout_category_shoulders
        FocusAreaIds.BICEPS -> R.string.workout_category_biceps
        FocusAreaIds.TRICEPS -> R.string.workout_category_triceps
        FocusAreaIds.LEGS -> R.string.workout_category_legs
        FocusAreaIds.ABS -> R.string.workout_category_abs
        else -> R.string.workout_title
    }
}

