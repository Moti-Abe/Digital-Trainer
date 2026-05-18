package com.example.digitaltrainer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_nutrition")
data class FoodNutrition(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val color: String,
    val calories: Int,
    val protein: String,
    val fat: String,
    val carbs: String
)

