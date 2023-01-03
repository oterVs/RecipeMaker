package com.example.recipemaker.domain.usecases

import com.example.recipemaker.domain.model.User
import com.example.recipemaker.domain.repository.LoginRepository
import com.example.recipemaker.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.PasswordAuthentication
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(user: User, password: String): Flow<DataState<User>> = flow {
        loginRepository.signUp(user,password)
    }
}