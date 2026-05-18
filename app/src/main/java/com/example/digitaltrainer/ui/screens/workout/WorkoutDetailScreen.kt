package com.example.digitaltrainer.ui.screens.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.digitaltrainer.R
import com.example.digitaltrainer.data.Exercise
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.digitaltrainer.ui.util.workoutCategoryLabelResId

@Composable
fun WorkoutDetailScreen(
    category: String,
    onBack: () -> Unit,
    onExerciseClick: (Exercise) -> Unit,
    viewModel: WorkoutDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(category) {
        viewModel.loadExercises(category)
    }
    val exercises by viewModel.exercises.collectAsState()

    val allPartitionKey = "ALL"
    val partitions = remember(exercises) {
        val items = exercises.map { it.partition }.distinct()
        listOf(allPartitionKey) + items
    }
    var selectedPartition by remember(category) { mutableStateOf(allPartitionKey) }
    var menuExpanded by remember { mutableStateOf(false) }
    val filteredExercises = if (selectedPartition == allPartitionKey) {
        exercises
    } else {
        exercises.filter { it.partition == selectedPartition }
    }

    val categoryLabel = stringResource(id = workoutCategoryLabelResId(category))

    val headerColor = when (category) {
        "CHEST" -> Color(0xFFFF6B6B)
        "BACK" -> Color(0xFF4ECDC4)
        "SHOULDERS" -> Color(0xFFFFD93D)
        "BICEPS" -> Color(0xFF6BCB77)
        "TRICEPS" -> Color(0xFF4D96FF)
        "LEGS" -> Color(0xFFFF8B94)
        "ABS" -> Color(0xFFA55EEA)
        else -> Color(0xFF8B5CF6)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F0F1F))) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerColor)
                    .statusBarsPadding()
                    .padding(top = 8.dp, start = 16.dp, end = 8.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(44.dp)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.action_back),
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(
                    text = stringResource(id = R.string.workout_detail_title, categoryLabel),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.action_filter),
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        partitions.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = if (option == allPartitionKey) {
                                            stringResource(id = R.string.filter_all)
                                        } else {
                                            option
                                        },
                                        fontWeight = if (option == selectedPartition) {
                                            FontWeight.Bold
                                        } else {
                                            FontWeight.Normal
                                        }
                                    )
                                },
                                onClick = {
                                    selectedPartition = option
                                    menuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Exercises Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredExercises.size) { index ->
                    ExerciseCard(
                        exercise = filteredExercises[index],
                        headerColor = headerColor,
                        onClick = { onExerciseClick(filteredExercises[index]) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    headerColor: Color,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val imageResId = context.resources.getIdentifier(exercise.imageResId, "drawable", context.packageName)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Exercise Image
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = exercise.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Dark overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            // Difficulty badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(
                        color = when (exercise.difficulty) {
                            "Beginner" -> Color(0xFF6BCB77)
                            "Intermediate" -> Color(0xFFFFD93D)
                            "Advanced" -> Color(0xFFFF6B6B)
                            else -> Color.Gray
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = exercise.difficulty,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // Exercise Name
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                        )
                    )
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = exercise.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Box(
                    modifier = Modifier
                        .height(3.dp)
                        .width(40.dp)
                        .background(
                            color = headerColor,
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}
