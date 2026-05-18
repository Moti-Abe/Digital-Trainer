package com.example.digitaltrainer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.digitaltrainer.ui.onboarding.OnboardingState
import com.example.digitaltrainer.ui.screens.NutritionScreen
import com.example.digitaltrainer.ui.screens.ProfileScreen
import com.example.digitaltrainer.ui.screens.onboarding.FocusAreaScreen
import com.example.digitaltrainer.ui.screens.onboarding.GenderScreen
import com.example.digitaltrainer.ui.screens.onboarding.GoalScreen
import com.example.digitaltrainer.ui.screens.onboarding.LanguageOption
import com.example.digitaltrainer.ui.screens.onboarding.LanguageScreen
import com.example.digitaltrainer.ui.screens.onboarding.LoadingScreen
import com.example.digitaltrainer.ui.screens.onboarding.PlanComparisonScreen
import com.example.digitaltrainer.ui.screens.onboarding.PlanSummaryScreen
import com.example.digitaltrainer.ui.screens.onboarding.UserInfoScreen
import com.example.digitaltrainer.ui.screens.workout.WorkoutScreen
import com.example.digitaltrainer.ui.screens.workout.WorkoutDetailScreen
import com.example.digitaltrainer.ui.screens.workout.ExerciseDetailScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.digitaltrainer.ui.settings.AppSettingsViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.digitaltrainer.ui.screens.MealTimeScreen
import com.example.digitaltrainer.ui.screens.MapsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var state by remember { mutableStateOf(OnboardingState()) }
    val settingsViewModel: AppSettingsViewModel = hiltViewModel()
    val appLanguage by settingsViewModel.appLanguage.collectAsState()

    LaunchedEffect(appLanguage) {
        settingsViewModel.applyLanguage(appLanguage)
    }

    NavHost(
        navController = navController,
        startDestination = AppRoute.LANGUAGE
    ) {
        composable(AppRoute.LANGUAGE) {
            LanguageScreen(
                selectedLanguageTag = state.language,
                onLanguageSelected = { option: LanguageOption ->
                    state = state.copy(language = option.languageTag)
                    settingsViewModel.updateLanguage(option.languageTag)
                    navController.navigate(AppRoute.GENDER)
                }
            )
        }
        composable(AppRoute.GENDER) {
            GenderScreen(
                selectedGender = state.gender,
                onGenderSelected = {
                    state = state.copy(gender = it)
                },
                onNext = { navController.navigate(AppRoute.GOAL) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoute.GOAL) {
            GoalScreen(
                selectedGoal = state.goal,
                onGoalSelected = {
                    state = state.copy(goal = it)
                },
                onNext = { navController.navigate(AppRoute.FOCUS) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoute.FOCUS) {
            FocusAreaScreen(
                selectedFocusArea = state.focusArea,
                onFocusAreaSelected = {
                    state = state.copy(focusArea = it)
                },
                onNext = { navController.navigate(AppRoute.PLAN_COMPARE) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoute.PLAN_COMPARE) {
            PlanComparisonScreen(
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppRoute.USER_INFO) }
            )
        }
        composable(AppRoute.USER_INFO) {
            UserInfoScreen(
                age = state.age,
                height = state.height,
                weight = state.weight,
                onAgeChanged = { state = state.copy(age = it) },
                onHeightChanged = { state = state.copy(height = it) },
                onWeightChanged = { state = state.copy(weight = it) },
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppRoute.LOADING) }
            )
        }
        composable(AppRoute.LOADING) {
            LoadingScreen(
                height = state.height,
                weight = state.weight,
                focusArea = state.focusArea,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppRoute.SUMMARY) }
            )
        }
        composable(AppRoute.SUMMARY) {
            PlanSummaryScreen(
                focusArea = state.focusArea,
                onBack = { navController.popBackStack() },
                onFinish = { navController.navigate(AppRoute.WORKOUT) }
            )
        }
        composable(AppRoute.WORKOUT) {
            WorkoutScreen(
                onNutritionClick = { navController.navigate(AppRoute.NUTRITION) },
                onProfileClick = { navController.navigate(AppRoute.PROFILE) },
                onStartPlanClick = { },
                onWorkoutCategoryClick = { category ->
                    navController.navigate("workout_detail/$category")
                },
                onMapClick = { navController.navigate(AppRoute.MAPS) }
            )
        }
        composable(
            AppRoute.WORKOUT_DETAIL,
            arguments = listOf(
                navArgument("category") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "CHEST"
            WorkoutDetailScreen(
                category = category,
                onBack = { navController.popBackStack() },
                onExerciseClick = { exercise ->
                    navController.navigate(AppRoute.exerciseDetailRoute(category, exercise.name))
                }
            )
        }
        composable(
            AppRoute.EXERCISE_DETAIL,
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("exercise") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "CHEST"
            val exerciseName = backStackEntry.arguments?.getString("exercise") ?: "Exercise"
            ExerciseDetailScreen(
                category = category,
                exerciseName = exerciseName,
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoute.PROFILE) {
            ProfileScreen(
                gender = state.gender,
                goal = state.goal,
                focusArea = state.focusArea,
                birthYear = state.age,
                weight = state.weight,
                height = state.height,
                languageTag = state.language,
                onBack = { navController.popBackStack() },
                onResetProfile = { navController.popBackStack() }
            )
        }
        composable(AppRoute.NUTRITION) {
            NutritionScreen(
                onBack = { navController.popBackStack() },
                onMealTimesClick = { navController.navigate(AppRoute.MEAL_TIMES) }
            )
        }
        composable(AppRoute.MEAL_TIMES) {
            MealTimeScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoute.MAPS) {
            MapsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
