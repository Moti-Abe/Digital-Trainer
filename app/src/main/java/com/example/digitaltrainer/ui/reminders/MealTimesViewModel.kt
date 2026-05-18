package com.example.digitaltrainer.ui.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitaltrainer.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealTimesViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val scheduler: MealReminderScheduler
) : ViewModel() {

    val mealTimes: StateFlow<MealTimesState> = combine(
        preferencesRepository.breakfastTime,
        preferencesRepository.lunchTime,
        preferencesRepository.dinnerTime
    ) { breakfast, lunch, dinner ->
        MealTimesState(
            breakfast = breakfast ?: DEFAULT_BREAKFAST,
            lunch = lunch ?: DEFAULT_LUNCH,
            dinner = dinner ?: DEFAULT_DINNER
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MealTimesState())

    fun saveMealTimes(breakfast: String, lunch: String, dinner: String) {
        viewModelScope.launch {
            preferencesRepository.updateBreakfastTime(breakfast)
            preferencesRepository.updateLunchTime(lunch)
            preferencesRepository.updateDinnerTime(dinner)
        }
        scheduleAll(breakfast, lunch, dinner)
    }

    private fun scheduleAll(breakfast: String, lunch: String, dinner: String) {
        schedule(MealReminderScheduler.MEAL_BREAKFAST, breakfast, MealReminderScheduler.REQUEST_BREAKFAST)
        schedule(MealReminderScheduler.MEAL_LUNCH, lunch, MealReminderScheduler.REQUEST_LUNCH)
        schedule(MealReminderScheduler.MEAL_DINNER, dinner, MealReminderScheduler.REQUEST_DINNER)
    }

    private fun schedule(mealType: String, time: String, requestCode: Int) {
        val parts = time.split(":")
        if (parts.size != 2) return
        val hour = parts[0].toIntOrNull() ?: return
        val minute = parts[1].toIntOrNull() ?: return
        scheduler.scheduleDailyMeal(mealType, hour, minute, requestCode)
    }

    companion object {
        private const val DEFAULT_BREAKFAST = "08:00"
        private const val DEFAULT_LUNCH = "13:00"
        private const val DEFAULT_DINNER = "19:00"
    }
}

data class MealTimesState(
    val breakfast: String = "08:00",
    val lunch: String = "13:00",
    val dinner: String = "19:00"
)

