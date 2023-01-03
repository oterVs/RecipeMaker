package com.example.recipemaker.domain.usecases

import android.provider.ContactsContract.Data
import com.example.recipemaker.domain.model.User
import com.example.recipemaker.domain.repository.LoginRepository
import com.example.recipemaker.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(user: User): Flow<DataState<Boolean>> = flow {
        loginRepository.saveUser(user)
    }
}