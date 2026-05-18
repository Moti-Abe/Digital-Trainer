package com.example.digitaltrainer.ui.reminders

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.digitaltrainer.domain.repository.PreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var preferencesRepository: PreferencesRepository
    @Inject lateinit var scheduler: MealReminderScheduler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        CoroutineScope(Dispatchers.IO).launch {
            val breakfast = preferencesRepository.breakfastTime.first()
            val lunch = preferencesRepository.lunchTime.first()
            val dinner = preferencesRepository.dinnerTime.first()

            breakfast?.let { schedule(MealReminderScheduler.MEAL_BREAKFAST, it) }
            lunch?.let { schedule(MealReminderScheduler.MEAL_LUNCH, it) }
            dinner?.let { schedule(MealReminderScheduler.MEAL_DINNER, it) }
        }
    }

    private fun schedule(mealType: String, time: String) {
        val parts = time.split(":")
        if (parts.size != 2) return
        val hour = parts[0].toIntOrNull() ?: return
        val minute = parts[1].toIntOrNull() ?: return

        when (mealType) {
            MealReminderScheduler.MEAL_BREAKFAST -> scheduler.scheduleDailyMeal(
                mealType,
                hour,
                minute,
                MealReminderScheduler.REQUEST_BREAKFAST
            )
            MealReminderScheduler.MEAL_LUNCH -> scheduler.scheduleDailyMeal(
                mealType,
                hour,
                minute,
                MealReminderScheduler.REQUEST_LUNCH
            )
            MealReminderScheduler.MEAL_DINNER -> scheduler.scheduleDailyMeal(
                mealType,
                hour,
                minute,
                MealReminderScheduler.REQUEST_DINNER
            )
        }
    }
}

