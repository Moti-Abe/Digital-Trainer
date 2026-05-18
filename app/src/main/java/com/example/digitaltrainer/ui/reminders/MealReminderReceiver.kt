package com.example.digitaltrainer.ui.reminders

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.digitaltrainer.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MealReminderReceiver : BroadcastReceiver() {
    @Inject lateinit var scheduler: MealReminderScheduler

    override fun onReceive(context: Context, intent: Intent) {
        val mealType = intent.getStringExtra(MealReminderScheduler.EXTRA_MEAL_TYPE) ?: return
        val hour = intent.getIntExtra(MealReminderScheduler.EXTRA_HOUR, -1)
        val minute = intent.getIntExtra(MealReminderScheduler.EXTRA_MINUTE, -1)
        val exact = intent.getBooleanExtra(MealReminderScheduler.EXTRA_EXACT, false)
        if (hour < 0 || minute < 0) return

        createChannelIfNeeded(context)

        val (title, body) = when (mealType) {
            MealReminderScheduler.MEAL_BREAKFAST -> {
                context.getString(R.string.notification_breakfast_title) to
                    context.getString(R.string.notification_meal_body)
            }
            MealReminderScheduler.MEAL_LUNCH -> {
                context.getString(R.string.notification_lunch_title) to
                    context.getString(R.string.notification_meal_body)
            }
            MealReminderScheduler.MEAL_DINNER -> {
                context.getString(R.string.notification_dinner_title) to
                    context.getString(R.string.notification_meal_body)
            }
            else -> return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (!granted) {
                return
            }
        }

        try {
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context).notify(mealType.hashCode(), notification)
        } catch (securityException: SecurityException) {
            return
        }

        if (exact) {
            val requestCode = when (mealType) {
                MealReminderScheduler.MEAL_BREAKFAST -> MealReminderScheduler.REQUEST_BREAKFAST
                MealReminderScheduler.MEAL_LUNCH -> MealReminderScheduler.REQUEST_LUNCH
                else -> MealReminderScheduler.REQUEST_DINNER
            }
            scheduler.scheduleDailyMeal(mealType, hour, minute, requestCode)
        }
    }

    private fun createChannelIfNeeded(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.notification_channel_meals),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.notification_channel_meals_desc)
            }
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "meal_reminders"
    }
}
