package com.example.digitaltrainer.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.digitaltrainer.data.AppDatabase
import com.example.digitaltrainer.data.ExerciseRepository
import com.example.digitaltrainer.data.FoodNutritionRepository
import com.example.digitaltrainer.data.local.datastore.appPreferencesDataStore
import com.example.digitaltrainer.domain.repository.PreferencesRepository
import com.example.digitaltrainer.data.repository.PreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideDatabase(application: Application, coroutineScope: CoroutineScope) =
        AppDatabase.getDatabase(application, coroutineScope)

    @Provides
    @Singleton
    fun provideExerciseRepository(database: AppDatabase) =
        ExerciseRepository(database.exerciseDao())

    @Provides
    @Singleton
    fun provideFoodNutritionRepository(database: AppDatabase) =
        FoodNutritionRepository(database.foodNutritionDao())

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.appPreferencesDataStore

    @Provides
    @Singleton
    fun providePreferencesRepository(
        dataStore: DataStore<Preferences>
    ): PreferencesRepository = PreferencesRepositoryImpl(dataStore)

}
