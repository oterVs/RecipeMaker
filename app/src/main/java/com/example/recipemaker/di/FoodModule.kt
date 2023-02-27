package com.example.recipemaker.di

import com.example.recipemaker.data.remote.FoodRepositoryImpl
import com.example.recipemaker.domain.interfaces.FoodRepository
import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FoodModule {

    @Provides
    @Singleton
    fun provideFoodRepository(
        @FirebaseModule.FoodCollection foodCollection: CollectionReference
    ): FoodRepository {
        return FoodRepositoryImpl(
            foodCollection
        )
    }
}