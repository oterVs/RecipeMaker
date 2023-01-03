package com.example.recipemaker.di

import androidx.core.content.PackageManagerCompat.UnusedAppRestrictionsStatus
import com.example.recipemaker.utils.Constants.FOOD_COLLECTION
import com.example.recipemaker.utils.Constants.USER_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuthProvider(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStoreProvider(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @UsersCollection
    @Provides
    @Singleton
    fun providesUsersCollection(
        firestore: FirebaseFirestore
    ): CollectionReference {
        return firestore.collection(USER_COLLECTION)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UsersCollection


    @FoodCollection
    @Provides
    @Singleton
    fun providesFoodCollection(
        firestore: FirebaseFirestore
    ): CollectionReference {
        return firestore.collection(FOOD_COLLECTION)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class FoodCollection


}