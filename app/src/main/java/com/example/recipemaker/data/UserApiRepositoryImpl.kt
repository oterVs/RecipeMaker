package com.example.recipemaker.data

import com.example.recipemaker.data.endpoint.UserApi
import com.example.recipemaker.domain.model.UserResponse
import com.example.recipemaker.domain.repository.UserApiRepository
import com.example.recipemaker.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserApiRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserApiRepository {
    override suspend fun getUsers(): Flow<DataState<UserResponse>> = flow {
        emit(DataState.Loading)
        try {
            var resul : UserResponse = userApi.getAllCharactersApi().body()!!
            emit(DataState.Success(resul))
            emit(DataState.Finished)
        } catch (e: Exception){
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }


    }
}