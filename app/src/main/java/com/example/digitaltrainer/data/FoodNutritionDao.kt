package com.example.digitaltrainer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodNutritionDao {
    @Query("SELECT * FROM food_nutrition")
    fun getAll(): Flow<List<FoodNutrition>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<FoodNutrition>)
}

