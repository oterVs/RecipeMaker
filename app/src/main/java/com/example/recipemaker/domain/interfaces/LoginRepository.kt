package com.example.recipemaker.domain.interfaces

import com.example.recipemaker.domain.model.User
import com.example.recipemaker.utils.DataState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(email: String, password: String): Flow<DataState<Boolean>>
    suspend fun signUp(user: User, password: String): Flow<DataState<User>>
    suspend fun logOut(): Flow<DataState<Boolean>>
    suspend fun getUserData(): Flow<DataState<Boolean>>
    suspend fun saveUser(user: User): Flow<DataState<Boolean>>
    suspend fun getUser(mail: String): Flow<DataState<User>>
}