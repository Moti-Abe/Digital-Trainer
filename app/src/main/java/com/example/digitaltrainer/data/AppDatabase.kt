package com.example.digitaltrainer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.digitaltrainer.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Exercise::class, FoodNutrition::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao
    abstract fun foodNutritionDao(): FoodNutritionDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.exerciseDao(), database.foodNutritionDao())
                }
            }
        }

        suspend fun populateDatabase(exerciseDao: ExerciseDao, foodNutritionDao: FoodNutritionDao) {
            // Add sample exercises
            val exercises = getInitialExercises()
            exerciseDao.insertAll(exercises)

            // Add sample food nutrition data
            val foods = getInitialFoodNutrition()
            foodNutritionDao.insertAll(foods)
        }

        private fun getInitialExercises(): List<Exercise> {
            return listOf(
                Exercise(name = "Bench Press", imageResId = "exercise_chest_1", difficulty = "Intermediate", partition = "Middle", category = "CHEST"),
                Exercise(name = "Incline Press", imageResId = "exercise_chest_2", difficulty = "Intermediate", partition = "Upper", category = "CHEST"),
                Exercise(name = "Dumbbell Fly", imageResId = "exercise_chest_3", difficulty = "Beginner", partition = "Middle", category = "CHEST"),
                Exercise(name = "Cable Fly", imageResId = "exercise_chest_4", difficulty = "Beginner", partition = "Middle", category = "CHEST"),
                Exercise(name = "Push-ups", imageResId = "exercise_chest_5", difficulty = "Beginner", partition = "Middle", category = "CHEST"),
                Exercise(name = "Decline Press", imageResId = "exercise_chest_6", difficulty = "Intermediate", partition = "Lower", category = "CHEST"),
                Exercise(name = "Machine Press", imageResId = "exercise_chest_7", difficulty = "Beginner", partition = "Middle", category = "CHEST"),
                Exercise(name = "Barbell Fly", imageResId = "exercise_chest_8", difficulty = "Intermediate", partition = "Middle", category = "CHEST"),
                Exercise(name = "Smith Machine Press", imageResId = "exercise_chest_9", difficulty = "Beginner", partition = "Upper", category = "CHEST"),
                Exercise(name = "Atlas Stone", imageResId = "exercise_chest_10", difficulty = "Advanced", partition = "Lower", category = "CHEST"),
                Exercise(name = "Resistance Band Fly", imageResId = "exercise_chest_11", difficulty = "Beginner", partition = "Upper", category = "CHEST"),
                Exercise(name = "Weighted Dips", imageResId = "exercise_chest_12", difficulty = "Intermediate", partition = "Lower", category = "CHEST"),
                Exercise(name = "Pull-ups", imageResId = "exercise_back_1", difficulty = "Intermediate", partition = "Upper", category = "BACK"),
                Exercise(name = "Lat Pulldown", imageResId = "exercise_back_2", difficulty = "Beginner", partition = "Upper", category = "BACK"),
                Exercise(name = "Barbell Row", imageResId = "exercise_back_3", difficulty = "Intermediate", partition = "Middle", category = "BACK"),
                Exercise(name = "Dumbbell Row", imageResId = "exercise_back_4", difficulty = "Beginner", partition = "Middle", category = "BACK"),
                Exercise(name = "T-Bar Row", imageResId = "exercise_back_5", difficulty = "Intermediate", partition = "Middle", category = "BACK"),
                Exercise(name = "Bent Over Row", imageResId = "exercise_back_6", difficulty = "Intermediate", partition = "Lower", category = "BACK"),
                Exercise(name = "Assisted Pull-up", imageResId = "exercise_back_7", difficulty = "Beginner", partition = "Upper", category = "BACK"),
                Exercise(name = "Face Pulls", imageResId = "exercise_back_8", difficulty = "Beginner", partition = "Upper", category = "BACK"),
                Exercise(name = "Reverse Fly", imageResId = "exercise_back_9", difficulty = "Beginner", partition = "Upper", category = "BACK"),
                Exercise(name = "Seal Rows", imageResId = "exercise_back_10", difficulty = "Intermediate", partition = "Middle", category = "BACK"),
                Exercise(name = "Rack Pulls", imageResId = "exercise_back_11", difficulty = "Advanced", partition = "Lower", category = "BACK"),
                Exercise(name = "Machine Row", imageResId = "exercise_back_12", difficulty = "Beginner", partition = "Middle", category = "BACK"),
                Exercise(name = "Shoulder Press", imageResId = "exercise_shoulder_1", difficulty = "Intermediate", partition = "Front", category = "SHOULDERS"),
                Exercise(name = "Military Press", imageResId = "exercise_shoulder_2", difficulty = "Intermediate", partition = "Front", category = "SHOULDERS"),
                Exercise(name = "Lateral Raise", imageResId = "exercise_shoulder_3", difficulty = "Beginner", partition = "Side", category = "SHOULDERS"),
                Exercise(name = "Front Raise", imageResId = "exercise_shoulder_4", difficulty = "Beginner", partition = "Front", category = "SHOULDERS"),
                Exercise(name = "Reverse Fly", imageResId = "exercise_shoulder_5", difficulty = "Beginner", partition = "Rear", category = "SHOULDERS"),
                Exercise(name = "Machine Press", imageResId = "exercise_shoulder_6", difficulty = "Beginner", partition = "Front", category = "SHOULDERS"),
                Exercise(name = "Pike Push-ups", imageResId = "exercise_shoulder_7", difficulty = "Intermediate", partition = "Front", category = "SHOULDERS"),
                Exercise(name = "Shrugs", imageResId = "exercise_shoulder_8", difficulty = "Beginner", partition = "Rear", category = "SHOULDERS"),
                Exercise(name = "Upright Row", imageResId = "exercise_shoulder_9", difficulty = "Intermediate", partition = "Side", category = "SHOULDERS"),
                Exercise(name = "Plate Raises", imageResId = "exercise_shoulder_10", difficulty = "Intermediate", partition = "Front", category = "SHOULDERS"),
                Exercise(name = "Cable Raises", imageResId = "exercise_shoulder_11", difficulty = "Beginner", partition = "Side", category = "SHOULDERS"),
                Exercise(name = "Arnold Press", imageResId = "exercise_shoulder_12", difficulty = "Intermediate", partition = "Front", category = "SHOULDERS"),
                Exercise(name = "Barbell Curl", imageResId = "exercise_biceps_1", difficulty = "Beginner", partition = "Long", category = "BICEPS"),
                Exercise(name = "Dumbbell Curl", imageResId = "exercise_biceps_2", difficulty = "Beginner", partition = "Short", category = "BICEPS"),
                Exercise(name = "Hammer Curl", imageResId = "exercise_biceps_3", difficulty = "Beginner", partition = "Brachialis", category = "BICEPS"),
                Exercise(name = "Cable Curl", imageResId = "exercise_biceps_4", difficulty = "Beginner", partition = "Short", category = "BICEPS"),
                Exercise(name = "Preacher Curl", imageResId = "exercise_biceps_5", difficulty = "Intermediate", partition = "Short", category = "BICEPS"),
                Exercise(name = "Concentration Curl", imageResId = "exercise_biceps_6", difficulty = "Beginner", partition = "Short", category = "BICEPS"),
                Exercise(name = "Incline Curl", imageResId = "exercise_biceps_7", difficulty = "Beginner", partition = "Long", category = "BICEPS"),
                Exercise(name = "Machine Curl", imageResId = "exercise_biceps_8", difficulty = "Beginner", partition = "Short", category = "BICEPS"),
                Exercise(name = "Reverse Curl", imageResId = "exercise_biceps_9", difficulty = "Beginner", partition = "Brachialis", category = "BICEPS"),
                Exercise(name = "EZ Bar Curl", imageResId = "exercise_biceps_10", difficulty = "Beginner", partition = "Long", category = "BICEPS"),
                Exercise(name = "Resistance Band Curl", imageResId = "exercise_biceps_11", difficulty = "Beginner", partition = "Long", category = "BICEPS"),
                Exercise(name = "21s Curl", imageResId = "exercise_biceps_12", difficulty = "Intermediate", partition = "Short", category = "BICEPS"),
                Exercise(name = "Tricep Pushdown", imageResId = "exercise_triceps_1", difficulty = "Beginner", partition = "Lateral", category = "TRICEPS"),
                Exercise(name = "Skull Crushers", imageResId = "exercise_triceps_2", difficulty = "Intermediate", partition = "Long", category = "TRICEPS"),
                Exercise(name = "Close Grip Press", imageResId = "exercise_triceps_3", difficulty = "Intermediate", partition = "Medial", category = "TRICEPS"),
                Exercise(name = "Dips", imageResId = "exercise_triceps_4", difficulty = "Intermediate", partition = "Long", category = "TRICEPS"),
                Exercise(name = "Overhead Extension", imageResId = "exercise_triceps_5", difficulty = "Beginner", partition = "Long", category = "TRICEPS"),
                Exercise(name = "Tricep Kickbacks", imageResId = "exercise_triceps_6", difficulty = "Beginner", partition = "Lateral", category = "TRICEPS"),
                Exercise(name = "Rope Pushdown", imageResId = "exercise_triceps_7", difficulty = "Beginner", partition = "Lateral", category = "TRICEPS"),
                Exercise(name = "Bench Dips", imageResId = "exercise_triceps_8", difficulty = "Beginner", partition = "Medial", category = "TRICEPS"),
                Exercise(name = "Diamond Push-ups", imageResId = "exercise_triceps_9", difficulty = "Intermediate", partition = "Medial", category = "TRICEPS"),
                Exercise(name = "Machine Dips", imageResId = "exercise_triceps_10", difficulty = "Beginner", partition = "Long", category = "TRICEPS"),
                Exercise(name = "Lying Extension", imageResId = "exercise_triceps_11", difficulty = "Beginner", partition = "Long", category = "TRICEPS"),
                Exercise(name = "JM Press", imageResId = "exercise_triceps_12", difficulty = "Intermediate", partition = "Medial", category = "TRICEPS"),
                Exercise(name = "Barbell Squat", imageResId = "exercise_legs_1", difficulty = "Intermediate", partition = "Quads", category = "LEGS"),
                Exercise(name = "Leg Press", imageResId = "exercise_legs_2", difficulty = "Beginner", partition = "Quads", category = "LEGS"),
                Exercise(name = "Leg Curl", imageResId = "exercise_legs_3", difficulty = "Beginner", partition = "Hamstrings", category = "LEGS"),
                Exercise(name = "Leg Extension", imageResId = "exercise_legs_4", difficulty = "Beginner", partition = "Quads", category = "LEGS"),
                Exercise(name = "Deadlift", imageResId = "exercise_legs_5", difficulty = "Advanced", partition = "Hamstrings", category = "LEGS"),
                Exercise(name = "Lunges", imageResId = "exercise_legs_6", difficulty = "Beginner", partition = "Glutes", category = "LEGS"),
                Exercise(name = "Romanian Deadlift", imageResId = "exercise_legs_7", difficulty = "Intermediate", partition = "Hamstrings", category = "LEGS"),
                Exercise(name = "Bulgarian Squat", imageResId = "exercise_legs_8", difficulty = "Intermediate", partition = "Glutes", category = "LEGS"),
                Exercise(name = "Hack Squat", imageResId = "exercise_legs_9", difficulty = "Beginner", partition = "Quads", category = "LEGS"),
                Exercise(name = "Smith Machine Squat", imageResId = "exercise_legs_10", difficulty = "Beginner", partition = "Quads", category = "LEGS"),
                Exercise(name = "Calf Raise", imageResId = "exercise_legs_11", difficulty = "Beginner", partition = "Calves", category = "LEGS"),
                Exercise(name = "Sissy Squat", imageResId = "exercise_legs_12", difficulty = "Advanced", partition = "Quads", category = "LEGS"),
                Exercise(name = "Crunch", imageResId = "exercise_abs_1", difficulty = "Beginner", partition = "Upper", category = "ABS"),
                Exercise(name = "Sit-ups", imageResId = "exercise_abs_2", difficulty = "Beginner", partition = "Upper", category = "ABS"),
                Exercise(name = "Cable Crunch", imageResId = "exercise_abs_3", difficulty = "Beginner", partition = "Upper", category = "ABS"),
                Exercise(name = "Decline Sit-ups", imageResId = "exercise_abs_4", difficulty = "Intermediate", partition = "Upper", category = "ABS"),
                Exercise(name = "Leg Raises", imageResId = "exercise_abs_5", difficulty = "Intermediate", partition = "Lower", category = "ABS"),
                Exercise(name = "Hanging Leg Raises", imageResId = "exercise_abs_6", difficulty = "Advanced", partition = "Lower", category = "ABS"),
                Exercise(name = "Ab Wheel", imageResId = "exercise_abs_7", difficulty = "Advanced", partition = "Upper", category = "ABS"),
                Exercise(name = "Machine Crunch", imageResId = "exercise_abs_8", difficulty = "Beginner", partition = "Upper", category = "ABS"),
                Exercise(name = "Weighted Crunch", imageResId = "exercise_abs_9", difficulty = "Intermediate", partition = "Upper", category = "ABS"),
                Exercise(name = "Planks", imageResId = "exercise_abs_10", difficulty = "Beginner", partition = "Upper", category = "ABS"),
                Exercise(name = "V-ups", imageResId = "exercise_abs_11", difficulty = "Advanced", partition = "Lower", category = "ABS"),
                Exercise(name = "Reverse Crunch", imageResId = "exercise_abs_12", difficulty = "Beginner", partition = "Lower", category = "ABS")
            )
        }

        private fun getInitialFoodNutrition(): List<FoodNutrition> {
            return listOf(
                FoodNutrition(name = "Injera", description = "Spongy fermented flatbread made from teff flour", color = "0xFFD4A574", calories = 82, protein = "2.7g", fat = "0.7g", carbs = "17g"),
                FoodNutrition(name = "Shiro", description = "Creamy paste made from ground chickpeas or beans", color = "0xFFB8860B", calories = 165, protein = "8g", fat = "5g", carbs = "22g"),
                FoodNutrition(name = "Kik", description = "Yellow split peas stew with spices", color = "0xFFFFD700", calories = 118, protein = "8g", fat = "1g", carbs = "20g"),
                FoodNutrition(name = "Misir", description = "Red lentil stew seasoned with berbere", color = "0xFFDC143C", calories = 130, protein = "9g", fat = "2g", carbs = "22g"),
                FoodNutrition(name = "Eggs", description = "Protein-rich eggs cooked traditionally", color = "0xFFFFE4B5", calories = 155, protein = "13g", fat = "11g", carbs = "1.1g"),
                FoodNutrition(name = "Milk", description = "Fresh dairy milk, natural energy source", color = "0xFFFFFFFF", calories = 64, protein = "3.2g", fat = "3.3g", carbs = "4.8g"),
                FoodNutrition(name = "Fish", description = "Protein-rich omega-3 seafood", color = "0xFFA0522D", calories = 206, protein = "22g", fat = "13g", carbs = "0g"),
                FoodNutrition(name = "Meat", description = "Lean beef or goat meat, highly nutritious", color = "0xFF8B0000", calories = 250, protein = "26g", fat = "15g", carbs = "0g"),
                FoodNutrition(name = "Tej", description = "Honey wine, traditional fermented beverage", color = "0xFFFFB347", calories = 140, protein = "0.5g", fat = "0g", carbs = "16g")
            )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            coroutineScope: CoroutineScope
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(AppDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
