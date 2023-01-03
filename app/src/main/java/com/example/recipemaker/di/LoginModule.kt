package com.example.recipemaker.di

import com.example.recipemaker.data.LoginRepositoryImpl
import com.example.recipemaker.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        auth: FirebaseAuth,
        @FirebaseModule.UsersCollection userCollection: CollectionReference
    ): LoginRepository {
        return LoginRepositoryImpl(
            auth, userCollection
        )
    }

}