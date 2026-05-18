package com.example.digitaltrainer.data

import kotlinx.coroutines.flow.Flow

class FoodNutritionRepository(private val foodNutritionDao: FoodNutritionDao) {

    fun getAllFoodNutrition(): Flow<List<FoodNutrition>> {
        return foodNutritionDao.getAll()
    }
}

