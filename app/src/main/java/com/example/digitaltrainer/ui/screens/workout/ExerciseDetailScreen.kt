package com.example.digitaltrainer.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitaltrainer.R
import java.util.Locale

@Composable
fun ExerciseDetailScreen(
    category: String,
    exerciseName: String,
    onBack: () -> Unit
) {
    val headerColor = when (category.uppercase(Locale.US)) {
        "CHEST" -> Color(0xFFFF6B6B)
        "BACK" -> Color(0xFF4ECDC4)
        "SHOULDERS" -> Color(0xFFFFD93D)
        "BICEPS" -> Color(0xFF6BCB77)
        "TRICEPS" -> Color(0xFF4D96FF)
        "LEGS" -> Color(0xFFFF8B94)
        "ABS" -> Color(0xFFA55EEA)
        else -> Color(0xFF8B5CF6)
    }
    val context = LocalContext.current
    val description = buildExerciseDescription(context, exerciseName, category)
    val steps = buildExerciseSteps(context, exerciseName)
    val youtubeUrl = buildYoutubeUrl(exerciseName)
    val uriHandler = LocalUriHandler.current

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F0F1F))) {
        Column(modifier = Modifier.fillMaxSize()) {
            RowHeader(
                title = exerciseName,
                headerColor = headerColor,
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = stringResource(id = R.string.exercise_how_to_title),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = description,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            lineHeight = 18.sp
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            steps.forEachIndexed { index, step ->
                                Text(
                                    text = "${index + 1}. $step",
                                    fontSize = 13.sp,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = stringResource(id = R.string.exercise_video_guide_title),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = stringResource(id = R.string.exercise_video_guide_subtitle),
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Button(
                            onClick = { uriHandler.openUri(youtubeUrl) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.exercise_watch_on_youtube),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun RowHeader(
    title: String,
    headerColor: Color,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(headerColor)
            .statusBarsPadding()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack, modifier = Modifier.size(44.dp)) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.action_back),
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.exercise_details_subtitle),
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color.White.copy(alpha = 0.15f), CircleShape)
        )
    }
}

private fun buildExerciseDescription(
    context: android.content.Context,
    exerciseName: String,
    category: String
): String {
    val muscle = category.lowercase(Locale.US)
    return context.getString(R.string.exercise_description_template, exerciseName, muscle)
}

private fun buildExerciseSteps(
    context: android.content.Context,
    exerciseName: String
): List<String> {
    return listOf(
        context.getString(R.string.exercise_step1_template, exerciseName),
        context.getString(R.string.exercise_step2_template),
        context.getString(R.string.exercise_step3_template)
    )
}

private fun buildYoutubeUrl(exerciseName: String): String {
    val query = java.net.URLEncoder.encode("$exerciseName exercise", "UTF-8")
    return "https://www.youtube.com/results?search_query=$query"
}
