package com.example.digitaltrainer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val imageResId: String,
    val difficulty: String,
    val partition: String,
    val category: String
): Serializable
