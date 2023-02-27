package com.example.recipemaker.di

import android.content.Context
import com.example.recipemaker.data.remote.DataStoreRepositoryIml

import com.example.recipemaker.domain.interfaces.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


import javax.inject.Singleton




@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context): DataStoreRepository = DataStoreRepositoryIml(appContext)
}