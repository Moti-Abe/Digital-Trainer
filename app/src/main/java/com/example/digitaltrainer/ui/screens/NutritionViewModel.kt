package com.example.digitaltrainer.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitaltrainer.data.FoodNutrition
import com.example.digitaltrainer.data.FoodNutritionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val foodNutritionRepository: FoodNutritionRepository
) : ViewModel() {

    val foodNutrition: StateFlow<List<FoodNutrition>> =
        foodNutritionRepository.getAllFoodNutrition()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}

