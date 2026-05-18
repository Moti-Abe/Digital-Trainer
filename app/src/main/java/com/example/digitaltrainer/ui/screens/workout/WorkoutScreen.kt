package com.example.digitaltrainer.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import com.example.digitaltrainer.R
import com.example.digitaltrainer.ui.util.FocusAreaIds

@Composable
fun WorkoutScreen(
    onNutritionClick: () -> Unit,
    onProfileClick: () -> Unit,
    onStartPlanClick: () -> Unit,
    onWorkoutCategoryClick: (String) -> Unit,
    onMapClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F0F1F))) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F0F1F))
                    .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.workout_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Nutrition Icon
                    IconButton(
                        onClick = onNutritionClick,
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color(0xFF1F1F2E), shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_nutrition),
                            contentDescription = stringResource(id = R.string.action_nutrition),
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    // Map Icon
                    IconButton(
                        onClick = onMapClick,
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color(0xFF1F1F2E), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = stringResource(id = R.string.action_map),
                            tint = Color.White
                        )
                    }
                    // Profile Icon
                    IconButton(
                        onClick = onProfileClick,
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color(0xFF1F1F2E), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(id = R.string.action_profile),
                            tint = Color.White
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Purple Card with CTA
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF8B5CF6))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.workout_cta_title, 30),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = stringResource(id = R.string.workout_cta_subtitle),
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    lineHeight = 18.sp
                                )
                            }
                            Button(
                                onClick = onStartPlanClick,
                                modifier = Modifier
                                    .height(44.dp)
                                    .wrapContentWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.action_start),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF8B5CF6)
                                )
                            }
                        }
                    }
                }

                // Workout Categories
                val workouts = listOf(
                    WorkoutCategory(FocusAreaIds.CHEST, R.string.workout_category_chest, R.drawable.workout_chest),
                    WorkoutCategory(FocusAreaIds.BACK, R.string.workout_category_back, R.drawable.workout_back),
                    WorkoutCategory(FocusAreaIds.SHOULDERS, R.string.workout_category_shoulders, R.drawable.workout_shoulders),
                    WorkoutCategory(FocusAreaIds.BICEPS, R.string.workout_category_biceps, R.drawable.workout_biceps),
                    WorkoutCategory(FocusAreaIds.TRICEPS, R.string.workout_category_triceps, R.drawable.workout_triceps),
                    WorkoutCategory(FocusAreaIds.LEGS, R.string.workout_category_legs, R.drawable.workout_legs),
                    WorkoutCategory(FocusAreaIds.ABS, R.string.workout_category_abs, R.drawable.workout_abs)
                )

                items(workouts.size) { index ->
                    WorkoutCategoryCard(
                        category = workouts[index],
                        onClick = { onWorkoutCategoryClick(workouts[index].id) }
                    )
                }
            }
        }
    }
}

private fun categoryColor(categoryId: String): Color {
    return when (categoryId) {
        FocusAreaIds.CHEST -> Color(0xFFFF6B6B)
        FocusAreaIds.BACK -> Color(0xFF4ECDC4)
        FocusAreaIds.SHOULDERS -> Color(0xFFFFD93D)
        FocusAreaIds.BICEPS -> Color(0xFF6BCB77)
        FocusAreaIds.TRICEPS -> Color(0xFF4D96FF)
        FocusAreaIds.LEGS -> Color(0xFFFF8B94)
        FocusAreaIds.ABS -> Color(0xFFA55EEA)
        else -> Color(0xFF8B5CF6)
    }
}

@Composable
private fun WorkoutCategoryCard(
    category: WorkoutCategory,
    onClick: () -> Unit
) {
    val exerciseCount = 12
    val headerColor = categoryColor(category.id)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = headerColor.copy(alpha = 0.25f))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = category.imageResId),
                contentDescription = stringResource(id = category.labelResId),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                headerColor.copy(alpha = 0.7f),
                                Color.Black.copy(alpha = 0.45f)
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = stringResource(id = category.labelResId),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = pluralStringResource(
                            id = R.plurals.workout_exercise_count,
                            count = exerciseCount,
                            exerciseCount
                        ),
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

data class WorkoutCategory(
    val id: String,
    val labelResId: Int,
    val imageResId: Int
)
