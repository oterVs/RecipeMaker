package com.example.recipemaker.data.endpoint

import com.example.recipemaker.domain.model.User
import com.example.recipemaker.domain.model.UserApiItem
import com.example.recipemaker.domain.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users")
    suspend fun getAllCharactersApi() : Response<UserResponse>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id : String) : Response<UserApiItem>
}