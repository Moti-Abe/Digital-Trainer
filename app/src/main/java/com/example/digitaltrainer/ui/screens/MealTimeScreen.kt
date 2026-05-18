package com.example.digitaltrainer.ui.screens

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.digitaltrainer.R
import com.example.digitaltrainer.ui.reminders.MealTimesState
import com.example.digitaltrainer.ui.reminders.MealTimesViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

@Composable
fun MealTimeScreen(
    onBack: () -> Unit,
    viewModel: MealTimesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.mealTimes.collectAsState()
    var localState by remember { mutableStateOf(MealTimesState()) }
    var pickerFor by remember { mutableStateOf<MealType?>(null) }

    LaunchedEffect(state) {
        localState = state
    }

    LaunchedEffect(pickerFor) {
        val mealType = pickerFor ?: return@LaunchedEffect
        val initial = when (mealType) {
            MealType.BREAKFAST -> localState.breakfast
            MealType.LUNCH -> localState.lunch
            MealType.DINNER -> localState.dinner
        }
        val (hour, minute) = parseTime(initial)
        val dialog = TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                val newTime = formatStorageTime(selectedHour, selectedMinute)
                localState = when (mealType) {
                    MealType.BREAKFAST -> localState.copy(breakfast = newTime)
                    MealType.LUNCH -> localState.copy(lunch = newTime)
                    MealType.DINNER -> localState.copy(dinner = newTime)
                }
                pickerFor = null
            },
            hour,
            minute,
            false
        )
        dialog.setOnCancelListener { pickerFor = null }
        dialog.show()
    }

    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F0F1F))) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(44.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.action_back),
                        tint = Color.White
                    )
                }
                Text(
                    text = stringResource(id = R.string.meal_times_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.meal_times_subtitle),
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )

                MealTimeCard(
                    title = stringResource(id = R.string.meal_breakfast),
                    time = formatDisplayTime(localState.breakfast),
                    onEdit = { pickerFor = MealType.BREAKFAST }
                )
                MealTimeCard(
                    title = stringResource(id = R.string.meal_lunch),
                    time = formatDisplayTime(localState.lunch),
                    onEdit = { pickerFor = MealType.LUNCH }
                )
                MealTimeCard(
                    title = stringResource(id = R.string.meal_dinner),
                    time = formatDisplayTime(localState.dinner),
                    onEdit = { pickerFor = MealType.DINNER }
                )

                Button(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        viewModel.saveMealTimes(
                            breakfast = localState.breakfast,
                            lunch = localState.lunch,
                            dinner = localState.dinner
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6))
                ) {
                    Text(
                        text = stringResource(id = R.string.action_save),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Button(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        showTestNotification(context)
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A2E))
                ) {
                    Text(
                        text = stringResource(id = R.string.meal_test_notification),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun MealTimeCard(
    title: String,
    time: String,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = time,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B5CF6)
                )
            }
            Button(
                onClick = onEdit,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = stringResource(id = R.string.action_edit))
            }
        }
    }
}

private fun formatStorageTime(hour: Int, minute: Int): String {
    return String.format("%02d:%02d", hour, minute)
}

private fun formatDisplayTime(value: String): String {
    val (hour, minute) = parseTime(value)
    val calendar = java.util.Calendar.getInstance().apply {
        set(java.util.Calendar.HOUR_OF_DAY, hour)
        set(java.util.Calendar.MINUTE, minute)
        set(java.util.Calendar.SECOND, 0)
    }
    val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    return formatter.format(calendar.time)
}

private fun parseTime(value: String): Pair<Int, Int> {
    val parts = value.split(":")
    val hour = parts.getOrNull(0)?.toIntOrNull() ?: 8
    val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0
    return hour to minute
}

private fun showTestNotification(context: android.content.Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        if (!granted) {
            return
        }
    }

    try {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "meal_reminders",
                context.getString(R.string.notification_channel_meals),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.notification_channel_meals_desc)
            }
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "meal_reminders")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.notification_meal_body))
            .setContentText(context.getString(R.string.notification_meal_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(9999, notification)
    } catch (securityException: SecurityException) {
        // Permission denied at runtime; ignore.
    }
}

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER
}
