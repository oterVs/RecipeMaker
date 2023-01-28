package com.example.recipemaker.data.endpoint

import com.example.recipemaker.domain.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {

    @GET("users")
    suspend fun getAllCharactersApi() : Response<UserResponse>
}