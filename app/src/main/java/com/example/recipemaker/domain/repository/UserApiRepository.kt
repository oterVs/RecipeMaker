package com.example.recipemaker.domain.repository

import com.example.recipemaker.domain.model.UserResponse
import com.example.recipemaker.utils.DataState
import kotlinx.coroutines.flow.Flow

interface UserApiRepository {

    suspend fun getUsers(): Flow<DataState<UserResponse>>
}