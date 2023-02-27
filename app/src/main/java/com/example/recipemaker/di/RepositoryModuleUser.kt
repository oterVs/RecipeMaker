package com.example.recipemaker.di

import com.example.recipemaker.data.remote.UserApiRepositoryImpl
import com.example.recipemaker.domain.interfaces.UserApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleUser {

    //Injección de dependencias
    @Singleton
    @Binds
    abstract fun userRepository(
        userRepositoryImpl: UserApiRepositoryImpl
    ):UserApiRepository

}